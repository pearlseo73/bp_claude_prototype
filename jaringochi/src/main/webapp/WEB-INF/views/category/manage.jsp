<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container mt-4">
    <h2 class="fw-bold mb-4">카테고리 관리</h2>
    
    <div class="row g-4">
        <div class="col-md-6">
            <div class="card shadow-sm border-0 glass-card h-100">
                <div class="card-header bg-transparent border-0 pt-4 pb-0">
                    <h5 class="fw-bold text-success"><i class="bi bi-arrow-down-circle"></i> 수입 카테고리</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex flex-wrap gap-2 mb-4">
                        <c:forEach var="cat" items="${categories}">
                            <c:if test="${cat.type == 'INCOME'}">
                                <span class="badge bg-light text-dark border p-2 d-flex align-items-center gap-2">
                                    ${cat.name}
                                    <form action="${ctx}/categories/delete" method="post" class="m-0" onsubmit="return confirm('삭제하시겠습니까?');">
                                        <input type="hidden" name="id" value="${cat.id}">
                                        <button type="submit" class="btn-close" style="font-size: 0.5rem;"></button>
                                    </form>
                                </span>
                            </c:if>
                        </c:forEach>
                    </div>
                    <form action="${ctx}/categories/new" method="post" class="d-flex gap-2">
                        <input type="hidden" name="type" value="INCOME">
                        <input type="text" name="name" class="form-control form-control-sm" placeholder="새 수입 카테고리" required>
                        <button type="submit" class="btn btn-sm btn-outline-success text-nowrap">추가</button>
                    </form>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <div class="card shadow-sm border-0 glass-card h-100">
                <div class="card-header bg-transparent border-0 pt-4 pb-0">
                    <h5 class="fw-bold text-danger"><i class="bi bi-arrow-up-circle"></i> 지출 카테고리</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex flex-wrap gap-2 mb-4">
                        <c:forEach var="cat" items="${categories}">
                            <c:if test="${cat.type == 'EXPENSE'}">
                                <span class="badge bg-light text-dark border p-2 d-flex align-items-center gap-2">
                                    ${cat.name}
                                    <form action="${ctx}/categories/delete" method="post" class="m-0" onsubmit="return confirm('삭제하시겠습니까?');">
                                        <input type="hidden" name="id" value="${cat.id}">
                                        <button type="submit" class="btn-close" style="font-size: 0.5rem;"></button>
                                    </form>
                                </span>
                            </c:if>
                        </c:forEach>
                    </div>
                    <form action="${ctx}/categories/new" method="post" class="d-flex gap-2">
                        <input type="hidden" name="type" value="EXPENSE">
                        <input type="text" name="name" class="form-control form-control-sm" placeholder="새 지출 카테고리" required>
                        <button type="submit" class="btn btn-sm btn-outline-danger text-nowrap">추가</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
