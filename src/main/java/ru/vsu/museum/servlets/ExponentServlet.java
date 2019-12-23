package ru.vsu.museum.servlets;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExponentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExponentServlet", urlPatterns = "/exponent/*")
public class ExponentServlet extends BaseServlet {
    ExponentService exponentService;
    CategoryService categoryService;

    public void init() {
        exponentService = new ExponentService();
        categoryService = new CategoryService();
    }

    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Exponent> exponents = exponentService.getAll();
        request.setAttribute("exponents", exponents);
        request.getRequestDispatcher("/WEB-INF/views/exponent/list.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/exponent/add.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        Exponent exponent = exponentService.getById(Long.parseLong(request.getParameter("exponentId")));
        request.setAttribute("categories", categories);
        request.setAttribute("exponent", exponent);
        request.getRequestDispatcher("/WEB-INF/views/exponent/edit.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("exponentName");
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Exponent exponent = new Exponent(null, name, categoryId);
        exponentService.add(exponent);
        response.sendRedirect("/exponent/list");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("exponentId"));
        Exponent exponent = exponentService.getById(id);
        exponent.setName(request.getParameter("exponentName"));
        exponent.setCategoryId(Long.parseLong(request.getParameter("categoryId")));
        exponentService.update(exponent);
        response.sendRedirect("/exponent/list");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("exponentId"));
        exponentService.deleteById(id);
        response.sendRedirect("/exponent/list");
    }
}
