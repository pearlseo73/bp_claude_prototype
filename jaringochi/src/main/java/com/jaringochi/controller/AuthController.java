package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.AuthService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if ("/login".equals(pathInfo)) {
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        } else if ("/signup".equals(pathInfo)) {
            req.getRequestDispatcher("/WEB-INF/views/auth/signup.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if ("/login".equals(pathInfo)) {
            User user = authService.login(req.getParameter("username"), req.getParameter("password"));
            if (user != null) {
                SessionUtil.setLoginUser(req, user);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            } else {
                req.setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
                req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            }
        } else if ("/signup".equals(pathInfo)) {
            boolean success = authService.signup(req.getParameter("username"), req.getParameter("password"), req.getParameter("nickname"));
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/auth/login");
            } else {
                req.setAttribute("error", "이미 존재하는 아이디입니다.");
                req.getRequestDispatcher("/WEB-INF/views/auth/signup.jsp").forward(req, resp);
            }
        } else if ("/logout".equals(pathInfo)) {
            SessionUtil.removeLoginUser(req);
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }
}
