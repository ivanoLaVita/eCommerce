<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">
    <title>Aggiungi Prodotto</title> 
    
    
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
<div class="admin-form-container">
    <h2>AGGIUNGI PRODOTTO</h2>
    <form action="adminEditProduct" method="get">
        <input type="hidden" name="mode" value="add">
        
        <label for="Id">ID</label>
	    <input type="text" id="id" name="id" required>

        <label for="name">Nome</label>
        <input type="text" id="name" name="name" required>

        <label for="description">Descrizione</label>
        <textarea id="description" name="description" required></textarea>

        <label for="quantity">Quantità</label>
        <input type="number" id="quantity" name="quantity" required>

        <label for="price">Costo</label>
        <input type="number" step="0.01" id="price" name="price" required>

        <label for="gender">Sesso</label>
        <select id="gender" name="gender" required>
            <option value="M">Maschile</option>
            <option value="F">Femminile</option>
        </select>

        <label for="image">Immagine URL</label>
        <input type="text" id="image" name="image" required>

        <label for="category">Categoria</label>
        <input type="text" id="category" name="category" required>

        <button type="submit">Aggiungi Prodotto</button>
    </form>
</div>
<!--== Page Content Wrapper End ==-->

<jsp:include page="fragments/Footer.jsp" />

<!-- Scroll to Top Start -->
<a href="#" class="scrolltotop"><i class="fa fa-angle-up"></i></a>
<!-- Scroll to Top End -->

</body>
</html>
