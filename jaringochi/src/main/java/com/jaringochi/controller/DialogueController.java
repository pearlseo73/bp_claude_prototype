package com.jaringochi.controller;

import com.jaringochi.domain.User;
import com.jaringochi.service.DialogueService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dialogues")
public class DialogueController extends HttpServlet {
    private final DialogueService dialogueService = new DialogueService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        req.setAttribute("dialogues", dialogueService.getDialogues(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/dialogue/log.jsp").forward(req, resp);
    }
}
