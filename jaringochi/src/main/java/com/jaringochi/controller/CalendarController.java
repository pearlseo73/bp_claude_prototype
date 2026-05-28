package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.CalendarService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/calendar/*")
public class CalendarController extends HttpServlet {
    private final CalendarService calendarService = new CalendarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        LocalDate now = LocalDate.now();
        int year = parseInt(req.getParameter("year"), now.getYear());
        int month = parseInt(req.getParameter("month"), now.getMonthValue());

        LocalDate first = LocalDate.of(year, month, 1);
        int daysInMonth = first.lengthOfMonth();
        // 일요일 시작 그리드: 1일 앞에 비울 칸 수 (SUNDAY=0)
        int firstOffset = first.getDayOfWeek().getValue() % 7;

        req.setAttribute("days", calendarService.getMonthData(user.getId(), year, month));
        req.setAttribute("today", now);
        req.setAttribute("year", year);
        req.setAttribute("month", month);
        req.setAttribute("daysInMonth", daysInMonth);
        req.setAttribute("firstOffset", firstOffset);

        // 이전/다음 달
        LocalDate prev = first.minusMonths(1);
        LocalDate next = first.plusMonths(1);
        req.setAttribute("prevYear", prev.getYear());
        req.setAttribute("prevMonth", prev.getMonthValue());
        req.setAttribute("nextYear", next.getYear());
        req.setAttribute("nextMonth", next.getMonthValue());

        // 날짜별 상세
        String pathInfo = req.getPathInfo();
        if ("/day".equals(pathInfo)) {
            String dateStr = req.getParameter("date");
            if (dateStr != null && !dateStr.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    req.setAttribute("selectedDate", date);
                    req.setAttribute("dayEntries", calendarService.getDayEntries(user.getId(), date));
                } catch (Exception ignored) { }
            }
        }

        req.getRequestDispatcher("/WEB-INF/views/calendar/main.jsp").forward(req, resp);
    }

    private int parseInt(String s, int def) {
        try { return (s != null) ? Integer.parseInt(s) : def; }
        catch (NumberFormatException e) { return def; }
    }
}
