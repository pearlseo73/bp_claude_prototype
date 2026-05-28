package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.CommunityService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/community/*")
public class CommunityController extends HttpServlet {
    private final CommunityService communityService = new CommunityService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/new".equals(pathInfo)) {
            req.getRequestDispatcher("/WEB-INF/views/community/form.jsp").forward(req, resp);

        } else if ("/view".equals(pathInfo)) {
            long postId = parseLong(req.getParameter("id"));
            req.setAttribute("post", communityService.getPost(postId));
            req.setAttribute("comments", communityService.getComments(postId));
            req.setAttribute("liked", communityService.hasLiked(postId, user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/community/detail.jsp").forward(req, resp);

        } else if ("/best".equals(pathInfo)) {
            req.setAttribute("posts", communityService.getBestPosts(20));
            req.setAttribute("bestPosts", communityService.getBestPosts(3));
            req.setAttribute("bestOnly", true);
            req.getRequestDispatcher("/WEB-INF/views/community/list.jsp").forward(req, resp);

        } else {
            req.setAttribute("posts", communityService.getAllPosts());
            req.setAttribute("bestPosts", communityService.getBestPosts(3));
            req.getRequestDispatcher("/WEB-INF/views/community/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();
        if ("/comment".equals(pathInfo)) {
            long postId = parseLong(req.getParameter("postId"));
            String content = req.getParameter("content");
            if (content != null && !content.trim().isEmpty()) {
                communityService.addComment(postId, user.getId(), content.trim());
            }
            resp.sendRedirect(req.getContextPath() + "/community/view?id=" + postId);

        } else if ("/like".equals(pathInfo)) {
            long postId = parseLong(req.getParameter("postId"));
            communityService.toggleLike(postId, user.getId());
            resp.sendRedirect(req.getContextPath() + "/community/view?id=" + postId);

        } else {
            // 게시글 작성
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
                long newId = communityService.createPost(user.getId(), title.trim(), content.trim());
                resp.sendRedirect(req.getContextPath() + "/community/view?id=" + newId);
            } else {
                resp.sendRedirect(req.getContextPath() + "/community/new");
            }
        }
    }

    private long parseLong(String s) {
        try { return Long.parseLong(s); } catch (Exception e) { return -1; }
    }
}
