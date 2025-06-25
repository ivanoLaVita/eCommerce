<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">

    <title>Errore</title>
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
            <div class="col-lg-6  m-auto text-center">
                <div class="error-page-content-wrap">
                    <h2>Errore</h2>
                    <h3>
                    	Siamo spiacenti ma la pagina che stai cercando non esiste, è stata rimossa, ha cambiato nome oppure è
                        temporaneamente non disponibile.
                    </h3>    
                    <a href="HomePage.jsp" class="button">Ritorna alla home</a>
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