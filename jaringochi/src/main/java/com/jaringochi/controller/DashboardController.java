package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.DashboardService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
    private DashboardService dashboardService = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        // Here we should fetch dashboard DTO, for now passing dummy
        req.getRequestDispatcher("/WEB-INF/views/dashboard/main.jsp").forward(req, resp);
    }
}
