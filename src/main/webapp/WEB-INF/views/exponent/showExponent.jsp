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
                            <th>Наименование</th>
                            <th>ID тематического раздела</th>
                            <th>Редактировать</th>
                            <th>Удалить</th>
                        </tr>

                        <c:forEach items="${exponents}" var="exponent">
                        <tr>
                            <td>
                                 ${exponent.getExponentId()}
                            </td>

                            <td>
                                 ${exponent.getName()}
                            </td>

                            <td>
                                 ${exponent.getCategoryId()}
                            </td>

                            <td>
                                <form action = "/exponents/edit" method="post">
                                    <input type="hidden" name="id" value="${exponent.getExponentId()}">
                                    <input type="hidden" name="name" value="${exponent.getName()}">
                                    <input type="hidden" name="categoryId" value="${exponent.getCategoryId()}">
                                    <input type="submit" value="Изменить" style="float:left">
                                </form>
                            </td>

                            <td>
                                <form action="/exponents/delete" method="post">
                                    <input type="hidden" name="id" value="${exponent.getExponentId()}">
                                    <input type="submit" value="Удалить" style="float:left">
                                </form>
                            </td>
                        </tr>
                        </c:forEach>

                    </table>
                    <a href="/exponents/add" class="btn btn-success btn-icon-split justify-content-lg-start">
                        <span class="icon text-white-50"><i class="fas fa-plus-circle"></i></span>
                        <span class="text">Добавить экспонат</span>
                    </a>
                </div>
            </div>
        </div>
    </ul>

</layout:admin>