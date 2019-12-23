package ru.vsu.museum.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String method = getMethod(request);
            if (method != null && !"".equals(method)) {
                callMethod(method, request, response);
            }
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/errors/404.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String method = getMethod(request);
            if (method != null && !"".equals(method)) {
                callMethod(method, request, response);
            }
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/errors/404.jsp").forward(request, response);
        }
    }

    private String getMethod(HttpServletRequest request) {
        String method = request.getPathInfo();
        if (!"".equals(method)) {
            return method.substring(1, method.length());
        }
        return null;
    }

    private void callMethod(String method, HttpServletRequest request, HttpServletResponse response) throws  Exception {
        this.getClass().getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class).invoke(this, request, response);
    }
}
