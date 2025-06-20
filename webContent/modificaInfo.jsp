<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.UsersBean" %>
<%@ include file="fragments/Header.jsp" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">


<%
    UsersBean user = (UsersBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="container">
    <h1 class="title">Modifica Informazioni</h1>

    <form action="ModificaInfoServlet" method="post" class="form">

        <h2>Dati personali</h2>
        <label>Nome: <input type="text" name="firstName" value="<%= user.getFirstName() %>" required></label>
        <%String lastNameValue = (user.getLastName() != null) ? user.getLastName() : "";%>
		<label>Cognome: <input type="text" name="lastName" value="<%= lastNameValue %>"></label>

        <label>Username: <input type="text" name="username" value="<%= user.getUsername() %>" required></label>

        <h2>Indirizzo di Fatturazione</h2>
        <label>Città: <input type="text" name="city" required></label>
        <label>Provincia: <input type="text" name="province" required></label>
        <label>CAP: <input type="text" name="postalCode" required></label>
        <label>Via: <input type="text" name="street" required></label>
        <label>Numero civico: <input type="text" name="streetNumber" required></label>

        <h2>Metodo di Pagamento</h2>
        <label>Tipo:
            <select name="paymentType" required>
                <option value="CARD">Carta</option>
                <option value="IBAN">IBAN</option>
            </select>
        </label>
        <label>Numero Carta: <input type="text" name="cardNumber"></label>
        <label>IBAN: <input type="text" name="iban"></label>

        <input type="submit" value="Salva modifiche" class="button">
    </form>
</div>

<%@ include file="fragments/Footer.jsp" %>
