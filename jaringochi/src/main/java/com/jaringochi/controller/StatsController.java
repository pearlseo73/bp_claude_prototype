package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.StatsService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/stats")
public class StatsController extends HttpServlet {
    private final StatsService statsService = new StatsService();
    private static final int MONTHS = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        long uid = user.getId();
        req.setAttribute("trendLabels", statsService.getTrendLabels(MONTHS));
        req.setAttribute("trendIncome", statsService.getMonthlyTrend(uid, "INCOME", MONTHS));
        req.setAttribute("trendExpense", statsService.getMonthlyTrend(uid, "EXPENSE", MONTHS));
        req.setAttribute("expenseByCategory", statsService.getCategoryBreakdown(uid, "EXPENSE"));
        req.setAttribute("incomeByCategory", statsService.getCategoryBreakdown(uid, "INCOME"));

        req.getRequestDispatcher("/WEB-INF/views/stats/main.jsp").forward(req, resp);
    }
}
