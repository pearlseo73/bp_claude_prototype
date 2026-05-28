<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-bar-chart-line text-primary me-2"></i>통계</h2>

    <!-- 월별 수입/지출 추이 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">최근 6개월 수입/지출 추이</h5>
            <canvas id="trendChart" height="110"></canvas>
        </div>
    </div>

    <div class="row g-4">
        <!-- 카테고리별 지출 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3">이번 달 카테고리별 지출</h5>
                    <c:choose>
                        <c:when test="${empty expenseByCategory}">
                            <p class="text-center text-muted py-5">이번 달 지출 내역이 없어요.</p>
                        </c:when>
                        <c:otherwise>
                            <canvas id="expenseChart" height="220"></canvas>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <!-- 카테고리별 수입 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3">이번 달 카테고리별 수입</h5>
                    <c:choose>
                        <c:when test="${empty incomeByCategory}">
                            <p class="text-center text-muted py-5">이번 달 수입 내역이 없어요.</p>
                        </c:when>
                        <c:otherwise>
                            <canvas id="incomeChart" height="220"></canvas>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
(function () {
    const palette = ['#1a6b5a','#ff8c42','#2980b9','#8e44ad','#27ae60','#f39c12','#e74c3c','#16a085','#d35400','#7f8c8d'];

    // 월별 추이
    const trendLabels = [<c:forEach var="l" items="${trendLabels}" varStatus="s">'${l}'<c:if test="${!s.last}">,</c:if></c:forEach>];
    const incomeData  = [<c:forEach var="v" items="${trendIncome}" varStatus="s">${v}<c:if test="${!s.last}">,</c:if></c:forEach>];
    const expenseData = [<c:forEach var="v" items="${trendExpense}" varStatus="s">${v}<c:if test="${!s.last}">,</c:if></c:forEach>];

    new Chart(document.getElementById('trendChart'), {
        type: 'bar',
        data: {
            labels: trendLabels,
            datasets: [
                { label: '수입', data: incomeData, backgroundColor: '#27ae60', borderRadius: 6 },
                { label: '지출', data: expenseData, backgroundColor: '#e74c3c', borderRadius: 6 }
            ]
        },
        options: { responsive: true, plugins: { legend: { position: 'top' } },
                   scales: { y: { beginAtZero: true, ticks: { callback: v => v.toLocaleString() } } } }
    });

    // 카테고리별 지출
    const expCatLabels = [<c:forEach var="e" items="${expenseByCategory}" varStatus="s">'${e.key}'<c:if test="${!s.last}">,</c:if></c:forEach>];
    const expCatData   = [<c:forEach var="e" items="${expenseByCategory}" varStatus="s">${e.value}<c:if test="${!s.last}">,</c:if></c:forEach>];
    const expEl = document.getElementById('expenseChart');
    if (expEl && expCatData.length) {
        new Chart(expEl, {
            type: 'doughnut',
            data: { labels: expCatLabels, datasets: [{ data: expCatData, backgroundColor: palette }] },
            options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
        });
    }

    // 카테고리별 수입
    const incCatLabels = [<c:forEach var="e" items="${incomeByCategory}" varStatus="s">'${e.key}'<c:if test="${!s.last}">,</c:if></c:forEach>];
    const incCatData   = [<c:forEach var="e" items="${incomeByCategory}" varStatus="s">${e.value}<c:if test="${!s.last}">,</c:if></c:forEach>];
    const incEl = document.getElementById('incomeChart');
    if (incEl && incCatData.length) {
        new Chart(incEl, {
            type: 'doughnut',
            data: { labels: incCatLabels, datasets: [{ data: incCatData, backgroundColor: palette }] },
            options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
        });
    }
})();
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
