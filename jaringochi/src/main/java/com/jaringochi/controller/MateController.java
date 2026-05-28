package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.MateService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/mates/*")
public class MateController extends HttpServlet {
    private final MateService mateService = new MateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/view".equals(pathInfo)) {
            Long mateId = mateService.getMateUserId(user.getId());
            if (mateId == null) { resp.sendRedirect(req.getContextPath() + "/mates"); return; }
            req.setAttribute("mateUser", mateService.getMateUser(user.getId()));
            req.setAttribute("mateGulbi", mateService.getMateGulbi(mateId));
            req.setAttribute("mateChildren", mateService.getMateChildren(mateId));
            req.setAttribute("mateClothes", mateService.getMateClothes(mateId));
            req.getRequestDispatcher("/WEB-INF/views/mate/view.jsp").forward(req, resp);
        } else {
            req.setAttribute("mateUser", mateService.getMateUser(user.getId()));
            req.setAttribute("receivedRequests", mateService.getReceivedRequests(user.getId()));
            req.setAttribute("sentRequests", mateService.getSentRequests(user.getId()));
            req.setAttribute("msg", req.getParameter("msg"));
            req.getRequestDispatcher("/WEB-INF/views/mate/main.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        String msg = null;
        if ("/request".equals(pathInfo)) {
            String result = mateService.requestByUsername(user.getId(), req.getParameter("username"));
            msg = (result == null) ? "메이트 요청을 보냈어요!" : result;
        } else if ("/accept".equals(pathInfo)) {
            mateService.acceptRequest(parseLong(req.getParameter("requestId")), user.getId());
            msg = "메이트를 수락했어요!";
        } else if ("/reject".equals(pathInfo)) {
            mateService.rejectRequest(parseLong(req.getParameter("requestId")), user.getId());
            msg = "요청을 거절했어요.";
        } else if ("/remove".equals(pathInfo)) {
            mateService.removeMate(user.getId());
            msg = "메이트를 해제했어요.";
        }
        String q = (msg != null) ? "?msg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8) : "";
        resp.sendRedirect(req.getContextPath() + "/mates" + q);
    }

    private long parseLong(String s) {
        try { return Long.parseLong(s); } catch (Exception e) { return -1; }
    }
}
