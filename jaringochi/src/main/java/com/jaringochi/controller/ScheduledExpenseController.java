package com.jaringochi.controller;

import com.jaringochi.domain.ScheduledExpense;
import com.jaringochi.domain.User;
import com.jaringochi.repository.EntryRepository;
import com.jaringochi.service.ScheduledExpenseService;
import com.jaringochi.util.DateUtil;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/scheduled-expenses/*")
public class ScheduledExpenseController extends HttpServlet {
    private final ScheduledExpenseService scheduledExpenseService = new ScheduledExpenseService();
    private final EntryRepository entryRepository = new EntryRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        long uid = user.getId();
        List<ScheduledExpense> list = scheduledExpenseService.getPendingExpenses(uid);

        int pendingTotal = 0;
        for (ScheduledExpense e : list) pendingTotal += e.getAmount();

        // 운용 가능 금액 = 수입 - (지출 + 예정 지출)  (이번 달 기준)
        LocalDate now = LocalDate.now();
        LocalDate start = DateUtil.getMonthStart(now);
        LocalDate end = DateUtil.getMonthEnd(now);
        int income = entryRepository.sumByUserIdAndTypeAndDateRange(uid, "INCOME", start, end);
        int expense = entryRepository.sumByUserIdAndTypeAndDateRange(uid, "EXPENSE", start, end);
        int available = income - (expense + pendingTotal);

        req.setAttribute("expenses", list);
        req.setAttribute("pendingTotal", pendingTotal);
        req.setAttribute("income", income);
        req.setAttribute("expense", expense);
        req.setAttribute("available", available);
        req.getRequestDispatcher("/WEB-INF/views/scheduledExpense/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/delete".equals(pathInfo)) {
            try { scheduledExpenseService.deleteExpense(Long.parseLong(req.getParameter("id"))); }
            catch (Exception ignored) { }
        } else {
            try {
                ScheduledExpense exp = new ScheduledExpense();
                exp.setUserId(user.getId());
                exp.setName(req.getParameter("name"));
                exp.setAmount(Integer.parseInt(req.getParameter("amount")));
                exp.setDueDate(LocalDate.parse(req.getParameter("dueDate")));
                scheduledExpenseService.addExpense(exp);
            } catch (Exception ignored) { }
        }
        resp.sendRedirect(req.getContextPath() + "/scheduled-expenses");
    }
}
