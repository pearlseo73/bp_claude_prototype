<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Main Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark jrc-navbar sticky-top" style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%);">

    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand fw-bold d-flex align-items-center" href="${ctx}/dashboard">
            <img src="${ctx}/static/images/logo.png" alt="로고" style="height:30px; margin-right:10px;">
            <span class="brand-text">자린고치</span>
        </a>

        <!-- Mobile Toggle -->
        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="메뉴 열기">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navigation Links -->
        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/dashboard"><i class="bi bi-speedometer2 me-1"></i> 대시보드</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="bi bi-journal-text me-1"></i> 가계부</a>
                    <ul class="dropdown-menu dropdown-menu-dark jrc-dropdown">
                        <li><a class="dropdown-item" href="${ctx}/entries/new"><i class="bi bi-plus-circle me-2"></i> 거래 등록</a></li>
                        <li><a class="dropdown-item" href="${ctx}/entries"><i class="bi bi-list-ul me-2"></i> 거래 내역</a></li>
                        <li><a class="dropdown-item" href="${ctx}/calendar"><i class="bi bi-calendar3 me-2"></i> 캘린더 보기</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${ctx}/categories"><i class="bi bi-tags me-2"></i> 카테고리 관리</a></li>
                        <li><a class="dropdown-item" href="${ctx}/stats"><i class="bi bi-bar-chart-line me-2"></i> 통계</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="bi bi-piggy-bank me-1"></i> 예산</a>
                    <ul class="dropdown-menu dropdown-menu-dark jrc-dropdown">
                        <li><a class="dropdown-item" href="${ctx}/budgets"><i class="bi bi-sliders me-2"></i> 예산 설정</a></li>
                        <li><a class="dropdown-item" href="${ctx}/scheduled-expenses"><i class="bi bi-calendar-check me-2"></i> 예정 지출</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="bi bi-clipboard-data me-1"></i> 레포트</a>
                    <ul class="dropdown-menu dropdown-menu-dark jrc-dropdown">
                        <li><a class="dropdown-item" href="${ctx}/reports/weekly"><i class="bi bi-calendar-week me-2"></i> 주간 레포트</a></li>
                        <li><a class="dropdown-item" href="${ctx}/reports/monthly"><i class="bi bi-calendar-month me-2"></i> 월간 레포트</a></li>
                        <li><a class="dropdown-item" href="${ctx}/dialogues"><i class="bi bi-chat-dots me-2"></i> 대화 로그</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><span class="me-1">🐟</span> 내 굴비</a>
                    <ul class="dropdown-menu dropdown-menu-dark jrc-dropdown">
                        <li><a class="dropdown-item" href="${ctx}/gulbi"><i class="bi bi-heart-pulse me-2"></i> 굴비 상태</a></li>
                        <li><a class="dropdown-item" href="${ctx}/gulbi/wardrobe"><i class="bi bi-bag me-2"></i> 옷장</a></li>
                        <li><a class="dropdown-item" href="${ctx}/gulbi/children"><i class="bi bi-egg me-2"></i> 새끼 굴비</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/mates"><i class="bi bi-people me-1"></i> 메이트</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/community"><i class="bi bi-chat-square-text me-1"></i> 커뮤니티</a>
                </li>
            </ul>

            <!-- Right Side: User Info -->
            <div class="d-flex align-items-center">
                <c:choose>
                    <c:when test="${not empty sessionScope.loginUser}">
                        <div class="nav-user-info me-3 text-white">
                            <span class="nav-user-avatar"><i class="bi bi-person-circle"></i></span>
                            <span class="nav-user-nickname fw-bold">${sessionScope.loginUser.nickname}</span>
                        </div>
                        <form action="${ctx}/auth/logout" method="post" class="d-inline">
                            <button type="submit" class="btn btn-outline-light btn-sm"><i class="bi bi-box-arrow-right me-1"></i> 로그아웃</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctx}/auth/login" class="btn btn-outline-light btn-sm me-2">로그인</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
