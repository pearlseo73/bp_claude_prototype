<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<c:set var="cur" value="${report.current}" />
<c:set var="prev" value="${report.previous}" />

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold"><i class="bi bi-calendar-month text-primary me-2"></i>월간 레포트</h2>
        <a href="${ctx}/reports/weekly" class="btn btn-outline-primary rounded-pill"><i class="bi bi-arrow-left"></i> 주간 레포트</a>
    </div>

    <!-- 월 이동 -->
    <div class="d-flex justify-content-center align-items-center gap-3 mb-4">
        <c:set var="prevM" value="${month == 1 ? 12 : month - 1}" />
        <c:set var="prevY" value="${month == 1 ? year - 1 : year}" />
        <c:set var="nextM" value="${month == 12 ? 1 : month + 1}" />
        <c:set var="nextY" value="${month == 12 ? year + 1 : year}" />
        <a href="${ctx}/reports/monthly?year=${prevY}&month=${prevM}" class="btn btn-sm btn-light rounded-circle"><i class="bi bi-chevron-left"></i></a>
        <h4 class="fw-bold mb-0">${year}년 ${month}월</h4>
        <a href="${ctx}/reports/monthly?year=${nextY}&month=${nextM}" class="btn btn-sm btn-light rounded-circle"><i class="bi bi-chevron-right"></i></a>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-md-4">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4 text-center">
                    <div class="text-muted small mb-1">이번 달 수입</div>
                    <div class="fw-bold" style="font-size:1.5rem; color:#27ae60;"><fmt:formatNumber value="${cur.totalIncome}" type="number" />원</div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4 text-center">
                    <div class="text-muted small mb-1">이번 달 지출</div>
                    <div class="fw-bold" style="font-size:1.5rem; color:#e74c3c;"><fmt:formatNumber value="${cur.totalExpense}" type="number" />원</div>
                    <div class="mt-2">
                        <c:choose>
                            <c:when test="${report.expenseChangePercent > 0}">
                                <span class="badge rounded-pill" style="background:rgba(231,76,60,0.15); color:#c0392b;"><i class="bi bi-arrow-up"></i> 지난달 대비 ${report.expenseChangePercent}%</span>
                            </c:when>
                            <c:when test="${report.expenseChangePercent < 0}">
                                <span class="badge rounded-pill" style="background:rgba(39,174,96,0.15); color:#1e8449;"><i class="bi bi-arrow-down"></i> 지난달 대비 ${-report.expenseChangePercent}%</span>
                            </c:when>
                            <c:otherwise><span class="badge rounded-pill bg-light text-muted">지난달과 동일</span></c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4 text-center">
                    <div class="text-muted small mb-1">이번 달 절약액</div>
                    <c:set var="saving" value="${cur.totalIncome - cur.totalExpense}" />
                    <div class="fw-bold" style="font-size:1.5rem; color:${saving >= 0 ? '#1a6b5a' : '#e74c3c'};"><fmt:formatNumber value="${saving}" type="number" />원</div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <!-- 굴비 성장 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px; background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
                <div class="card-body p-4 text-center">
                    <h5 class="fw-bold mb-3">굴비 성장 결과</h5>
                    <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" style="height:110px; object-fit:contain;">
                    <c:if test="${report.gulbi != null}">
                        <div class="mt-3 fw-bold" style="font-size:1.2rem;">현재 체중: ${report.gulbi.weight}kg</div>
                        <div class="small mt-2" style="opacity:0.85;">
                            연속 기록 ${report.gulbi.streakRecord}일 · 예산 성공 ${report.gulbi.streakBudget}주
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- 메이트 비교 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3"><i class="bi bi-people me-1"></i> 메이트 비교</h5>
                    <c:choose>
                        <c:when test="${not empty report.mateNickname}">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="text-muted">나의 지출</span>
                                <span class="fw-bold" style="color:#e74c3c;"><fmt:formatNumber value="${cur.totalExpense}" type="number" />원</span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="text-muted">${report.mateNickname}님의 지출</span>
                                <span class="fw-bold"><fmt:formatNumber value="${report.mateExpense}" type="number" />원</span>
                            </div>
                            <hr>
                            <div class="text-center fw-bold ${cur.totalExpense <= report.mateExpense ? 'text-success' : 'text-danger'}">
                                <c:choose>
                                    <c:when test="${cur.totalExpense < report.mateExpense}">🎉 메이트보다 더 아꼈어요!</c:when>
                                    <c:when test="${cur.totalExpense > report.mateExpense}">😢 메이트보다 더 썼어요. 분발해요!</c:when>
                                    <c:otherwise>막상막하예요!</c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center text-muted py-4">
                                <div style="font-size:2.5rem;">🤝</div>
                                <p class="mt-2 mb-2">아직 메이트가 없어요.</p>
                                <a href="${ctx}/mates" class="btn btn-sm btn-outline-primary rounded-pill">메이트 맺으러 가기</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- 다짐 작성 + 굴비 대화 -->
    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3"><i class="bi bi-chat-heart me-1"></i> 굴비에게 다짐 남기기</h5>
            <form action="${ctx}/reports/monthly/message" method="post" class="mb-4">
                <input type="hidden" name="year" value="${year}">
                <input type="hidden" name="month" value="${month}">
                <div class="input-group">
                    <input type="text" name="message" class="form-control" placeholder="이번 달 다짐이나 굴비에게 하고 싶은 말을 적어보세요!" required maxlength="200">
                    <button type="submit" class="btn rounded-end" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">보내기</button>
                </div>
            </form>

            <c:choose>
                <c:when test="${empty report.dialogues}">
                    <p class="text-center text-muted py-3">아직 굴비와 나눈 대화가 없어요. 첫 다짐을 남겨보세요!</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="d" items="${report.dialogues}">
                        <div class="mb-3">
                            <!-- 사용자 -->
                            <div class="d-flex justify-content-end mb-1">
                                <div class="p-2 px-3" style="background:#e9f5f1; border-radius:14px 14px 2px 14px; max-width:75%;">
                                    ${d.userMessage}
                                </div>
                            </div>
                            <!-- 굴비 -->
                            <div class="d-flex justify-content-start align-items-end gap-2">
                                <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" style="height:32px; object-fit:contain;">
                                <div class="p-2 px-3" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white; border-radius:14px 14px 14px 2px; max-width:75%;">
                                    ${d.gulbiReply}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="text-end">
                        <a href="${ctx}/dialogues" class="small text-muted">전체 대화 로그 보기 <i class="bi bi-arrow-right"></i></a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
