package com.jaringochi.filter;

import com.jaringochi.util.SessionUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 로그인 체크 필터
 * 로그인하지 않은 사용자의 접근을 제한하고 로그인 페이지로 리다이렉트
 */
@WebFilter("/*")
public class LoginCheckFilter implements Filter {

    // 로그인 없이 접근 가능한 경로 목록
    private static final String[] EXCLUDE_PATHS = {
            "/auth/",
            "/static/",
            "/index.jsp"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 불필요
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String requestURI = httpReq.getRequestURI();
        String contextPath = httpReq.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // 루트 경로 허용
        if ("/".equals(path) || path.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        // 제외 경로인지 확인
        for (String excludePath : EXCLUDE_PATHS) {
            if (path.startsWith(excludePath)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 로그인 여부 확인
        if (SessionUtil.isLoggedIn(httpReq)) {
            chain.doFilter(request, response);
        } else {
            httpResp.sendRedirect(contextPath + "/auth/login");
        }
    }

    @Override
    public void destroy() {
        // 정리 불필요
    }
}
