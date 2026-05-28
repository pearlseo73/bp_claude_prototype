<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:760px;">
    <h2 class="fw-bold mb-4"><i class="bi bi-people-fill text-primary me-2"></i>메이트</h2>

    <c:if test="${not empty msg}">
        <div class="alert alert-info rounded-3">${msg}</div>
    </c:if>

    <!-- 현재 메이트 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">나의 메이트</h5>
            <c:choose>
                <c:when test="${mateUser != null}">
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="d-flex align-items-center gap-3">
                            <span class="d-flex align-items-center justify-content-center rounded-circle"
                                  style="width:52px; height:52px; background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white; font-size:1.5rem;">
                                <i class="bi bi-person"></i>
                            </span>
                            <div>
                                <div class="fw-bold fs-5">${mateUser.nickname}</div>
                                <div class="text-muted small">@${mateUser.username}</div>
                            </div>
                        </div>
                        <div class="d-flex gap-2">
                            <a href="${ctx}/mates/view" class="btn btn-sm rounded-pill" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
                                <i class="bi bi-eye me-1"></i> 옆집 굴비 보기
                            </a>
                            <form action="${ctx}/mates/remove" method="post" onsubmit="return confirm('메이트를 해제할까요?');">
                                <button class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-x-circle me-1"></i> 해제</button>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-muted mb-3">아직 메이트가 없어요. 친구의 아이디로 메이트를 신청해보세요!</p>
                    <form action="${ctx}/mates/request" method="post" class="d-flex gap-2">
                        <input type="text" name="username" class="form-control" placeholder="메이트로 추가할 상대 아이디 (예: test2)" required>
                        <button class="btn rounded-pill px-4" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">요청</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 받은 요청 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">받은 요청</h5>
            <c:choose>
                <c:when test="${empty receivedRequests}">
                    <p class="text-muted mb-0">받은 메이트 요청이 없어요.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="r" items="${receivedRequests}">
                        <div class="d-flex justify-content-between align-items-center py-2 border-bottom">
                            <span><strong>${r.fromNickname}</strong>님의 메이트 요청</span>
                            <div class="d-flex gap-2">
                                <form action="${ctx}/mates/accept" method="post">
                                    <input type="hidden" name="requestId" value="${r.id}">
                                    <button class="btn btn-sm btn-success rounded-pill">수락</button>
                                </form>
                                <form action="${ctx}/mates/reject" method="post">
                                    <input type="hidden" name="requestId" value="${r.id}">
                                    <button class="btn btn-sm btn-outline-secondary rounded-pill">거절</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 보낸 요청 -->
    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">보낸 요청</h5>
            <c:choose>
                <c:when test="${empty sentRequests}">
                    <p class="text-muted mb-0">보낸 메이트 요청이 없어요.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="r" items="${sentRequests}">
                        <div class="d-flex justify-content-between align-items-center py-2 border-bottom">
                            <span><strong>${r.toNickname}</strong>님에게 요청함</span>
                            <span class="badge rounded-pill bg-light text-muted">대기 중</span>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
