<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">${sessionScope.loginUser.nickname}님의 대시보드</h2>
        <div>
            <a href="${ctx}/entries/new" class="btn btn-primary rounded-pill"><i class="bi bi-plus-circle me-1"></i> 지출 등록</a>
        </div>
    </div>

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
            <div class="card shadow-sm border-0 h-100" style="background: linear-gradient(135deg, #ff8c42 0%, #ffaa6e 100%); color: white; border-radius: 20px;">
                <div class="card-body d-flex flex-column justify-content-center align-items-center position-relative overflow-hidden">
                    <h5 class="fw-bold mb-3 z-1">오늘의 굴비</h5>
                    <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" class="img-fluid z-1" style="max-height: 120px; object-fit: contain;">
                    <div class="mt-3 text-center bg-white text-dark p-2 rounded-3 shadow-sm z-1" style="font-size: 0.9rem;">
                        "잘 아끼고 있어! 이 속도면 이번 주도 성공이야! 🐟✨"
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
