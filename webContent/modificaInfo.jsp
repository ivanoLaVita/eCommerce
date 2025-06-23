<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UsersBean, model.AddressBean, model.PaymentMethodBean" %>
<%@ include file="fragments/Header.jsp" %>
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
    <h1 class="title">Modifica Informazioni</h1>

    <form action="ModificaInfoServlet" method="post">
        <div class="section">
            <h2>Informazioni Utente</h2>
            <label>Nome:</label>
            <input type="text" name="firstName" value="<%= user.getFirstName() %>" required />
            
            <label>Cognome:</label>
            <input type="text" name="lastName" value="<%= user.getLastName() != null ? user.getLastName() : "" %>" />
            
            <label>Email:</label>
            <input type="email" value="<%= user.getEmail() %>" disabled />
            
            <label>Username:</label>
            <input type="text" name="username" value="<%= user.getUsername() %>" required />
        </div>

        <div class="section">
            <h2>Indirizzo</h2>
            <label>Città:</label>
            <input type="text" name="city" value="<%= address != null ? address.getCity() : "" %>" />
            
            <label>Provincia:</label>
            <input type="text" name="province" value="<%= address != null ? address.getProvince() : "" %>" />
            
            <label>CAP:</label>
            <input type="text" name="postalCode" value="<%= address != null ? address.getPostalCode() : "" %>" />
            
            <label>Via:</label>
            <input type="text" name="street" value="<%= address != null ? address.getStreet() : "" %>" />
            
            <label>Numero Civico:</label>
            <input type="text" name="streetNumber" value="<%= address != null ? address.getStreetNumber() : "" %>" />
        </div>

        <div class="section">
            <h2>Metodo di Pagamento</h2>
            <label>Tipo:</label>
            <select name="paymentType">
                <option value="">-- Seleziona --</option>
                <option value="CARD" <%= payment != null && "CARD".equals(payment.getType()) ? "selected" : "" %>>Carta</option>
                <option value="IBAN" <%= payment != null && "IBAN".equals(payment.getType()) ? "selected" : "" %>>IBAN</option>
            </select>

            <label>Numero Carta:</label>
            <input type="text" name="cardNumber" value="<%= payment != null ? payment.getCardNumber() : "" %>" />

            <label>IBAN:</label>
            <input type="text" name="iban" value="<%= payment != null ? payment.getIban() : "" %>" />
        </div>

        <input type="submit" value="Salva modifiche" class="button" />
    </form>
</div>

<%@ include file="fragments/Footer.jsp" %>
