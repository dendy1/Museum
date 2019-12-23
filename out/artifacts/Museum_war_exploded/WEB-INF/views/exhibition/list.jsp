<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:admin>

    <h1 class="h3 mb-2 text-gray-800"><?php echo $page_title; ?></h1>
    <ul class="list-group">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Список экспонатов</h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <tr>
                            <th>ID</th>
                            <th>Дата</th>
                            <th>Название</th>
                            <th>Просмотр</th>
                            <th>Редактировать</th>
                            <th>Удалить</th>
                        </tr>

                        <c:forEach items="${exhibitions}" var="exhibition">
                            <tr>
                                <td>
                                        ${exhibition.getExhibitionId()}
                                </td>

                                <td>
                                        ${exhibition.getDate()}
                                </td>

                                <td>
                                        ${exhibition.getName()}
                                </td>

                                <td>
                                    <form action = "${pageContext.request.contextPath}/exhibition/show" method="post">
                                        <input type="hidden" name="exhibitionId" value="${exhibition.getExhibitionId()}">
                                        <input type="submit" value="Просмотр" style="float:left">
                                    </form>
                                </td>

                                <td>
                                    <form action = "${pageContext.request.contextPath}/exhibition/edit" method="post">
                                        <input type="hidden" name="exhibitionId" value="${exhibition.getExhibitionId()}">
                                        <input type="submit" value="Изменить" style="float:left">
                                    </form>
                                </td>

                                <td>
                                    <form action="${pageContext.request.contextPath}/exhibition/delete" method="get">
                                        <input type="hidden" name="exhibitionId" value="${exhibition.getExhibitionId()}">
                                        <input type="submit" value="Удалить" style="float:left">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                    <a href="${pageContext.request.contextPath}/exhibition/add" class="btn btn-success btn-icon-split justify-content-lg-start">
                        <span class="icon text-white-50"><i class="fas fa-plus-circle"></i></span>
                        <span class="text">Добавить выставку</span>
                    </a>
                </div>
            </div>
        </div>
    </ul>

</layout:admin>