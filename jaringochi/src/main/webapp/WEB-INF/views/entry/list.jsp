<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">거래 내역</h2>
        <a href="${ctx}/entries/new" class="btn btn-primary rounded-pill"><i class="bi bi-plus"></i> 새 거래 등록</a>
    </div>
    
    <div class="card shadow-sm border-0 glass-card">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>날짜</th>
                            <th>분류</th>
                            <th>카테고리</th>
                            <th>내용</th>
                            <th class="text-end">금액</th>
                            <th class="text-center">관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${entries}">
                            <tr>
                                <td>${entry.entryDate}</td>
                                <td>
                                    <c:if test="${entry.type == 'INCOME'}"><span class="badge bg-success">수입</span></c:if>
                                    <c:if test="${entry.type == 'EXPENSE'}"><span class="badge bg-danger">지출</span></c:if>
                                </td>
                                <td>${entry.categoryName}</td>
                                <td>${entry.memo}</td>
                                <td class="text-end fw-bold ${entry.type == 'INCOME' ? 'text-success' : 'text-danger'}">
                                    <c:if test="${entry.type == 'INCOME'}">+</c:if>
                                    <c:if test="${entry.type == 'EXPENSE'}">-</c:if>
                                    <fmt:formatNumber value="${entry.amount}" type="number" />원
                                </td>
                                <td class="text-center">
                                    <form action="${ctx}/entries/delete" method="post" class="d-inline" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                        <input type="hidden" name="id" value="${entry.id}">
                                        <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty entries}">
                            <tr>
                                <td colspan="6" class="text-center py-5 text-muted">등록된 거래 내역이 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
