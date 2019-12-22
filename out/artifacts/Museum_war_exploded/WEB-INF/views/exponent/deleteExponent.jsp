<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

    Вы действительно хотите удалить экспонат ${exponent.getExponentId()}?

    <form action="/exponents/delete" method="post">
        <input type="hidden" name="_method" value="delete">
        <input type="hidden" name="id" value="${exponent.getExponentId()}">
        <input type="submit" value="Удалить">
    </form>

</layout:admin>