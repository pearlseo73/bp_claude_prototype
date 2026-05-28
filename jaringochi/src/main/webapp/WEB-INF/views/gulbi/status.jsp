<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container mt-4 text-center">
    <h2 class="fw-bold mb-4">내 반려 굴비 상태</h2>

    <c:if test="${not empty msg}">
        <div class="alert alert-info rounded-3 mx-auto" style="max-width:600px;">${msg}</div>
    </c:if>

    <div class="card shadow-sm border-0 glass-card mx-auto p-5" style="max-width: 600px; background: linear-gradient(135deg, #1a6b5a 0%, #2d9b7a 100%); color: white;">
        <img src="${ctx}/static/images/gulbi_main.png" class="mx-auto mb-4" alt="굴비" style="height: 250px; object-fit: contain;">
        <h4 class="fw-bold">현재 체중: ${gulbi.weight}kg</h4>
        <p class="mb-2">종류: ${gulbi.gulbiType == 'TYPE_1' ? '기본 굴비' : (gulbi.gulbiType == 'TYPE_2' ? '날씬 굴비' : '통통 굴비')}</p>
        <p class="mb-4">성격: ${gulbi.personality == null ? '아직 성격이 없어요!' : gulbi.personality}</p>
        
        <div class="row text-center bg-white text-dark rounded-3 p-3 mx-2 mb-4 shadow-sm">
            <div class="col-6 border-end">
                <div class="small text-muted">연속 기록</div>
                <div class="fw-bold fs-5">${gulbi.streakRecord}일</div>
            </div>
            <div class="col-6">
                <div class="small text-muted">예산 성공</div>
                <div class="fw-bold fs-5">${gulbi.streakBudget}주</div>
            </div>
        </div>
        
        <div class="d-flex justify-content-center gap-2">
            <a href="${ctx}/gulbi/wardrobe" class="btn btn-light rounded-pill">
                <i class="bi bi-bag me-1"></i> 옷장 보기
            </a>
            <a href="${ctx}/gulbi/children" class="btn btn-outline-light rounded-pill">
                <i class="bi bi-egg me-1"></i> 새끼 굴비 보기
            </a>
        </div>
    </div>

    <!-- 성장 반영 -->
    <div class="mx-auto mt-4" style="max-width:600px;">
        <form action="${ctx}/gulbi/grow" method="post">
            <button class="btn btn-accent rounded-pill px-4" type="submit">
                <i class="bi bi-stars me-1"></i> 이번 주 성장 반영하기
            </button>
        </form>
        <p class="text-muted small mt-2">이번 주 기록·예산 성공 여부에 따라 굴비 체중과 보상이 갱신돼요.</p>
    </div>

    <!-- 성격 설정 -->
    <div class="card border-0 shadow-sm mx-auto mt-3" style="max-width:600px; border-radius:16px;">
        <div class="card-body p-4 text-start">
            <h5 class="fw-bold mb-3"><i class="bi bi-pencil me-1 text-primary"></i>굴비 성격 설정</h5>
            <form action="${ctx}/gulbi/personality" method="post">
                <textarea name="personality" class="form-control mb-3" rows="2"
                          placeholder="우리 굴비의 성격을 적어주세요 (예: 다정하고 응원해주는 성격)">${gulbi.personality}</textarea>
                <div class="text-end">
                    <button class="btn btn-primary rounded-pill px-4" type="submit">저장</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
