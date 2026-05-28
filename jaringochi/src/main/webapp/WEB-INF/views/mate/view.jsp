<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4" style="max-width:760px;">
    <div class="d-flex align-items-center mb-4">
        <a href="${ctx}/mates" class="btn btn-outline-secondary btn-sm me-3"><i class="bi bi-arrow-left"></i> 돌아가기</a>
        <h2 class="fw-bold mb-0"><i class="bi bi-house-heart text-primary me-2"></i>${mateUser.nickname}님의 굴비</h2>
    </div>

    <!-- 메이트 굴비 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius:16px; background:linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color:white;">
        <div class="card-body p-4 text-center">
            <img src="${ctx}/static/images/gulbi_main.png" alt="굴비" style="height:160px; object-fit:contain;">
            <c:choose>
                <c:when test="${mateGulbi != null}">
                    <h4 class="fw-bold mt-3">체중: ${mateGulbi.weight}kg</h4>
                    <p class="mb-1" style="opacity:0.9;">
                        종류:
                        <c:choose>
                            <c:when test="${mateGulbi.gulbiType == 'TYPE_1'}">기본 굴비</c:when>
                            <c:when test="${mateGulbi.gulbiType == 'TYPE_2'}">날씬 굴비</c:when>
                            <c:otherwise>통통 굴비</c:otherwise>
                        </c:choose>
                    </p>
                    <p class="small" style="opacity:0.8;">연속 기록 ${mateGulbi.streakRecord}일 · 예산 성공 ${mateGulbi.streakBudget}주</p>
                </c:when>
                <c:otherwise>
                    <p class="mt-3">아직 굴비를 키우지 않았어요.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="row g-4">
        <!-- 새끼 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3"><i class="bi bi-egg me-1"></i> 새끼 굴비</h5>
                    <c:choose>
                        <c:when test="${empty mateChildren}">
                            <p class="text-muted mb-0">아직 새끼 굴비가 없어요.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="child" items="${mateChildren}">
                                <div class="d-flex justify-content-between py-1">
                                    <span>
                                        <c:choose>
                                            <c:when test="${child.childOrder == 1}">첫째</c:when>
                                            <c:when test="${child.childOrder == 2}">둘째</c:when>
                                            <c:otherwise>${child.childOrder}째</c:otherwise>
                                        </c:choose> 굴비
                                    </span>
                                    <span class="fw-bold">${child.weight}kg</span>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <!-- 의상 -->
        <div class="col-md-6">
            <div class="card border-0 shadow-sm h-100" style="border-radius:16px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3"><i class="bi bi-bag me-1"></i> 보유 의상 (${mateClothes.size()}개)</h5>
                    <c:choose>
                        <c:when test="${empty mateClothes}">
                            <p class="text-muted mb-0">보유한 의상이 없어요.</p>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex flex-wrap gap-2">
                                <c:forEach var="clo" items="${mateClothes}">
                                    <span class="badge rounded-pill" style="background:#e9f5f1; color:#1a6b5a;">${clo.name}</span>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
