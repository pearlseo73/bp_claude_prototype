<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <div class="d-flex align-items-center mb-4">
        <a href="${ctx}/gulbi" class="btn btn-outline-secondary btn-sm me-3">
            <i class="bi bi-arrow-left"></i> 돌아가기
        </a>
        <h2 class="fw-bold mb-0"><i class="bi bi-egg me-2 text-primary"></i>새끼 굴비</h2>
    </div>

    <!-- 부모 굴비 요약 -->
    <div class="card border-0 shadow-sm mb-4 p-4" style="border-radius: 16px; background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color: white;">
        <div class="d-flex align-items-center gap-3">
            <img src="${ctx}/static/images/gulbi_main.png" alt="굴비"
                 style="height: 60px; object-fit: contain;">
            <div>
                <div class="fw-bold fs-5">어미 굴비 (체중: ${gulbi.weight}kg)</div>
                <div class="small opacity-75">연속 기록 ${gulbi.streakRecord}일 &nbsp;|&nbsp; 예산 성공 ${gulbi.streakBudget}주</div>
            </div>
        </div>
    </div>

    <!-- 새끼 굴비 목록 -->
    <div class="card border-0 shadow-sm" style="border-radius: 16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-4">새끼 굴비 목록</h5>
            <c:choose>
                <c:when test="${empty childrenList}">
                    <div class="text-center py-5">
                        <div style="font-size: 4rem;">🐟</div>
                        <h5 class="fw-bold mt-3 text-muted">아직 새끼 굴비가 없어요!</h5>
                        <p class="text-muted">
                            예산을 꾸준히 지키면 새끼 굴비가 태어납니다.<br>
                            연속 예산 성공 <strong>4주</strong>마다 새끼 굴비 1마리씩 늘어나요!
                        </p>
                        <a href="${ctx}/budgets" class="btn rounded-pill mt-2" style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color: white;">
                            예산 설정하러 가기
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row g-4 justify-content-center">
                        <c:forEach var="child" items="${childrenList}">
                            <div class="col-12 col-md-5">
                                <div class="card border-0 shadow-sm text-center p-4" style="border-radius: 16px; background: linear-gradient(135deg, #ff8c42 0%, #ffaa6e 100%); color: white;">
                                    <img src="${ctx}/static/images/gulbi_main.png" alt="새끼 굴비"
                                         class="mx-auto mb-3" style="height: 100px; object-fit: contain; opacity: 0.9;">
                                    <h5 class="fw-bold">
                                        <c:choose>
                                            <c:when test="${child.childOrder == 1}">첫째 굴비</c:when>
                                            <c:when test="${child.childOrder == 2}">둘째 굴비</c:when>
                                            <c:otherwise>${child.childOrder}째 굴비</c:otherwise>
                                        </c:choose>
                                    </h5>
                                    <p class="mb-1 opacity-90">체중: <strong>${child.weight}kg</strong></p>
                                    <p class="small opacity-75 mb-0">
                                        획득일: ${child.acquiredAt != null ? child.acquiredAt.toLocalDate() : '알 수 없음'}
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
