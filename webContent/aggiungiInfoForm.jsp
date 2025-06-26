<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="fragments/Header.jsp" />
<link rel="stylesheet" href="assets/css/style.css">

<%
    String target = (String) request.getAttribute("target");
    String email = (String) request.getAttribute("email");
    String errorMessage = (String) session.getAttribute("error");
    String successMessage = (String) session.getAttribute("message");
%>

<div class="form-container">
    <h2>
        <% if ("indirizzo".equals(target)) { %>
            Aggiungi Indirizzo
        <% } else if ("metodoPagamento".equals(target)) { %>
            Aggiungi Metodo di Pagamento
        <% } %>
    </h2>

    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
        <% session.removeAttribute("error"); %>
    <% } %>

    <% if (successMessage != null) { %>
        <div class="success"><%= successMessage %></div>
        <% session.removeAttribute("message"); %>
    <% } %>

    <form method="post" action="info">
        <input type="hidden" name="mode" value="add"/>
        <input type="hidden" name="target" value="<%= target %>"/>
        <input type="hidden" name="utente" value="<%= email %>"/>

        <% if ("indirizzo".equals(target)) { %>
            <label for="street">Via:</label>
            <input type="text" name="street" id="street" required>

            <label for="streetNumber">Civico:</label>
            <input type="text" name="streetNumber" id="streetNumber" required>

            <label for="city">Città:</label>
            <input type="text" name="city" id="city" required>

            <label for="province">Provincia:</label>
            <input type="text" name="province" id="province" required>

            <label for="postalCode">CAP:</label>
            <input type="text" name="postalCode" id="postalCode" required>

        <% } else if ("metodoPagamento".equals(target)) { %>
            <label for="type">Tipo di pagamento:</label>
            <select name="type" id="type" required onchange="toggleFields()">
                <option value="">-- Seleziona --</option>
                <option value="IBAN">IBAN</option>
                <option value="CARD">Carta</option>
            </select>

            <div id="ibanField" style="display:none;">
                <label for="iban">IBAN:</label>
                <input type="text" name="iban" id="iban">
            </div>

            <div id="cardField" style="display:none;">
                <label for="cardNumber">Numero Carta:</label>
                <input type="text" name="cardNumber" id="cardNumber">
            </div>

            <script>
                function toggleFields() {
                    var tipo = document.getElementById("type").value;
                    document.getElementById("ibanField").style.display = tipo === "IBAN" ? "block" : "none";
                    document.getElementById("cardField").style.display = tipo === "CARD" ? "block" : "none";
                }
            </script>
        <% } %>

        <button type="submit">Salva</button>
    </form>
</div>

<jsp:include page="fragments/Footer.jsp" />
