<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:680px;">
    <div class="mb-4">
        <a href="${ctx}/community" class="btn btn-outline-secondary btn-sm"><i class="bi bi-arrow-left"></i> 목록</a>
    </div>

    <c:choose>
        <c:when test="${post == null}">
            <div class="alert alert-warning rounded-3">존재하지 않는 게시글이에요.</div>
        </c:when>
        <c:otherwise>
            <!-- 게시글 본문 -->
            <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h3 class="fw-bold mb-2">${post.title}</h3>
                    <div class="text-muted small mb-3">
                        <i class="bi bi-person-circle me-1"></i>${post.nickname}
                        <span class="ms-2"><i class="bi bi-clock me-1"></i>${post.createdAt}</span>
                    </div>
                    <hr>
                    <p style="white-space:pre-wrap; line-height:1.8;">${post.content}</p>

                    <!-- 좋아요 -->
                    <div class="text-center mt-4">
                        <form action="${ctx}/community/like" method="post" class="d-inline">
                            <input type="hidden" name="postId" value="${post.id}">
                            <button type="submit" class="btn rounded-pill px-4 ${liked ? 'btn-danger' : 'btn-outline-danger'}">
                                <i class="bi ${liked ? 'bi-heart-fill' : 'bi-heart'} me-1"></i>
                                좋아요 ${post.likeCount}
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- 댓글 -->
            <div class="card border-0 shadow-sm" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3"><i class="bi bi-chat-dots me-1"></i> 댓글 ${post.commentCount}</h5>

                    <form action="${ctx}/community/comment" method="post" class="d-flex gap-2 mb-4">
                        <input type="hidden" name="postId" value="${post.id}">
                        <input type="text" name="content" class="form-control" placeholder="댓글을 입력하세요" required maxlength="255">
                        <button class="btn rounded-pill px-3" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">등록</button>
                    </form>

                    <c:choose>
                        <c:when test="${empty comments}">
                            <p class="text-center text-muted py-3">첫 댓글을 남겨보세요!</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="c" items="${comments}">
                                <div class="py-2 border-bottom">
                                    <div class="d-flex justify-content-between">
                                        <span class="fw-bold small">${c.nickname}</span>
                                        <span class="text-muted" style="font-size:0.75rem;">${c.createdAt}</span>
                                    </div>
                                    <div class="mt-1">${c.content}</div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
