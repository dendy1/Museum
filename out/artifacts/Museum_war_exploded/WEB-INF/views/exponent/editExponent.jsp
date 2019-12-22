<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

    <form method="post" action="/exponents/update">
        <input type="hidden" name = "id" value="${exponent.getExponentId}">
        <input type="hidden" name="_method" value="put">
        <div class="container mx-auto">

            <div class="form-group">
                <h4><?php echo $page_title; ?></h4>
            </div>

            <div class="form-group">
                <label for="name">Наименование</label>
                <input name="name" id="name" type="text" class="form-control" value="${exponent.getName()}">
            </div>

            <div class="form-group">
                <label for="categoryId">Тематический раздел</label>
                <select class="form-control" id="categoryId" name="categoryId">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.getCategoryId()}" <c:if test = "${category.getCategoryId() == exponent.getCategoryId()}">selected</c:if>>${category.getName()}</option>
                    </c:forEach>>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" id="postButton" class="btn btn-success">Добавить</button>
            </div>

        </div>
    </form>

</layout:admin>