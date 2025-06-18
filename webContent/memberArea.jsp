<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.UsersBean" %>
<%@ page import="model.OrderBean" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LaVita Jewels - Area Utente</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <jsp:include page="fragments/Header.jsp" />

    <main class="member-area-content">
        <div class="member-container">
            <%
                // Recupera l'utente dalla sessione
                UsersBean loggedUser = (UsersBean) session.getAttribute("user");

                if (loggedUser == null) {
                    // Se l'utente non è loggato, reindirizza alla pagina di login
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return; // Importante per fermare l'esecuzione della JSP
                }
            %>

            <h2>Benvenuto, <%= loggedUser.getFirstName() %> <%= loggedUser.getLastName() %>!</h2>

            <section class="user-info-section">
                <h3>Le Tue Informazioni Personali</h3>
                <p><strong>Nome Completo:</strong> <%= loggedUser.getFirstName() %> <%= loggedUser.getLastName() %></p>
                <p><strong>Nome Utente:</strong> <%= loggedUser.getUsersname() %></p>
                <p><strong>Email:</strong> <%= loggedUser.getEmail() %></p>
                <%-- Non mostrare la password qui per sicurezza --%>
                <%-- Puoi aggiungere un link per modificare il profilo o la password qui --%>
                <a href="#" class="button-secondary">Modifica Profilo</a>
            </section>

            <section class="order-history-section">
                <h3>Cronologia Ordini</h3>
                <%
                    // Recupera la lista degli ordini dalla request (verrà popolata dalla servlet)
                    List<OrderBean> userOrders = (List<OrderBean>) request.getAttribute("userOrders");

                    if (userOrders != null && !userOrders.isEmpty()) {
                %>
                    <table class="orders-table">
                        <thead>
                            <tr>
                                <th>ID Ordine</th>
                                <th>Data</th>
                                <th>Costo Totale</th>
                                <th>Dettagli</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (OrderBean order : userOrders) {
                            %>
                                <tr>
                                    <td><%= order.getId() %></td>
                                    <td><%= order.getDate() %></td>
                                    <td><%= String.format("%.2f €", order.getTotalCost()) %></td>
                                    <td><a href="<%= request.getContextPath() %>/OrderDetailsServlet?orderId=<%= order.getId() %>" class="button-small">Visualizza</a></td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                <%
                    } else {
                %>
                    <p>Non hai ancora effettuato nessun ordine.</p>
                <%
                    }
                %>
            </section>
        </div>
    </main>

    <jsp:include page="fragments/Footer.jsp" />
</body>
</html>