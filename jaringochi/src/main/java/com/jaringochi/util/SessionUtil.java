package com.jaringochi.util;

import com.jaringochi.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 세션 관리 유틸리티 클래스
 */
public class SessionUtil {

    private static final String LOGIN_USER_KEY = "loginUser";

    /**
     * 세션에서 로그인 사용자 정보를 반환 (없으면 null)
     */
    public static User getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(LOGIN_USER_KEY);
    }

    /**
     * 세션에서 로그인 사용자 ID를 반환 (없으면 -1)
     */
    public static long getLoginUserId(HttpServletRequest req) {
        User user = getLoginUser(req);
        return user != null ? user.getId() : -1;
    }

    /**
     * 세션에 로그인 사용자 정보를 저장
     */
    public static void setLoginUser(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute(LOGIN_USER_KEY, user);
    }

    /**
     * 세션을 무효화하여 로그인 사용자 정보를 제거
     */
    public static void removeLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 로그인 여부를 확인
     */
    public static boolean isLoggedIn(HttpServletRequest req) {
        return getLoginUser(req) != null;
    }
}
