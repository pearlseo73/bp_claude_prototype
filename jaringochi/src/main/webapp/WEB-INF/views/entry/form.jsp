<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container mt-4 d-flex justify-content-center">
    <div class="card shadow-sm border-0 glass-card" style="width: 100%; max-width: 500px;">
        <div class="card-body p-4">
            <h3 class="fw-bold mb-4 text-center">거래 등록</h3>
            <form action="${ctx}/entries" method="post">
                <div class="mb-3">
                    <label class="form-label">유형</label>
                    <select class="form-select" name="type" required>
                        <option value="EXPENSE">지출</option>
                        <option value="INCOME">수입</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">날짜</label>
                    <input type="date" class="form-control" name="entryDate" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">금액</label>
                    <div class="input-group">
                        <input type="number" class="form-control" name="amount" required>
                        <span class="input-group-text">원</span>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">카테고리</label>
                    <select class="form-select" name="categoryId" required>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.id}">${cat.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-4">
                    <label class="form-label">메모 (선택)</label>
                    <input type="text" class="form-control" name="memo">
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary rounded-pill">등록하기</button>
                    <a href="${ctx}/entries" class="btn btn-light rounded-pill">취소</a>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
