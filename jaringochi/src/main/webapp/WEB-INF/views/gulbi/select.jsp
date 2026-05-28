<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container vh-100 d-flex flex-column justify-content-center align-items-center">
    <div class="text-center mb-5">
        <h1 class="fw-bold text-primary">반려 굴비를 선택해주세요!</h1>
        <p class="text-muted">앞으로 나와 함께 절약 생활을 이어나갈 친구입니다.</p>
    </div>
    
    <div class="row g-4 justify-content-center w-100" style="max-width: 1000px;">
        <div class="col-md-4">
            <div class="card shadow-sm text-center h-100 border-0 p-3 hover-lift">
                <img src="${ctx}/static/images/gulbi_main.png" class="card-img-top mx-auto" alt="기본 굴비" style="height: 150px; object-fit: contain;">
                <div class="card-body">
                    <h5 class="fw-bold">기본 굴비</h5>
                    <p class="small text-muted">가장 스탠다드한 친구. 성실하게 저축합니다.</p>
                    <form action="${ctx}/gulbi/select" method="post">
                        <input type="hidden" name="gulbiType" value="TYPE_1">
                        <button type="submit" class="btn btn-primary w-100 rounded-pill">선택하기</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm text-center h-100 border-0 p-3 hover-lift">
                <img src="${ctx}/static/images/gulbi_main.png" class="card-img-top mx-auto" alt="날씬 굴비" style="height: 150px; object-fit: contain;">
                <div class="card-body">
                    <h5 class="fw-bold">날씬 굴비</h5>
                    <p class="small text-muted">극강의 짠테크를 선호하는 깐깐한 친구입니다.</p>
                    <form action="${ctx}/gulbi/select" method="post">
                        <input type="hidden" name="gulbiType" value="TYPE_2">
                        <button type="submit" class="btn btn-primary w-100 rounded-pill">선택하기</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm text-center h-100 border-0 p-3 hover-lift">
                <img src="${ctx}/static/images/gulbi_main.png" class="card-img-top mx-auto" alt="통통 굴비" style="height: 150px; object-fit: contain;">
                <div class="card-body">
                    <h5 class="fw-bold">통통 굴비</h5>
                    <p class="small text-muted">조금은 넉넉한 마음씨를 가진 부드러운 친구입니다.</p>
                    <form action="${ctx}/gulbi/select" method="post">
                        <input type="hidden" name="gulbiType" value="TYPE_3">
                        <button type="submit" class="btn btn-primary w-100 rounded-pill">선택하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
