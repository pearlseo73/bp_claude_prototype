<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-calendar3 text-primary me-2"></i>가계부 캘린더</h2>

    <!-- 월 이동 -->
    <div class="d-flex justify-content-center align-items-center gap-3 mb-4">
        <a href="${ctx}/calendar?year=${prevYear}&month=${prevMonth}" class="btn btn-sm btn-light rounded-circle"><i class="bi bi-chevron-left"></i></a>
        <h4 class="fw-bold mb-0">${year}년 ${month}월</h4>
        <a href="${ctx}/calendar?year=${nextYear}&month=${nextMonth}" class="btn btn-sm btn-light rounded-circle"><i class="bi bi-chevron-right"></i></a>
    </div>

    <div class="row g-4">
        <div class="${not empty selectedDate ? 'col-lg-8' : 'col-12'}">
            <div class="card border-0 shadow-sm" style="border-radius:16px;">
                <div class="card-body p-3">
                    <!-- 요일 헤더 -->
                    <div style="display:grid; grid-template-columns:repeat(7,1fr); text-align:center; font-weight:700; margin-bottom:8px;">
                        <div style="color:#e74c3c;">일</div>
                        <div>월</div><div>화</div><div>수</div><div>목</div><div>금</div>
                        <div style="color:#2980b9;">토</div>
                    </div>
                    <!-- 날짜 그리드 -->
                    <fmt:formatNumber var="mm" value="${month}" minIntegerDigits="2" groupingUsed="false"/>
                    <div style="display:grid; grid-template-columns:repeat(7,1fr); gap:6px;">
                        <c:forEach var="i" begin="0" end="${firstOffset - 1}">
                            <div></div>
                        </c:forEach>
                        <c:forEach var="d" begin="1" end="${daysInMonth}">
                            <c:set var="dto" value="${days[d - 1]}" />
                            <c:set var="isToday" value="${today.year == year && today.monthValue == month && today.dayOfMonth == d}" />
                            <fmt:formatNumber var="dd" value="${d}" minIntegerDigits="2" groupingUsed="false"/>
                            <c:set var="dateStr" value="${year}-${mm}-${dd}" />
                            <a href="${ctx}/calendar/day?year=${year}&month=${month}&date=${dateStr}"
                               class="text-decoration-none text-dark"
                               style="border:1px solid ${isToday ? '#2d9b7a' : '#eee'}; border-radius:10px; min-height:78px; padding:6px; display:block; ${isToday ? 'background:rgba(45,155,122,0.08);' : ''}">
                                <div class="fw-bold small ${isToday ? 'text-success' : ''}">${d}</div>
                                <c:if test="${dto.totalIncome > 0}">
                                    <div style="font-size:0.7rem; color:#27ae60;">+<fmt:formatNumber value="${dto.totalIncome}" type="number"/></div>
                                </c:if>
                                <c:if test="${dto.totalExpense > 0}">
                                    <div style="font-size:0.7rem; color:#e74c3c;">-<fmt:formatNumber value="${dto.totalExpense}" type="number"/></div>
                                </c:if>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!-- 선택한 날짜 상세 -->
        <c:if test="${not empty selectedDate}">
            <div class="col-lg-4">
                <div class="card border-0 shadow-sm" style="border-radius:16px;">
                    <div class="card-body p-4">
                        <h5 class="fw-bold mb-3">${selectedDate} 거래 내역</h5>
                        <c:choose>
                            <c:when test="${empty dayEntries}">
                                <p class="text-muted text-center py-3">이 날의 거래가 없어요.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="e" items="${dayEntries}">
                                    <div class="d-flex justify-content-between align-items-center py-2 border-bottom">
                                        <div>
                                            <span class="badge ${e.type == 'INCOME' ? 'bg-success' : 'bg-danger'} me-1">
                                                ${e.type == 'INCOME' ? '수입' : '지출'}
                                            </span>
                                            <span class="small">${e.categoryName}</span>
                                            <div class="text-muted" style="font-size:0.75rem;">${e.memo}</div>
                                        </div>
                                        <span class="fw-bold ${e.type == 'INCOME' ? 'text-success' : 'text-danger'}">
                                            ${e.type == 'INCOME' ? '+' : '-'}<fmt:formatNumber value="${e.amount}" type="number"/>원
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <div class="text-end mt-3">
                            <a href="${ctx}/entries/new" class="btn btn-sm rounded-pill" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
                                <i class="bi bi-plus"></i> 거래 등록
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
