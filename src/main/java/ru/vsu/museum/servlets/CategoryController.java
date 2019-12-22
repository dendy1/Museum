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

@WebServlet(name = "CategoryController", urlPatterns = "/categories/*")
public class CategoryController extends HttpServlet {

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
        doGet(request, response);
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
                    insertCategory(request, response);
                    break;
                case "/delete":
                    deleteCategory(request, response);
                    break;
                case "/update":
                    updateCategory(request, response);
                    break;
                default:
                    listCategory(request, response);
                    break;
            }
        } catch (SQLException ex) {
                throw new ServletException(ex);
        }
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Category category = new Category(null, name, description);
        categoryService.add(category);
        response.sendRedirect("/categories");
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Long id = Long.parseLong(request.getParameter("id"));
        Category category = categoryService.getById(id);
        category.setCategoryId(id);
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));
        categoryService.update(category);
        response.sendRedirect("/categories");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Long id = Long.parseLong(request.getParameter("id"));
        categoryService.deleteById(id);
        response.sendRedirect("/categories");
    }

    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/category/showCategory.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/category/addCategory.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Category category = categoryService.getById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/views/exponent/editCategory.jsp").forward(request, response);
    }
}
