<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold"><i class="bi bi-calendar-week text-primary me-2"></i>주간 레포트</h2>
        <a href="${ctx}/reports/monthly" class="btn btn-outline-primary rounded-pill">월간 레포트 <i class="bi bi-arrow-right"></i></a>
    </div>

    <c:set var="cur" value="${report.current}" />
    <c:set var="prev" value="${report.previous}" />

    <!-- 기간 -->
    <p class="text-muted mb-4">
        <i class="bi bi-clock-history me-1"></i>
        ${cur.weekStart} ~ ${cur.weekEnd}
    </p>

    <div class="row g-4 mb-4">
        <!-- 이번 주 지출 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <div class="text-muted small mb-1">이번 주 지출</div>
                    <div class="fw-bold" style="font-size:1.8rem; color:#e74c3c;">
                        <fmt:formatNumber value="${cur.totalExpense}" type="number" />원
                    </div>
                    <div class="mt-2">
                        <c:choose>
                            <c:when test="${report.expenseChangePercent > 0}">
                                <span class="badge rounded-pill" style="background:rgba(231,76,60,0.15); color:#c0392b;">
                                    <i class="bi bi-arrow-up"></i> 지난주 대비 ${report.expenseChangePercent}% 증가
                                </span>
                            </c:when>
                            <c:when test="${report.expenseChangePercent < 0}">
                                <span class="badge rounded-pill" style="background:rgba(39,174,96,0.15); color:#1e8449;">
                                    <i class="bi bi-arrow-down"></i> 지난주 대비 ${-report.expenseChangePercent}% 감소
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge rounded-pill bg-light text-muted">지난주와 동일</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between text-muted small">
                        <span>지난주 지출</span>
                        <span><fmt:formatNumber value="${prev.totalExpense}" type="number" />원</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 이번 주 수입 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <div class="text-muted small mb-1">이번 주 수입</div>
                    <div class="fw-bold" style="font-size:1.8rem; color:#27ae60;">
                        <fmt:formatNumber value="${cur.totalIncome}" type="number" />원
                    </div>
                    <div class="mt-2">
                        <c:choose>
                            <c:when test="${report.incomeChangePercent > 0}">
                                <span class="badge rounded-pill" style="background:rgba(39,174,96,0.15); color:#1e8449;">
                                    <i class="bi bi-arrow-up"></i> 지난주 대비 ${report.incomeChangePercent}% 증가
                                </span>
                            </c:when>
                            <c:when test="${report.incomeChangePercent < 0}">
                                <span class="badge rounded-pill" style="background:rgba(231,76,60,0.15); color:#c0392b;">
                                    <i class="bi bi-arrow-down"></i> 지난주 대비 ${-report.incomeChangePercent}% 감소
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge rounded-pill bg-light text-muted">지난주와 동일</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between text-muted small">
                        <span>지난주 수입</span>
                        <span><fmt:formatNumber value="${prev.totalIncome}" type="number" />원</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 예산 대비 + 성공 여부 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
        <div class="card-body p-4">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h5 class="fw-bold mb-0">주간 예산 달성</h5>
                <c:choose>
                    <c:when test="${cur.budgetSuccess}">
                        <span class="badge rounded-pill px-3 py-2" style="background:linear-gradient(135deg,#27ae60,#2ecc71); font-size:0.85rem;">
                            <i class="bi bi-check-circle me-1"></i> 예산 성공
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge rounded-pill px-3 py-2" style="background:linear-gradient(135deg,#e74c3c,#ff6b6b); font-size:0.85rem;">
                            <i class="bi bi-exclamation-circle me-1"></i> 예산 초과
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:set var="pct" value="${cur.budgetAmount > 0 ? (cur.totalExpense * 100 / cur.budgetAmount) : 0}" />
            <div class="progress mb-2" style="height:22px; border-radius:20px;">
                <div class="progress-bar ${cur.budgetSuccess ? 'bg-success' : 'bg-danger'}"
                     role="progressbar" style="width: ${pct > 100 ? 100 : pct}%;">
                    ${pct}%
                </div>
            </div>
            <div class="d-flex justify-content-between text-muted small">
                <span>지출 <fmt:formatNumber value="${cur.totalExpense}" type="number" />원</span>
                <span>예산 <fmt:formatNumber value="${cur.budgetAmount}" type="number" />원</span>
            </div>
        </div>
    </div>

    <!-- 굴비 피드백 -->
    <div class="card border-0 shadow-sm" style="border-radius:16px; background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
        <div class="card-body p-4 d-flex align-items-center gap-3">
            <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" style="height:64px; object-fit:contain;">
            <div>
                <div class="fw-bold mb-1">굴비의 한마디 🐟</div>
                <div style="opacity:0.95;">${cur.feedbackMessage}</div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
