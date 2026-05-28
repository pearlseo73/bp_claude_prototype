<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 - 자린고치</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
    <link rel="icon" href="${ctx}/static/images/favicon.png">
</head>
<body class="bg-cream">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-5 shadow glass-card text-center" style="max-width: 400px; width: 100%;">
            <img src="${ctx}/static/images/logo.png" alt="자린고치 로고" class="mb-4 mx-auto" style="width: 100px;">
            <h2 class="mb-4 fw-bold text-primary">자린고치</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form action="${ctx}/auth/login" method="post">
                <div class="mb-3 text-start">
                    <label for="username" class="form-label">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-4 text-start">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100 rounded-pill mb-3">로그인</button>
                <a href="${ctx}/auth/signup" class="text-decoration-none text-muted">아직 계정이 없으신가요? 회원가입</a>
            </form>
        </div>
    </div>
</body>
</html>
