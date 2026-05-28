package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.GulbiService;
import com.jaringochi.service.RewardService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/gulbi/*")
public class GulbiController extends HttpServlet {
    private GulbiService gulbiService = new GulbiService();
    private RewardService rewardService = new RewardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();

        if ("/select".equals(pathInfo)) {
            if (gulbiService.hasGulbi(user.getId())) {
                resp.sendRedirect(req.getContextPath() + "/gulbi");
                return;
            }
            req.getRequestDispatcher("/WEB-INF/views/gulbi/select.jsp").forward(req, resp);

        } else if ("/wardrobe".equals(pathInfo)) {
            if (!gulbiService.hasGulbi(user.getId())) {
                resp.sendRedirect(req.getContextPath() + "/gulbi/select"); return;
            }
            req.setAttribute("gulbi", gulbiService.getGulbi(user.getId()));
            req.setAttribute("clothesList", gulbiService.getClothes(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/gulbi/wardrobe.jsp").forward(req, resp);

        } else if ("/children".equals(pathInfo)) {
            if (!gulbiService.hasGulbi(user.getId())) {
                resp.sendRedirect(req.getContextPath() + "/gulbi/select"); return;
            }
            req.setAttribute("gulbi", gulbiService.getGulbi(user.getId()));
            req.setAttribute("childrenList", gulbiService.getChildren(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/gulbi/children.jsp").forward(req, resp);

        } else {
            if (!gulbiService.hasGulbi(user.getId())) {
                resp.sendRedirect(req.getContextPath() + "/gulbi/select"); return;
            }
            req.setAttribute("gulbi", gulbiService.getGulbi(user.getId()));
            req.setAttribute("msg", req.getParameter("msg"));
            req.getRequestDispatcher("/WEB-INF/views/gulbi/status.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        String pathInfo = req.getPathInfo();

        if ("/select".equals(pathInfo)) {
            gulbiService.selectGulbi(user.getId(), req.getParameter("gulbiType"));
            resp.sendRedirect(req.getContextPath() + "/gulbi");

        } else if ("/wardrobe/equip".equals(pathInfo)) {
            String clothesIdStr = req.getParameter("clothesId");
            Long clothesId = (clothesIdStr != null && !clothesIdStr.isEmpty())
                    ? Long.parseLong(clothesIdStr) : null;
            gulbiService.equipClothes(user.getId(), clothesId);
            resp.sendRedirect(req.getContextPath() + "/gulbi/wardrobe");

        } else if ("/personality".equals(pathInfo)) {
            gulbiService.setPersonality(user.getId(), req.getParameter("personality"));
            resp.sendRedirect(req.getContextPath() + "/gulbi");

        } else if ("/grow".equals(pathInfo)) {
            String result = rewardService.applyWeeklyGrowth(user.getId());
            resp.sendRedirect(req.getContextPath() + "/gulbi?msg="
                    + URLEncoder.encode(result, StandardCharsets.UTF_8));
        }
    }
}
