<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container mt-4 d-flex justify-content-center">
    <div class="card shadow-sm border-0 glass-card" style="width: 100%; max-width: 500px;">
        <div class="card-body p-4 text-center">
            <h3 class="fw-bold mb-4">예산 설정</h3>
            
            <c:if test="${not empty budget}">
                <div class="alert alert-success mb-4 text-start">
                    <h5 class="alert-heading fw-bold"><i class="bi bi-info-circle me-1"></i> 현재 예산 정보</h5>
                    <p class="mb-1">예산 기준: <strong>${budget.budgetType == 'WEEKLY' ? '주간' : '월간'}</strong></p>
                    <p class="mb-1">주간 예산: <strong><fmt:formatNumber value="${budget.weeklyAmount}" type="number" />원</strong></p>
                    <p class="mb-0">월간 예산: <strong><fmt:formatNumber value="${budget.monthlyAmount}" type="number" />원</strong></p>
                </div>
            </c:if>
            
            <form action="${ctx}/budgets" method="post" class="text-start">
                <div class="mb-3">
                    <label class="form-label fw-bold">기준 설정</label>
                    <select class="form-select" name="budgetType" required>
                        <option value="WEEKLY" ${budget.budgetType == 'WEEKLY' ? 'selected' : ''}>주간 예산</option>
                        <option value="MONTHLY" ${budget.budgetType == 'MONTHLY' ? 'selected' : ''}>월간 예산</option>
                    </select>
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold">금액 설정</label>
                    <div class="input-group">
                        <input type="number" class="form-control form-control-lg" name="amount" placeholder="예: 150000" required>
                        <span class="input-group-text">원</span>
                    </div>
                    <div class="form-text mt-2 text-muted">주간 예산을 입력하면 월간 예산이 자동 계산됩니다.</div>
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg rounded-pill">예산 저장하기</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
