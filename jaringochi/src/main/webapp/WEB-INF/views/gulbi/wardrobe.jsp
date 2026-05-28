<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>

<div class="container mt-4">
    <div class="d-flex align-items-center mb-4">
        <a href="${ctx}/gulbi" class="btn btn-outline-secondary btn-sm me-3">
            <i class="bi bi-arrow-left"></i> 돌아가기
        </a>
        <h2 class="fw-bold mb-0"><i class="bi bi-bag me-2 text-primary"></i>굴비 옷장</h2>
    </div>

    <!-- 현재 착용 중 -->
    <div class="card border-0 shadow-sm mb-4" style="border-radius: 16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">현재 착용 중</h5>
            <c:choose>
                <c:when test="${gulbi.activeClothesId != null}">
                    <div class="d-flex align-items-center gap-3">
                        <span class="badge rounded-pill px-3 py-2" style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); font-size: 0.9rem;">
                            의상 #${gulbi.activeClothesId}
                        </span>
                        <form action="${ctx}/gulbi/wardrobe/equip" method="post" class="d-inline">
                            <input type="hidden" name="clothesId" value="">
                            <button type="submit" class="btn btn-outline-danger btn-sm rounded-pill">
                                <i class="bi bi-x-circle me-1"></i> 탈의하기
                            </button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-muted mb-0"><i class="bi bi-info-circle me-1"></i> 착용 중인 의상이 없어요.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 보유 의상 목록 -->
    <div class="card border-0 shadow-sm" style="border-radius: 16px;">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-4">보유 의상</h5>
            <c:choose>
                <c:when test="${empty clothesList}">
                    <div class="text-center py-5">
                        <div style="font-size: 4rem;">👘</div>
                        <h5 class="fw-bold mt-3 text-muted">아직 의상이 없어요!</h5>
                        <p class="text-muted">절약 목표를 달성하면 굴비에게 새 옷을 선물할 수 있어요.</p>
                        <a href="${ctx}/gulbi" class="btn rounded-pill mt-2" style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color: white;">
                            굴비 상태 보기
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row g-3">
                        <c:forEach var="clothes" items="${clothesList}">
                            <div class="col-6 col-md-3">
                                <div class="card border h-100 text-center p-3" style="border-radius: 12px; transition: box-shadow 0.2s;"
                                     onmouseover="this.style.boxShadow='0 4px 16px rgba(26,107,90,0.2)'"
                                     onmouseout="this.style.boxShadow='none'">
                                    <c:choose>
                                        <c:when test="${not empty clothes.imagePath}">
                                            <img src="${ctx}${clothes.imagePath}" alt="${clothes.name}"
                                                 class="mx-auto mb-2" style="height: 80px; object-fit: contain;">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mx-auto mb-2 d-flex align-items-center justify-content-center"
                                                 style="height:80px; font-size:3rem;">👘</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="fw-bold small mb-2">${clothes.name}</div>
                                    <c:choose>
                                        <c:when test="${gulbi.activeClothesId == clothes.id}">
                                            <span class="badge rounded-pill" style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%);">
                                                착용 중
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="${ctx}/gulbi/wardrobe/equip" method="post">
                                                <input type="hidden" name="clothesId" value="${clothes.id}">
                                                <button type="submit" class="btn btn-sm rounded-pill w-100"
                                                        style="background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color: white; font-size: 0.8rem;">
                                                    착용하기
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
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
