<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:860px;">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold"><i class="bi bi-chat-square-text text-primary me-2"></i>커뮤니티</h2>
        <a href="${ctx}/community/new" class="btn rounded-pill px-4" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
            <i class="bi bi-pencil me-1"></i> 글쓰기
        </a>
    </div>

    <!-- 베스트 고치 -->
    <c:if test="${not empty bestPosts}">
        <div class="card border-0 shadow-sm mb-4" style="border-radius:16px; background:linear-gradient(135deg, #ff8c42 0%, #ffaa6e 100%); color:white;">
            <div class="card-body p-4">
                <h5 class="fw-bold mb-3"><i class="bi bi-trophy me-1"></i> 베스트 고치</h5>
                <div class="row g-3">
                    <c:forEach var="p" items="${bestPosts}" varStatus="st">
                        <div class="col-md-4">
                            <a href="${ctx}/community/view?id=${p.id}" class="text-decoration-none">
                                <div class="bg-white text-dark p-3 h-100" style="border-radius:12px;">
                                    <div class="fw-bold text-truncate">${st.index + 1}. ${p.title}</div>
                                    <div class="small text-muted mt-1">${p.nickname}</div>
                                    <div class="small mt-2" style="color:#e07530;"><i class="bi bi-heart-fill"></i> ${p.likeCount}</div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>

    <!-- 게시글 목록 -->
    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty posts}">
                    <div class="text-center py-5 text-muted">
                        <div style="font-size:3.5rem;">📝</div>
                        <p class="mt-3">아직 게시글이 없어요. 첫 글을 작성해보세요!</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="p" items="${posts}">
                            <li class="list-group-item px-4 py-3">
                                <a href="${ctx}/community/view?id=${p.id}" class="text-decoration-none text-dark">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <div class="fw-bold">${p.title}</div>
                                            <div class="small text-muted mt-1">
                                                <i class="bi bi-person-circle me-1"></i>${p.nickname}
                                                <span class="ms-2"><i class="bi bi-clock me-1"></i>${p.createdAt}</span>
                                            </div>
                                        </div>
                                        <div class="text-end text-nowrap ms-3">
                                            <span class="me-2" style="color:#e74c3c;"><i class="bi bi-heart-fill"></i> ${p.likeCount}</span>
                                            <span class="text-muted"><i class="bi bi-chat"></i> ${p.commentCount}</span>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
