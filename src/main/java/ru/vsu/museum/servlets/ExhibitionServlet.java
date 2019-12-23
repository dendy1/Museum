package ru.vsu.museum.servlets;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExhibitionService;
import ru.vsu.museum.service.ExponentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ExhibitionServlet", urlPatterns = "/exhibition/*")
public class ExhibitionServlet extends BaseServlet {
    ExhibitionService exhibitionService;
    ExponentService exponentService;

    public void init() {
        exhibitionService = new ExhibitionService();
        exponentService = new ExponentService();
    }

    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Exhibition> exhibitions = exhibitionService.getAll();
        request.setAttribute("exhibitions", exhibitions);
        request.getRequestDispatcher("/WEB-INF/views/exhibition/list.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Exponent> exponents = exponentService.getAll();
        request.setAttribute("exponents", exponents);
        request.getRequestDispatcher("/WEB-INF/views/exhibition/add.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("exhibitionId"));
        Exhibition exhibition = exhibitionService.getById(id);
        List<Exponent> allExponents = exponentService.getAll();
        List<Exponent> exponents = exhibitionService.getExponents(id);
        request.setAttribute("exhibition", exhibition);
        request.setAttribute("allExponents", allExponents);
        request.setAttribute("exponents", exponents);
        request.getRequestDispatcher("/WEB-INF/views/exhibition/edit.jsp").forward(request, response);
    }

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("exhibitionId"));
        Exhibition exhibition = exhibitionService.getById(id);
        List<Exponent> exponents = exhibitionService.getExponents(id);
        request.setAttribute("exhibition", exhibition);
        request.setAttribute("exponents", exponents);
        request.getRequestDispatcher("/WEB-INF/views/exhibition/show.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        String name = request.getParameter("exhibitionName");
        String dateString = request.getParameter("exhibitionDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);

        Exhibition exhibition = new Exhibition(null, date, name);

        List<Long> exponentsIds = new ArrayList<Long>();
        String[] exponentsIdsValues = request.getParameterValues("exponentIds");
        for (String idString : exponentsIdsValues) {
            exponentsIds.add(Long.parseLong(idString));
        }
        exhibitionService.addWithExponents(exhibition, exponentsIds);
        response.sendRedirect("/exhibition/list");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        Long id = Long.parseLong(request.getParameter("exhibitionId"));
        Exhibition exhibition = exhibitionService.getById(id);

        String name = request.getParameter("exhibitionName");
        String dateString = request.getParameter("exhibitionDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);

        exhibition.setName(name);
        exhibition.setDate(date);

        List<Long> exponentsIds = new ArrayList<Long>();
        String[] exponentsIdsValues = request.getParameterValues("exponentIds");
        for (String idString : exponentsIdsValues) {
            exponentsIds.add(Long.parseLong(idString));
        }

        exhibitionService.updateWithExponents(exhibition, exponentsIds);
        response.sendRedirect("/exhibition/list");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("exhibitionId"));
        exhibitionService.deleteById(id);
        response.sendRedirect("/exhibition/list");
    }
}
