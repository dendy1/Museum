<%@ tag description="Admin Panel Wrapping Tag" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><?php echo $page_title ?></title>
    <!-- Custom fonts for this template-->
    <link href="/public/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="/public/css/nunito.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template-->
    <link href="/public/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="/public/vendor/summernote/summernote.css" rel="stylesheet" type="text/css">
    <link href="/public/css/sweetalert2.css" rel="stylesheet" type="text/css">
    <link href="/public/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">
</head>

<body class="bg-gradient-primary">
<!-- Page Wrapper -->
<div id="wrapper">
    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-university"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Museum</div>
        </a>
        <!-- Divider -->
        <hr class="sidebar-divider my-0">
        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="/admin">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Dashboard</span></a>
        </li>
        <!-- Divider -->
        <hr class="sidebar-divider">
        <!-- Heading -->
        <div class="sidebar-heading">
            Панель управления
        </div>
        <!-- Nav Item - Pages Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOne"
               aria-expanded="true" aria-controls="collapseOne">
                <i class="far fa-image"></i>
                <span>Экспонаты</span>
            </a>
            <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Управление экспонат.:</h6>
                    <a class="collapse-item" href="/exponents/add"><i class="fas fa-plus-circle"></i> Добавить экспонат</a>
                    <a class="collapse-item" href="/exponents"><i class="fas fa-list"></i> Просмотреть экспонаты</a>
                </div>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-images"></i>
                <span>Выставки</span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Управление выстав.:</h6>
                    <a class="collapse-item" href="/exhibitions/add"><i class="fas fa-plus-circle"></i> Организовать выставку</a>
                    <a class="collapse-item" href="/exhibitions"><i class="fas fa-list"></i> Посмотреть выставки</a>
                </div>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseThree"
               aria-expanded="true" aria-controls="collapseThree">
                <i class="fas fa-list"></i>
                <span>Тематические разделы</span>
            </a>
            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Управление тем. разд.:</h6>
                    <a class="collapse-item" href="/categories/add"><i class="fas fa-plus-circle"></i> Добавить тем. разд.</a>
                    <a class="collapse-item" href="/categories"><i class="fas fa-list"></i> Посмотреть тем. разд.</a>
                </div>
            </div>
        </li>
    </ul>
    <!-- End of Sidebar -->
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">
                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small"><?php echo $_SESSION['account']['email']; ?></span>
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Выйти
                            </a>
                        </div>
                    </li>
                </ul>
            </nav>
            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <div class="container-fluid">
                <jsp:doBody/>
            </div>
            <!-- End of Page Content -->
        </div>
        <!-- End of Main Content -->
        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; 2019</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Page Wrapper -->
<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>
<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Уже готовы уйти?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Выберите "Выйти" ниже, если вы хотите закончить текущую сессию.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Отмена</button>
                <a class="btn btn-primary" href="/account/logout">Выйти</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="/public/vendor/jquery/jquery.js"></script>
<script src="/public/js/popper.min.js"></script>
<script src="/public/vendor/bootstrap/js/bootstrap.js"></script>
<!-- Core plugin JavaScript-->
<script src="/public/vendor/jquery-easing/jquery.easing.js"></script>
<!-- Custom scripts for all pages-->
<script src="/public/vendor/summernote/summernote.js"></script>
<script src="/public/js/sb-admin-2.js"></script>
<script src="/public/js/sweetalert2.js"></script>
<script src="/public/js/formpopup.js"></script>
<script src="/public/js/textarea.js"></script>
</body>
</html>