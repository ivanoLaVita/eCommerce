<!-- memberArea.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.UsersBean, model.AddressBean, model.PaymentMethodBean" %>
<%@ include file="fragments/Header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.OrderBean, model.OrderItemBean, model.ProductBean" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">

<%
    UsersBean user = (UsersBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    AddressBean address = (AddressBean) request.getAttribute("address");
    PaymentMethodBean payment = (PaymentMethodBean) request.getAttribute("payment");
%>

<div class="container">
    <h1 class="title">Area Personale</h1>

    <div class="section">
        <h2>Informazioni Utente</h2>
        <p><strong>Nome:</strong> <%= user.getFirstName() %></p>
        <p><strong>Cognome:</strong> <%= user.getLastName() != null ? user.getLastName() : "Non fornito" %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %> (non modificabile)</p>
        <p><strong>Username:</strong> <%= user.getUsername() %></p>
        <a href="modificaInfo.jsp" class="button">Modifica informazioni</a>
    </div>

    <div class="section">
        <h2>Indirizzo di Fatturazione</h2>
        <% if (address != null) { %>
            <p><strong>Città:</strong> <%= address.getCity() %></p>
            <p><strong>Provincia:</strong> <%= address.getProvince() %></p>
            <p><strong>CAP:</strong> <%= address.getPostalCode() %></p>
            <p><strong>Via:</strong> <%= address.getStreet() %> <%= address.getStreetNumber() %></p>
            <form action="RemoveAddressServlet" method="post">
                <input type="submit" value="Rimuovi Indirizzo" class="button remove">
            </form>
        <% } else { %>
            <p>Nessun indirizzo salvato.</p>
            <a href="modificaInfo.jsp" class="button">Aggiungi Indirizzo</a>
        <% } %>
    </div>

    <div class="section">
        <h2>Metodo di Pagamento</h2>
        <% if (payment != null) {
            String type = payment.getType();
            if ("CARD".equals(type)) { %>
                <p><strong>Tipo:</strong> Carta</p>
                <p><strong>Numero Carta:</strong> <%= payment.getCardNumber() %></p>
        <%  } else if ("IBAN".equals(type)) { %>
                <p><strong>Tipo:</strong> IBAN</p>
                <p><strong>IBAN:</strong> <%= payment.getIban() %></p>
        <%  } %>
            <form action="RemovePaymentServlet" method="post">
                <input type="submit" value="Rimuovi Metodo di Pagamento" class="button remove">
            </form>
        <% } else { %>
            <p>Nessun metodo di pagamento salvato.</p>
            <a href="modificaInfo.jsp" class="button">Aggiungi Metodo di Pagamento</a>
        <% } %>
    </div>
</div>

<%@ include file="fragments/Footer.jsp" %>