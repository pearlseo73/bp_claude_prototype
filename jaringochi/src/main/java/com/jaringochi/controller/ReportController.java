package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.DialogueService;
import com.jaringochi.service.ReportService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reports/*")
public class ReportController extends HttpServlet {
    private final ReportService reportService = new ReportService();
    private final DialogueService dialogueService = new DialogueService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/monthly".equals(pathInfo)) {
            LocalDate now = LocalDate.now();
            int year = parseInt(req.getParameter("year"), now.getYear());
            int month = parseInt(req.getParameter("month"), now.getMonthValue());
            req.setAttribute("report", reportService.getMonthlyReport(user.getId(), year, month));
            req.setAttribute("year", year);
            req.setAttribute("month", month);
            req.getRequestDispatcher("/WEB-INF/views/report/monthly.jsp").forward(req, resp);
        } else {
            // 기본: 주간 레포트
            req.setAttribute("report", reportService.getWeeklyReport(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/report/weekly.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/monthly/message".equals(pathInfo)) {
            LocalDate now = LocalDate.now();
            int year = parseInt(req.getParameter("year"), now.getYear());
            int month = parseInt(req.getParameter("month"), now.getMonthValue());
            String message = req.getParameter("message");
            if (message != null && !message.trim().isEmpty()) {
                Long reportId = reportService.getMonthlyReportId(user.getId(), year, month);
                dialogueService.createDialogue(user.getId(), reportId, message.trim());
            }
            resp.sendRedirect(req.getContextPath() + "/reports/monthly?year=" + year + "&month=" + month);
        }
    }

    private int parseInt(String s, int def) {
        try { return (s != null) ? Integer.parseInt(s) : def; }
        catch (NumberFormatException e) { return def; }
    }
}
