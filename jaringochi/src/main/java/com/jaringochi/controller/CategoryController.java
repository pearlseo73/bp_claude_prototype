package com.jaringochi.controller;

import com.jaringochi.domain.Category;
import com.jaringochi.domain.User;
import com.jaringochi.service.CategoryService;
import com.jaringochi.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/categories/*")
public class CategoryController extends HttpServlet {
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        req.setAttribute("categories", categoryService.getCategories(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/category/manage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.getLoginUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        
        String pathInfo = req.getPathInfo();
        if ("/new".equals(pathInfo)) {
            Category cat = new Category();
            cat.setUserId(user.getId());
            cat.setName(req.getParameter("name"));
            cat.setType(req.getParameter("type"));
            categoryService.addCategory(cat);
        } else if ("/delete".equals(pathInfo)) {
            categoryService.deleteCategory(Long.parseLong(req.getParameter("id")));
        }
        resp.sendRedirect(req.getContextPath() + "/categories");
    }
}
