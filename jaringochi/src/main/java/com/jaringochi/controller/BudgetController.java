package com.jaringochi.controller;

import com.jaringochi.domain.Budget;
import com.jaringochi.domain.User;
import com.jaringochi.service.BudgetService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/budgets/*")
public class BudgetController extends HttpServlet {
    private BudgetService budgetService = new BudgetService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        req.setAttribute("budget", budgetService.getActiveBudget(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/budget/setting.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        Budget budget = new Budget();
        budget.setUserId(user.getId());
        budget.setBudgetType(req.getParameter("budgetType"));
        
        if ("WEEKLY".equals(budget.getBudgetType())) {
            budget.setWeeklyAmount(Integer.parseInt(req.getParameter("amount")));
        } else {
            budget.setMonthlyAmount(Integer.parseInt(req.getParameter("amount")));
        }
        budget.setStartDate(LocalDate.now()); // simplified
        
        budgetService.saveBudget(budget);
        resp.sendRedirect(req.getContextPath() + "/budgets");
    }
}
