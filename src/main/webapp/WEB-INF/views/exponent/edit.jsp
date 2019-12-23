<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

    <form method="post" action="${pageContext.request.contextPath}/exponent/update">
        <div class="container mx-auto">

            <input type="hidden" name = "exponentId" value="${exponent.getExponentId()}">

            <div class="form-group">
                <label for="exponentName">Наименование</label>
                <input name="exponentName" id="exponentName" type="text" class="form-control" value="${exponent.getName()}">
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
                <button type="submit" id="postButton" class="btn btn-success">Сохранить</button>
            </div>

        </div>
    </form>

</layout:admin>