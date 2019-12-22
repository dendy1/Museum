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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ExponentController", urlPatterns = "/exponents/*")
public class ExponentController extends HttpServlet {

    ExponentService exponentService;
    CategoryService categoryService;

    public void init()
    {
        exponentService = new ExponentService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println(request.getParameter("categoryName") + ";" + request.getParameter("categoryId"));
        //doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = request.getServletPath();

        try {
            switch (action) {
                case "/add":
                    showAddForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/insert":
                    System.out.println("insert");
                    insertExponent(request, response);
                    break;
                case "/delete":
                    deleteExponent(request, response);
                    break;
                case "/update":
                    updateExponent(request, response);
                    break;
                default:
                    listExponent(request, response);
                    break;
            }
        } catch (SQLException ex) {
                throw new ServletException(ex);
        }
    }

    private void insertExponent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String name = request.getParameter("name");
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Exponent exponent = new Exponent(null, name, categoryId);
        exponentService.add(exponent);
        response.sendRedirect("/exponents");
    }

    private void updateExponent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Long id = Long.parseLong(request.getParameter("id"));
        Exponent exponent = exponentService.getById(id);
        exponent.setExponentId(id);
        exponent.setName(request.getParameter("name"));
        exponent.setCategoryId(Long.parseLong(request.getParameter("categoryId")));
        exponentService.update(exponent);
        response.sendRedirect("/exponents");
    }

    private void deleteExponent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Long id = Long.parseLong(request.getParameter("id"));
        exponentService.deleteById(id);
        response.sendRedirect("/exponents");
    }

    private void listExponent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Exponent> exponents = exponentService.getAll();
        request.setAttribute("exponents", exponents);
        request.getRequestDispatcher("/WEB-INF/views/exponent/showExponent.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/exponent/addExponent.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Exponent exponent = exponentService.getById(id);
        request.setAttribute("exponent", exponent);
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/exponent/editExponent.jsp").forward(request, response);
    }
}
