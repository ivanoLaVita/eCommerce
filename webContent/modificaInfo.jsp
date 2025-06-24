<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UsersBean" %>
<%
    UsersBean user = (UsersBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica Informazioni</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<div class="container">
    <h1 class="title">Modifica Informazioni</h1>

    <form action="ModificaInfoServlet" method="post" class="form">
        <!-- Sezione Utente -->
        <div class="section">
            <h2>Informazioni Utente</h2>

            <label for="firstName">Nome:</label>
            <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>" required>

            <label for="lastName">Cognome:</label>
            <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() != null ? user.getLastName() : "" %>" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" readonly>

            <label for="usersname">Username:</label>
            <input type="text" id="usersname" name="usersname" value="<%= user.getUsername() %>" readonly>
        </div>

        <!-- Sezione Indirizzo -->
        <div class="section">
            <h2>Indirizzo</h2>

            <label for="city">Città:</label>
            <input type="text" id="city" name="city" value="<%= request.getAttribute("city") != null ? request.getAttribute("city") : "" %>">

            <label for="province">Provincia:</label>
            <input type="text" id="province" name="province" value="<%= request.getAttribute("province") != null ? request.getAttribute("province") : "" %>">

            <label for="zip">CAP:</label>
            <input type="text" id="zip" name="zip" value="<%= request.getAttribute("zip") != null ? request.getAttribute("zip") : "" %>">

            <label for="street">Via:</label>
            <input type="text" id="street" name="street" value="<%= request.getAttribute("street") != null ? request.getAttribute("street") : "" %>">

            <label for="civic">Numero Civico:</label>
            <input type="text" id="civic" name="civic" value="<%= request.getAttribute("civic") != null ? request.getAttribute("civic") : "" %>">
        </div>

        <!-- Sezione Metodo di Pagamento -->
        <div class="section">
            <h2>Metodo di Pagamento</h2>

            <label for="paymentType">Tipo:</label>
            <select id="paymentType" name="paymentType">
                <option value="">-- Seleziona --</option>
                <option value="CARD">Carta</option>
                <option value="IBAN">IBAN</option>
            </select>

            <label for="cardNumber">Numero Carta:</label>
            <input type="text" id="cardNumber" name="cardNumber">

            <label for="iban">IBAN:</label>
            <input type="text" id="iban" name="iban">
        </div>

        <input type="submit" value="Salva modifiche">
    </form>
</div>

<jsp:include page="fragments/Footer.jsp" />
</body>
</html>
