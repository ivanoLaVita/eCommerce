<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.OrderDAO"%>
<%@page import="model.OrderBean"%>
<%@page import="model.InserimentoDAO"%>
<%@page import="model.InserimentoBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">

    <title>Dettagli Ordine</title>
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

<%
    String idOrdine = request.getParameter("id");
    if (idOrdine != null && !idOrdine.isEmpty()) {
        InserimentoDAO inserimentoDAO = new InserimentoDAO();
        List<InserimentoBean> inserimenti = inserimentoDAO.doRetrieveByOrdine(idOrdine);
        OrderDAO ordineDAO = new OrderDAO();
        OrderBean ordine = ordineDAO.doRetrieveByKey(idOrdine);
%>

<!--== Page Title Area Start ==-->
<div id="page-title-area">
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <div class="page-title-content">
                    <h1>Dettagli Ordine</h1>
                    <ul class="breadcrumb">
                        <li><a class="button" href="HomePage.jsp">Home</a></li>
                        <% Boolean isAdmin = (Boolean) request.getSession().getAttribute("admin");
                            if (isAdmin != null && isAdmin.equals(Boolean.TRUE)) { %>
                        <li><a class="button" href="adminArea.jsp">Ordini</a></li>
                        <% } else { %>
                        <li><a class="button" href="memberArea.jsp">Ordini</a></li>
                        <% } %>
                        
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--== Page Title Area End ==-->

<div id="page-content-order" class="p-9">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="cart-table table-responsive">
                    <table class="order-table" class="table table-bordered">
                        <thead>
                            <tr>
                                <th class="product-image">Immagine</th>
                                <th class="pro-title">Prodotto</th>
                                <th class="pro-price">Prezzo</th>
                                <th class="pro-quantity">Quantità</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (InserimentoBean inserimento : inserimenti) {
                            %>
                            <tr>
                                <td class="pro-thumbnail"><img class="img-fluid" src="<%= inserimento.getImmagine() %>" alt="Product"/></td>
                                <td class="pro-title"><a href="productDetails?id=<%= inserimento.getProdottoId() %>"><%= inserimento.getNome() %></a></td>
                                <td class="pro-price">$<%= inserimento.getCosto() %></td>
                                <td class="pro-quantity"><%= inserimento.getQuantita() %></td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 ml-auto">
                <div class="cart-calculator-wrapper">
                    <h3>Riepilogo</h3>
                    <div class="cart-calculate-items">
                        <div class="table-responsive">
                            <table class="order-table" class="table table-bordered">
                            	<tr>
                                    <td>Spedizione</td>
                                    <td id="netto">$ 10</td>
                                </tr>
                                <tr>
                                    <td>Totale</td>
                                    <td id="netto">$<%= ordine.getTotalCost() + 10 %></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%
    }
%>

<jsp:include page="fragments/Footer.jsp" />

<!-- Scroll to Top Start -->
<a class="button-outline" href="#" class="scrolltotop"><i class="fa fa-angle-up"></i></a>
<!-- Scroll to Top End -->


</body>
</html>
