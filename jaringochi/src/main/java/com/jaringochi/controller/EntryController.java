package com.jaringochi.controller;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.domain.User;
import com.jaringochi.service.CategoryService;
import com.jaringochi.service.EntryService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/entries/*")
public class EntryController extends HttpServlet {
    private EntryService entryService = new EntryService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            req.setAttribute("entries", entryService.getEntries(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/entry/list.jsp").forward(req, resp);
        } else if ("/new".equals(pathInfo)) {
            req.setAttribute("categories", categoryService.getCategories(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/entry/form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            AccountBookEntry entry = new AccountBookEntry();
            entry.setUserId(user.getId());
            entry.setType(req.getParameter("type"));
            entry.setAmount(Integer.parseInt(req.getParameter("amount")));
            entry.setCategoryId(Long.parseLong(req.getParameter("categoryId")));
            entry.setEntryDate(LocalDate.parse(req.getParameter("entryDate")));
            entry.setMemo(req.getParameter("memo"));
            
            entryService.addEntry(entry);
            
            if ("EXPENSE".equals(entry.getType())) {
                double usage = entryService.calculateBudgetUsage(user.getId());
                String msg = entryService.generateGulbiMessage(usage, "기본");
                req.getSession().setAttribute("flashMessage", msg);
            }
            
            resp.sendRedirect(req.getContextPath() + "/entries");
        } else if ("/delete".equals(pathInfo)) {
            entryService.deleteEntry(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/entries");
        }
    }
}
