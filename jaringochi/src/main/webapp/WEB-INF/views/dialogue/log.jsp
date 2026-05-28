<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:760px;">
    <h2 class="fw-bold mb-4"><i class="bi bi-chat-dots text-primary me-2"></i>굴비와의 대화 로그</h2>

    <div class="card border-0 shadow-sm" style="border-radius:16px;">
        <div class="card-body p-4">
            <c:choose>
                <c:when test="${empty dialogues}">
                    <div class="text-center py-5 text-muted">
                        <div style="font-size:3.5rem;">💬</div>
                        <p class="mt-3 mb-2">아직 굴비와 나눈 대화가 없어요.</p>
                        <a href="${ctx}/reports/monthly" class="btn btn-sm rounded-pill" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">월간 레포트에서 다짐 남기기</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="d" items="${dialogues}">
                        <div class="mb-4 pb-3 border-bottom">
                            <div class="text-muted small mb-2"><i class="bi bi-clock me-1"></i>${d.createdAt}</div>
                            <div class="d-flex justify-content-end mb-2">
                                <div class="p-2 px-3" style="background:#e9f5f1; border-radius:14px 14px 2px 14px; max-width:75%;">
                                    ${d.userMessage}
                                </div>
                            </div>
                            <div class="d-flex justify-content-start align-items-end gap-2">
                                <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" style="height:32px; object-fit:contain;">
                                <div class="p-2 px-3" style="background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white; border-radius:14px 14px 14px 2px; max-width:75%;">
                                    ${d.gulbiReply}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
