<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:860px;">
    <h2 class="fw-bold mb-4"><i class="bi bi-calendar-check text-primary me-2"></i>예정 지출</h2>

    <!-- 운용 가능 금액 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px; background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
        <div class="card-body p-4">
            <div class="text-center mb-3">
                <div class="small" style="opacity:0.85;">이번 달 운용 가능 금액</div>
                <div class="fw-bold" style="font-size:2rem;"><fmt:formatNumber value="${available}" type="number"/>원</div>
            </div>
            <div class="row text-center" style="font-size:0.9rem;">
                <div class="col-4"><div style="opacity:0.8;">수입</div><div class="fw-bold"><fmt:formatNumber value="${income}" type="number"/></div></div>
                <div class="col-4"><div style="opacity:0.8;">지출</div><div class="fw-bold"><fmt:formatNumber value="${expense}" type="number"/></div></div>
                <div class="col-4"><div style="opacity:0.8;">예정 지출</div><div class="fw-bold"><fmt:formatNumber value="${pendingTotal}" type="number"/></div></div>
            </div>
        </div>
    </div>

    <!-- 등록 폼 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">예정 지출 등록</h5>
            <form action="${ctx}/scheduled-expenses" method="post" class="row g-2">
                <div class="col-md-5">
                    <input type="text" name="name" class="form-control" placeholder="예: 월세, 통신비" required>
                </div>
                <div class="col-md-3">
                    <input type="number" name="amount" class="form-control" placeholder="금액" min="0" required>
                </div>
                <div class="col-md-3">
                    <input type="date" name="dueDate" class="form-control" required>
                </div>
                <div class="col-md-1 d-grid">
                    <button class="btn rounded" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">등록</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 목록 -->
    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-4">예정일</th>
                            <th>내용</th>
                            <th class="text-end">금액</th>
                            <th class="text-center">관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="e" items="${expenses}">
                            <tr>
                                <td class="ps-4">${e.dueDate}</td>
                                <td>${e.name}</td>
                                <td class="text-end fw-bold text-danger">-<fmt:formatNumber value="${e.amount}" type="number"/>원</td>
                                <td class="text-center">
                                    <form action="${ctx}/scheduled-expenses/delete" method="post" class="d-inline" onsubmit="return confirm('삭제하시겠습니까?');">
                                        <input type="hidden" name="id" value="${e.id}">
                                        <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty expenses}">
                            <tr><td colspan="4" class="text-center py-5 text-muted">등록된 예정 지출이 없습니다.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
