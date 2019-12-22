<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>
    <form method="post" action="/exponents" name="addForm" id="addForm">
        <div class="container mx-auto">

            <div class="form-group">
                <label for="categoryName">Наименование</label>
                <input name="categoryName" id="categoryName" type="text" class="form-control" placeholder="Наименование">
            </div>

            <div class="form-group">
                <label for="categoryId">Тематический раздел</label>
                <select class="form-control" id="categoryId" name="categoryId">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.getCategoryId()}">${category.getName()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" id="postButton" class="btn btn-success">Добавить</button>
            </div>

        </div>
    </form>
</layout:admin>