<%@page import="model.ProductBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">
    <title>Modifica Prodotto</title>
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

<!--== Page Title Area Start ==-->
<div id="page-title-area">
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <div class="page-title-content">
                    <h1>Modifica Prodotto</h1>
                    <ul class="breadcrumb">
                        <li><a href="HomePage.jsp">Home</a></li>
                        <li><a href="adminArea.jsp">Indietro</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--== Page Title Area End ==-->

<!--== Page Content Wrapper Start ==-->
<div id="page-content-modProd" class="p-9">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 m-auto">
                <div class="login-register-wrapper">
                    <div class="tab-content" id="login-reg-tabcontent">
                        <div class="tab-pane fade show active" id="register" role="tabpanel">
                            <div class="login-reg-form-wrap">
                                <% 
                                    ProductBean prodotto = (ProductBean) request.getAttribute("product"); //productId
                                    if (prodotto == null) {
                                        out.println("Errore: prodotto non trovato");
                                        return;
                                    }
                                %>
                                <form action="adminEditProduct" method="post">
    <div class="single-input-item">
        <input type="hidden" id="id" name="id" value="<%= prodotto.getId() %>">
    </div>
    <div class="single-input-item">
        <label for="nome">Nome:</label>
        <input type="text" placeholder="Nome" id="name" name="name" value="<%= prodotto.getName() %>" required>
    </div>
    <div class="single-input-item">
        <label for="descrizione">Descrizione:</label>
        <textarea placeholder="Descrizione" id="description" name="description" required><%= prodotto.getDescription() %></textarea>
    </div>
    <div class="single-input-item">
        <label for="quantita">Quantità:</label>
        <input type="number" placeholder="Quantità" id="quantity" name="quantity" value="<%= prodotto.getQuantity() %>" required>
    </div>
    <div class="single-input-item">
        <label for="costo">Costo:</label>
        <input type="number" placeholder="Costo" id="price" name="price" value="<%= prodotto.getPrice() %>" required>
    </div>
    <div class="single-input-item">
        <label for="sesso">Sesso:</label>
        <select class="form-control" id="gender" name="gender" required>
            <option value="M" <%= prodotto.getGender().equals("M") ? "selected" : "" %>>Maschile</option>
            <option value="F" <%= prodotto.getGender().equals("F") ? "selected" : "" %>>Femminile</option>
        </select>
    </div>
    <div class="single-input-item">
        <label for="immagine">Immagine URL:</label>
        <input type="text" placeholder="Immagine URL" id="image" name="image" value="<%= prodotto.getImage() %>" required>
    </div>
    <div class="single-input-item">
        <label for="categoriaNome">Categoria:</label>
        <input type="text" placeholder="Categoria" id="category" name="category" value="<%= prodotto.getCategoryName() %>" required>
    </div>
    <div class="single-input-item">
        <button class="btn-login" type="submit">Salva Modifiche</button>
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
