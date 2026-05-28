<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:680px;">
    <div class="d-flex align-items-center mb-4">
        <a href="${ctx}/community" class="btn btn-outline-secondary btn-sm me-3"><i class="bi bi-arrow-left"></i> 목록</a>
        <h2 class="fw-bold mb-0"><i class="bi bi-pencil-square text-primary me-2"></i>게시글 작성</h2>
    </div>

    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-4">
            <form action="${ctx}/community" method="post">
                <div class="mb-3">
                    <label class="form-label fw-bold">제목</label>
                    <input type="text" name="title" class="form-control" placeholder="제목을 입력하세요" required maxlength="100">
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold">내용</label>
                    <textarea name="content" class="form-control" rows="8" placeholder="내 굴비를 자랑하거나 절약 팁을 공유해보세요!" required></textarea>
                </div>
                <div class="d-flex justify-content-end gap-2">
                    <a href="${ctx}/community" class="btn btn-light rounded-pill px-4">취소</a>
                    <button type="submit" class="btn rounded-pill px-4" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">등록</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
