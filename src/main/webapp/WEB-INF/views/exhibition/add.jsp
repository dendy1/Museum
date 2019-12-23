<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>
    <form method="post" action="${pageContext.request.contextPath}/exhibition/insert">
        <div class="container mx-auto">

            <div class="form-group">
                <label for="exhibitionName">Наименование</label>
                <input name="exhibitionName" id="exhibitionName" type="text" class="form-control" placeholder="Наименование">
            </div>

            <div class="form-group">
                <label class="control-label col-xs-3" for="exhibitionDate">Дата проведения:</label>
                <div class="col-xs-9">
                    <input type="date" min="2015-01-01" max="<?php echo date('Y-m-d'); ?>" class="form-control" id="exhibitionDate" name="exhibitionDate"
                           placeholder="Выберите проведения" required>
                </div>
            </div>

            <div class="form-group">
                <label for="listIds">Экспонаты</label>
                <ul class="list-group list-group-flush" id="listIds">
                    <c:forEach items="${exponents}" var="exponent">
                        <li class="list-group-item">
                            <!-- Default checked -->
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="check${exponent.getExponentId()}" value="${exponent.getExponentId()}" name="exponentIds">
                                <label class="custom-control-label" for="check${exponent.getExponentId()}">${exponent.getName()}</label>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="form-group">
                <button type="submit" id="postButton" class="btn btn-success">Добавить</button>
            </div>

        </div>
    </form>
</layout:admin>