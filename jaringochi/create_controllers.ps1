$ErrorActionPreference = "Stop"

$baseJava = "c:\Users\SSAFY\jj\bp2\jaringochi\src\main\java\com\jaringochi\controller"
$baseJsp = "c:\Users\SSAFY\jj\bp2\jaringochi\src\main\webapp\WEB-INF\views"

# Create directories
New-Item -ItemType Directory -Force -Path "$baseJsp\auth" | Out-Null
New-Item -ItemType Directory -Force -Path "$baseJsp\dashboard" | Out-Null

# AuthController.java
$authController = @"
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
"@
Set-Content -Path "$baseJava\AuthController.java" -Value $authController -Encoding UTF8

# login.jsp
$loginJsp = @"
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="`${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 - 자린고치</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="`${ctx}/static/css/style.css">
    <link rel="icon" href="`${ctx}/static/images/favicon.png">
</head>
<body class="bg-cream">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-5 shadow glass-card text-center" style="max-width: 400px; width: 100%;">
            <img src="`${ctx}/static/images/logo.png" alt="자린고치 로고" class="mb-4 mx-auto" style="width: 100px;">
            <h2 class="mb-4 fw-bold text-primary">자린고치</h2>
            <c:if test="`${not empty error}">
                <div class="alert alert-danger">`${error}</div>
            </c:if>
            <form action="`${ctx}/auth/login" method="post">
                <div class="mb-3 text-start">
                    <label for="username" class="form-label">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-4 text-start">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100 rounded-pill mb-3">로그인</button>
                <a href="`${ctx}/auth/signup" class="text-decoration-none text-muted">아직 계정이 없으신가요? 회원가입</a>
            </form>
        </div>
    </div>
</body>
</html>
"@
Set-Content -Path "$baseJsp\auth\login.jsp" -Value $loginJsp -Encoding UTF8

# DashboardController.java
$dashController = @"
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
"@
Set-Content -Path "$baseJava\DashboardController.java" -Value $dashController -Encoding UTF8

# dashboard main.jsp
$dashJsp = @"
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">`${sessionScope.loginUser.nickname}님의 대시보드</h2>
        <div>
            <a href="`${ctx}/entries/new" class="btn btn-primary rounded-pill"><i class="bi bi-plus-circle me-1"></i> 지출 등록</a>
        </div>
    </div>

    <!-- User provided Image integration here -->
    <div class="row mb-4">
        <div class="col-md-8">
            <div class="card shadow-sm glass-card h-100 border-0">
                <div class="card-body">
                    <h5 class="card-title fw-bold mb-3"><i class="bi bi-wallet2 text-primary me-2"></i>이번 주 예산 현황</h5>
                    <div class="progress mb-3" style="height: 25px; border-radius: 20px;">
                        <div class="progress-bar bg-primary" role="progressbar" style="width: 45%;" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100">45%</div>
                    </div>
                    <div class="d-flex justify-content-between text-muted">
                        <span>현재 지출: 67,500원</span>
                        <span>주간 예산: 150,000원</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100" style="background: var(--gradient-accent); color: white; border-radius: 20px;">
                <div class="card-body d-flex flex-column justify-content-center align-items-center position-relative overflow-hidden">
                    <h5 class="fw-bold mb-3 z-1">오늘의 굴비</h5>
                    <img src="`${ctx}/static/images/gulbi_main.png" alt="굴비" class="img-fluid z-1" style="max-height: 120px; object-fit: contain;">
                    <div class="mt-3 text-center bg-white text-dark p-2 rounded-3 shadow-sm z-1" style="font-size: 0.9rem;">
                        "잘 아끼고 있어! 이 속도면 이번 주도 성공이야! 🐟✨"
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
"@
Set-Content -Path "$baseJsp\dashboard\main.jsp" -Value $dashJsp -Encoding UTF8

Write-Host "Success"
