<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

    <form method="post" action="${pageContext.request.contextPath}/category/update">
        <div class="container mx-auto">

            <input type="hidden" name="categoryId" value="${category.getCategoryId()}">

            <div class="form-group">
                <label for="categoryName">Наименование</label>
                <input name="categoryName" id="categoryName" type="text" class="form-control" placeholder="Наименование" value="${category.getName()}">
            </div>

            <div class="form-group">
                <label for="categoryDescription">Описание</label>
                <input name="categoryDescription" id="categoryDescription" type="text" class="form-control" placeholder="Описание" value="${category.getDescription()}">
            </div>

            <div class="form-group">
                <button type="submit" id="postButton" class="btn btn-success">Сохранить</button>
            </div>

        </div>
    </form>

</layout:admin>