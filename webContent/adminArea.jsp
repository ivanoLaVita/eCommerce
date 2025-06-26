<%@page import="java.sql.SQLException"%>
<%@page import="model.ProductBean"%>
<%@page import="model.ProductDAO"%>
<%@page import="model.OrderDAO"%>
<%@page import="model.OrderBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">

<%
if (request.getSession().getAttribute("admin") != Boolean.TRUE)
    response.sendRedirect("catalogo?mode=home");

List<ProductBean> prodotti = (List<ProductBean>) session.getAttribute("prodotti");
if (prodotti == null) {
    ProductDAO prodottoDAO = new ProductDAO();
    try {
        prodotti = prodottoDAO.doRetrieveAll("id");
        session.setAttribute("prodotti", prodotti);
    } catch (SQLException e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Errore nel recupero dei prodotti");
    }
}
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Area Amministratore</title>
    <link href="assets/css/style.css" rel="stylesheet">
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<div class="container">
    <div class="myaccount-content">
        <h3>Area Amministratore</h3>
        <div class="welcome">
            <% String nome = (String) session.getAttribute("firstName");
               String cognome = (String) session.getAttribute("lastName"); %>
            <p>Bentornato, <%= nome %> <%= cognome %>!</p>
        </div>
        <p class="mb-0">Da questa area puoi gestire ordini e prodotti del catalogo.</p>
    </div>

    <div class="myaccount-content">
        <h3>Gestisci Ordini</h3>
        <form action="adminArea.jsp#orders" method="GET">
            <label for="inputEmail">Email:</label>
            <input type="text" id="inputEmail" name="email">

            <label for="inputDataInizio">Data Inizio:</label>
            <input type="date" id="inputDataInizio" name="dataInizio">

            <label for="inputDataFine">Data Fine:</label>
            <input type="date" id="inputDataFine" name="dataFine">

            <button type="submit">Cerca Ordini</button>
            <button type="button" onclick="resetFilters()">Azzerare Filtri</button>
        </form>

        <div class="myaccount-table">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Email</th>
                        <th>Dettagli</th>
                    </tr>
                </thead>
                <tbody>
                    <% OrderDAO ordineDAO = new OrderDAO();
                       List<OrderBean> ordini = null;
                       String userEmail = request.getParameter("email");
                       String dataInizio = request.getParameter("dataInizio");
                       String dataFine = request.getParameter("dataFine");
                       String reset = request.getParameter("reset");

                       try {
                           if (reset != null && reset.equals("true")) {
                               ordini = ordineDAO.doRetrieveAll(null);
                           } else if (userEmail != null && !userEmail.isEmpty()) {
                               ordini = ordineDAO.doRetrieveByEmail(userEmail);
                           } else if (dataInizio != null && dataFine != null && !dataInizio.isEmpty() && !dataFine.isEmpty()) {
                               ordini = ordineDAO.doRetrieveByDateRange(dataInizio, dataFine);
                           } else {
                               ordini = ordineDAO.doRetrieveAll(null);
                           }
                       } catch (Exception e) {
                           out.println("<tr><td colspan='5'>Errore: " + e.getMessage() + "</td></tr>");
                       }

                       if (ordini != null && !ordini.isEmpty()) {
                           for (OrderBean ordine : ordini) {
                    %>
                    <tr>
                        <td><%= ordine.getId() %></td>
                        <td><%= ordine.getDate() %></td>
                        <td><%= ordine.getTotalCost() %></td>
                        <td><%= ordine.getUserEmail() %></td>
                        <td><a class="btn-add-to-cart" href="visualizzaOrdine.jsp?id=<%= ordine.getId() %>">Visualizza</a></td>
                    </tr>
                    <% } } else { %>
                    <tr><td colspan="5">Nessun ordine trovato.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="myaccount-content">
        <h3>Gestisci Prodotti</h3>
        <a href="aggiungiProdotto.jsp" class="button">Aggiungi Prodotto</a>

        <div class="myaccount-table">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Quantità</th>
                        <th>Prezzo</th>
                        <th>Sesso</th>
                        <th>Categoria</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (prodotti != null && !prodotti.isEmpty()) {
                           for (ProductBean prodotto : prodotti) {
                    %>
                    <tr>
                        <td><%= prodotto.getId() %></td>
                        <td><%= prodotto.getName() %></td>
                        <td><%= prodotto.getQuantity() %></td>
                        <td><%= prodotto.getPrice() %></td>
                        <td><%= prodotto.getGender() %></td>
                        <td><%= prodotto.getCategoryName() %></td>
                        <td>
                            <a href="adminEditProduct?mode=edit&productId=<%= prodotto.getId() %>" class="btn btn-info btn-sm">Modifica</a>
                            <a href="adminEditProduct?mode=delete&productId=<%= prodotto.getId() %>" class="btn btn-danger btn-sm">Elimina</a>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr><td colspan="7">Nessun prodotto trovato.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="fragments/Footer.jsp" />

<script>
function resetFilters() {
    document.getElementById("inputEmail").value = "";
    document.getElementById("inputDataInizio").value = "";
    document.getElementById("inputDataFine").value = "";
    var form = document.querySelector("form");
    var inputReset = document.createElement("input");
    inputReset.setAttribute("type", "hidden");
    inputReset.setAttribute("name", "reset");
    inputReset.setAttribute("value", "true");
    form.appendChild(inputReset);
    form.submit();
}
</script>

</body>
</html>
