<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

        <div class="container mx-auto">

            <input type="hidden" name = "exponentId" value="${exhibition.getExhibitionId()}">

            <div class="form-group">
                <label for="exhibitionName">Наименование</label>
                <input name="exhibitionName" id="exhibitionName" type="text" class="form-control" readonly placeholder="Наименование" value="${exhibition.getName()}">
            </div>

            <div class="form-group">
                <label class="control-label col-xs-3" for="exhibitionDate">Дата проведения:</label>
                <div class="col-xs-9">
                    <input type="date" min="2015-01-01" max="<?php echo date('Y-m-d'); ?>" class="form-control" id="exhibitionDate" name="exhibitionDate"
                           placeholder="Выберите проведения" readonly required value="${exhibition.getDate()}">
                </div>
            </div>

            <div class="form-group">
                <label for="exponents">Экспонаты</label>
                <ul class="list-group list-group-flush" name="exponents" id="exponents">
                    <c:forEach items="${exponents}" var="exponent">
                        <li class="list-group-item">${exponent.getName()}</li>
                    </c:forEach>
                </ul>
            </div>

            <a href="${pageContext.request.contextPath}/exhibition/list" class="btn btn-success btn-icon-split justify-content-lg-start">
                <span class="icon text-white-50"><i class="fas fa-arrow-left"></i></span>
                <span class="text">Назад</span>
            </a>
        </div>
</layout:admin>