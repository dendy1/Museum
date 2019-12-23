package ru.vsu.museum.servlets;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExponentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService;

    public void init() {
        categoryService = new CategoryService();
    }

    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/category/list.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/category/add.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("categoryId"));
        Category category = categoryService.getById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/views/category/edit.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("categoryName");
        String description = request.getParameter("categoryDescription");
        Category category = new Category(null, name, description);
        categoryService.add(category);
        response.sendRedirect("/category/list");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("categoryId"));
        Category category = categoryService.getById(id);
        category.setName(request.getParameter("categoryName"));
        category.setDescription(request.getParameter("categoryDescription"));
        categoryService.update(category);
        response.sendRedirect("/category/list");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("categoryId"));
        categoryService.deleteById(id);
        response.sendRedirect("/category/list");
    }
}
