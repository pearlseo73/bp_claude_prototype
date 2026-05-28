/**
 * 자린고치 (Jaringochi) - Main Application JavaScript
 * 절약 습관을 만들어가는 게이미피케이션 가계부
 */

document.addEventListener('DOMContentLoaded', function () {
    'use strict';

    // ─────────────────────────────────────────
    // 초기화
    // ─────────────────────────────────────────
    fadeInElements();
    initTooltips();
    initActiveNavHighlight();

    console.log('🐟 자린고치 앱이 시작되었습니다!');
});

// ═════════════════════════════════════════════
// 유틸리티 함수
// ═════════════════════════════════════════════

/**
 * 금액을 한국 원화 형식으로 포맷팅
 * @param {number} amount - 금액
 * @returns {string} 포맷된 금액 (예: "1,234,567원")
 */
function formatCurrency(amount) {
    if (amount == null || isNaN(amount)) return '0원';
    return new Intl.NumberFormat('ko-KR').format(amount) + '원';
}

/**
 * 금액을 입력 필드용으로 포맷팅 (원 단위 없이 콤마만)
 * @param {number} amount - 금액
 * @returns {string} 포맷된 금액 (예: "1,234,567")
 */
function formatNumber(amount) {
    if (amount == null || isNaN(amount)) return '0';
    return new Intl.NumberFormat('ko-KR').format(amount);
}

/**
 * 포맷된 문자열에서 숫자만 추출
 * @param {string} str - 포맷된 금액 문자열
 * @returns {number} 숫자
 */
function parseCurrency(str) {
    if (!str) return 0;
    return parseInt(str.replace(/[^0-9-]/g, ''), 10) || 0;
}


// ═════════════════════════════════════════════
// 토스트 알림
// ═════════════════════════════════════════════

/**
 * Bootstrap 토스트 알림 표시
 * @param {string} message - 메시지 내용
 * @param {string} type - 알림 유형 ('success' | 'danger' | 'warning' | 'info')
 */
function showToast(message, type = 'info') {
    const container = document.getElementById('toastContainer');
    if (!container) return;

    const iconMap = {
        success: 'bi-check-circle-fill',
        danger: 'bi-exclamation-triangle-fill',
        warning: 'bi-exclamation-circle-fill',
        info: 'bi-info-circle-fill'
    };

    const labelMap = {
        success: '성공',
        danger: '오류',
        warning: '주의',
        info: '알림'
    };

    const icon = iconMap[type] || iconMap.info;
    const label = labelMap[type] || labelMap.info;

    const toastId = 'toast-' + Date.now();
    const toastHTML = `
        <div id="${toastId}" class="toast jrc-toast align-items-center text-bg-${type} border-0"
             role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="4000">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="bi ${icon} me-2"></i>
                    <strong>${label}:</strong> ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast" aria-label="닫기"></button>
            </div>
        </div>
    `;

    container.insertAdjacentHTML('beforeend', toastHTML);

    const toastElement = document.getElementById(toastId);
    const bsToast = new bootstrap.Toast(toastElement);
    bsToast.show();

    // 자동 제거
    toastElement.addEventListener('hidden.bs.toast', function () {
        toastElement.remove();
    });
}


// ═════════════════════════════════════════════
// 확인 다이얼로그
// ═════════════════════════════════════════════

/**
 * 삭제 확인 다이얼로그
 * @param {string} message - 확인 메시지
 * @returns {boolean} 사용자 확인 여부
 */
function confirmDelete(message = '정말로 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.') {
    return confirm(message);
}

/**
 * 일반 확인 다이얼로그
 * @param {string} message - 확인 메시지
 * @returns {boolean} 사용자 확인 여부
 */
function confirmAction(message = '계속 진행하시겠습니까?') {
    return confirm(message);
}


// ═════════════════════════════════════════════
// 예산 계산
// ═════════════════════════════════════════════

/**
 * 예산 대비 지출 비율 계산
 * @param {number} budget - 예산 금액
 * @param {number} expense - 지출 금액
 * @returns {number} 퍼센트 (0~100, 초과 시 100 이상)
 */
function calculateBudgetPercent(budget, expense) {
    if (!budget || budget <= 0) return 0;
    const percent = (expense / budget) * 100;
    return Math.round(percent * 10) / 10; // 소수점 1자리
}

/**
 * 예산 상태에 따른 색상 클래스 반환
 * @param {number} percent - 예산 사용 퍼센트
 * @returns {string} CSS 클래스
 */
function getBudgetStatusClass(percent) {
    if (percent >= 100) return 'bg-danger';
    if (percent >= 80) return 'bg-warning';
    if (percent >= 50) return 'bg-info';
    return 'bg-success';
}

/**
 * 예산 상태 텍스트 반환
 * @param {number} percent - 예산 사용 퍼센트
 * @returns {string} 상태 텍스트
 */
function getBudgetStatusText(percent) {
    if (percent >= 100) return '🚨 예산 초과!';
    if (percent >= 80) return '⚠️ 주의 필요';
    if (percent >= 50) return '📊 적정 수준';
    return '✅ 여유로워요';
}


// ═════════════════════════════════════════════
// 애니메이션
// ═════════════════════════════════════════════

/**
 * 숫자 카운팅 애니메이션
 * @param {HTMLElement} element - 대상 요소
 * @param {number} target - 목표 숫자
 * @param {number} duration - 애니메이션 시간 (ms)
 * @param {boolean} isCurrency - 원화 포맷 적용 여부
 */
function animateCounter(element, target, duration = 1500, isCurrency = true) {
    if (!element) return;

    const start = 0;
    const startTime = performance.now();

    function easeOutQuart(t) {
        return 1 - Math.pow(1 - t, 4);
    }

    function update(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);
        const easedProgress = easeOutQuart(progress);
        const current = Math.floor(start + (target - start) * easedProgress);

        element.textContent = isCurrency ? formatCurrency(current) : formatNumber(current);

        if (progress < 1) {
            requestAnimationFrame(update);
        }
    }

    requestAnimationFrame(update);
}

/**
 * IntersectionObserver를 이용한 스크롤 시 fade-in 애니메이션
 */
function fadeInElements() {
    const targets = document.querySelectorAll('.fade-in-up, .fade-in');

    if (targets.length === 0) return;

    const observer = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry, index) => {
                if (entry.isIntersecting) {
                    // 순차적 딜레이 적용
                    setTimeout(() => {
                        entry.target.classList.add('is-visible');
                    }, index * 80);
                    observer.unobserve(entry.target);
                }
            });
        },
        {
            threshold: 0.1,
            rootMargin: '0px 0px -30px 0px'
        }
    );

    targets.forEach((target) => observer.observe(target));
}


// ═════════════════════════════════════════════
// Bootstrap 초기화 헬퍼
// ═════════════════════════════════════════════

/**
 * Bootstrap 툴팁 초기화
 */
function initTooltips() {
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipTriggerList.forEach((el) => new bootstrap.Tooltip(el));
}

/**
 * 현재 경로 기반 네비게이션 메뉴 활성화
 */
function initActiveNavHighlight() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');

    navLinks.forEach((link) => {
        const href = link.getAttribute('href');
        if (href && currentPath.includes(href) && href !== '#') {
            link.classList.add('active');
            // 드롭다운 부모도 활성화
            const parentDropdown = link.closest('.dropdown');
            if (parentDropdown) {
                const toggle = parentDropdown.querySelector('.dropdown-toggle');
                if (toggle) toggle.classList.add('active');
            }
        }
    });
}


// ═════════════════════════════════════════════
// 폼 유효성 검사
// ═════════════════════════════════════════════

/**
 * 필수 입력 필드 검증
 * @param {string} formId - 폼 ID
 * @returns {boolean} 유효 여부
 */
function validateRequired(formId) {
    const form = document.getElementById(formId);
    if (!form) return false;

    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');

    requiredFields.forEach((field) => {
        removeFieldError(field);

        if (!field.value.trim()) {
            showFieldError(field, '필수 입력 항목입니다.');
            isValid = false;
        }
    });

    return isValid;
}

/**
 * 금액 필드 검증 (양수만 허용)
 * @param {HTMLElement} field - 입력 필드
 * @returns {boolean} 유효 여부
 */
function validateAmount(field) {
    const value = parseCurrency(field.value);
    if (isNaN(value) || value <= 0) {
        showFieldError(field, '올바른 금액을 입력해주세요.');
        return false;
    }
    removeFieldError(field);
    return true;
}

/**
 * 이메일 형식 검증
 * @param {HTMLElement} field - 이메일 입력 필드
 * @returns {boolean} 유효 여부
 */
function validateEmail(field) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(field.value)) {
        showFieldError(field, '올바른 이메일 주소를 입력해주세요.');
        return false;
    }
    removeFieldError(field);
    return true;
}

/**
 * 필드 오류 메시지 표시
 * @param {HTMLElement} field - 대상 입력 필드
 * @param {string} message - 오류 메시지
 */
function showFieldError(field, message) {
    field.classList.add('is-invalid');
    let feedback = field.nextElementSibling;
    if (!feedback || !feedback.classList.contains('invalid-feedback')) {
        feedback = document.createElement('div');
        feedback.className = 'invalid-feedback';
        field.parentNode.insertBefore(feedback, field.nextSibling);
    }
    feedback.textContent = message;
}

/**
 * 필드 오류 메시지 제거
 * @param {HTMLElement} field - 대상 입력 필드
 */
function removeFieldError(field) {
    field.classList.remove('is-invalid');
    const feedback = field.nextElementSibling;
    if (feedback && feedback.classList.contains('invalid-feedback')) {
        feedback.remove();
    }
}


// ═════════════════════════════════════════════
// AJAX 헬퍼
// ═════════════════════════════════════════════

/**
 * AJAX POST 요청 헬퍼
 * @param {string} url - 요청 URL
 * @param {Object} data - 전송 데이터
 * @param {Object} options - 추가 옵션
 * @returns {Promise<Object>} 응답 데이터
 */
async function ajaxPost(url, data = {}, options = {}) {
    const {
        contentType = 'application/x-www-form-urlencoded',
        showLoading = true,
        successMessage = null,
        errorMessage = '요청 처리 중 오류가 발생했습니다.'
    } = options;

    try {
        if (showLoading) showLoadingOverlay();

        let body;
        let headers = {};

        if (contentType === 'application/json') {
            body = JSON.stringify(data);
            headers['Content-Type'] = 'application/json';
        } else {
            body = new URLSearchParams(data).toString();
            headers['Content-Type'] = 'application/x-www-form-urlencoded';
        }

        const response = await fetch(url, {
            method: 'POST',
            headers: headers,
            body: body
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const result = await response.json();

        if (successMessage) {
            showToast(successMessage, 'success');
        }

        return result;

    } catch (error) {
        console.error('AJAX POST Error:', error);
        showToast(errorMessage, 'danger');
        throw error;

    } finally {
        if (showLoading) hideLoadingOverlay();
    }
}

/**
 * AJAX GET 요청 헬퍼
 * @param {string} url - 요청 URL
 * @param {Object} params - 쿼리 파라미터
 * @returns {Promise<Object>} 응답 데이터
 */
async function ajaxGet(url, params = {}) {
    try {
        const queryString = new URLSearchParams(params).toString();
        const fullUrl = queryString ? `${url}?${queryString}` : url;

        const response = await fetch(fullUrl, {
            method: 'GET',
            headers: { 'Accept': 'application/json' }
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        return await response.json();

    } catch (error) {
        console.error('AJAX GET Error:', error);
        showToast('데이터를 불러오는 중 오류가 발생했습니다.', 'danger');
        throw error;
    }
}


// ═════════════════════════════════════════════
// 로딩 오버레이
// ═════════════════════════════════════════════

/**
 * 로딩 오버레이 표시
 */
function showLoadingOverlay() {
    let overlay = document.getElementById('loadingOverlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.id = 'loadingOverlay';
        overlay.className = 'jrc-loading-overlay';
        overlay.innerHTML = `
            <div class="jrc-loading-spinner">
                <div class="spinner-border text-light" role="status">
                    <span class="visually-hidden">로딩 중...</span>
                </div>
                <p class="text-light mt-3 fw-medium">잠시만 기다려주세요...</p>
            </div>
        `;
        document.body.appendChild(overlay);
    }
    overlay.classList.add('show');
}

/**
 * 로딩 오버레이 숨기기
 */
function hideLoadingOverlay() {
    const overlay = document.getElementById('loadingOverlay');
    if (overlay) {
        overlay.classList.remove('show');
    }
}


// ═════════════════════════════════════════════
// 날짜 유틸리티
// ═════════════════════════════════════════════

/**
 * 날짜를 한국어 형식으로 포맷팅
 * @param {string|Date} date - 날짜
 * @returns {string} 포맷된 날짜 (예: "2026년 5월 27일")
 */
function formatDateKR(date) {
    const d = new Date(date);
    return `${d.getFullYear()}년 ${d.getMonth() + 1}월 ${d.getDate()}일`;
}

/**
 * 오늘 날짜를 YYYY-MM-DD 형식으로 반환
 * @returns {string}
 */
function getTodayString() {
    return new Date().toISOString().split('T')[0];
}

/**
 * 이번 달의 첫날을 YYYY-MM-DD 형식으로 반환
 * @returns {string}
 */
function getFirstDayOfMonth() {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-01`;
}
