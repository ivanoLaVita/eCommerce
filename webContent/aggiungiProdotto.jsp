<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">
    <title>Aggiungi Prodotto</title> 
    <link rel="shortcut icon" href="assets/img/logo.png" type="image/x-icon"/>
    
    <!--== Google Fonts ==-->
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Droid+Serif:400,400i,700,700i"/>
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Montserrat:400,700"/>
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i"/>

    <!--=== Main Style CSS ===-->
    <link href="assets/css/style.css" rel="stylesheet">
    
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<!--== Page Content Wrapper Start ==-->
<div id="page-content-wrapper" class="p-9">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 m-auto">
                <div class="login-register-wrapper">
                    <div class="tab-content" id="login-reg-tabcontent">
                        <div class="tab-pane fade show active" id="register" role="tabpanel">
                            <div class="login-reg-form-wrap">
                                <form action="adminEditProduct" method="get">
                                <input required type="hidden" name="mode" value="add">
	                                <div class="single-input-item">
	                                        <input type="text" placeholder="ID" id="id" name="id" required>
	                                </div> 
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Nome" id="name" name="name" required>
                                    </div>
                                    <div class="single-input-item">
                                        <textarea placeholder="Descrizione" id="description" name="description" required></textarea>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="number" placeholder="Quantità" id="quantity" name="quantity" required>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="number" placeholder="Costo" id="price" name="price" required>
                                    </div>
                                    <div class="single-input-item">
                                        <select class="form-control" id="gender" name="gender" required>
                                            <option value="M">Maschile</option>
                                            <option value="F">Femminile</option>
                                        </select>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Immagine URL" id="image" name="image" required>
                                    </div>
                                    
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Categoria" id="category" name="category" required>
                                    </div>
                                    <div class="single-input-item">
                                        <button class="btn-login" type="submit">Aggiungi Prodotto</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--== Page Content Wrapper End ==-->

<jsp:include page="fragments/Footer.jsp" />

<!-- Scroll to Top Start -->
<a href="#" class="scrolltotop"><i class="fa fa-angle-up"></i></a>
<!-- Scroll to Top End -->

</body>
</html>
