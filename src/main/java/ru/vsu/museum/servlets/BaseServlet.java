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
                callMethod(method, request, response); // вызываем метод
            }
        } catch (Exception e) {
            // log exception
            request.getRequestDispatcher(WebPages.PAGE_404).forward(request, response);
        }
    }

    private String getMethod(HttpServletRequest request) {
        String method = request.getPathInfo();  // путь, например "/login"
        if (!"".equals(method)) {
            return method.substring(1, method.length()); // возвращаем подстроку без "/"
        }
        return null;
    }

    private void callMethod(String method, HttpServletRequest request, HttpServletResponse response) throws  Exception
    // вот так вот просто вызвать метод с помощью рефлекшн
            this.getClass().getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class).invoke(this, request, response);
}
}
