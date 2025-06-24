<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64, java.util.Base64.Decoder" %>
<%@ page import="model.UsersBean, model.AddressBean, model.AddressDAO, model.PaymentMethodBean, model.PaymentMethodDAO, model.OrderDAO, model.OrderBean" %>
<%@ page import="java.util.List, java.sql.SQLException" %>

<%
    UsersBean userBean = (UsersBean) session.getAttribute("user");
    if (userBean == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String userEmail = userBean.getEmail();

    AddressBean address = (AddressBean) request.getAttribute("address");
    PaymentMethodBean payment = (PaymentMethodBean) request.getAttribute("payment");
%>

<%@ include file="fragments/Header.jsp" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">

<!--== Page Title Area Start ==-->
<div id="page-title-area">
<div class="container">
    <h1 class="title">Area Personale</h1>
</div>
</div>
    <%--<div class="section">
        <h2>Informazioni Utente</h2>
        <p><strong>Nome:</strong> <%= userBean.getFirstName() %></p>
        <p><strong>Cognome:</strong> <%= userBean.getLastName() != null ? userBean.getLastName() : "Non fornito" %></p>
        <p><strong>Email:</strong> <%= userBean.getEmail() %> (non modificabile)</p>
        <p><strong>Username:</strong> <%= userBean.getUsername() %></p>
        <a href="modificaInfo.jsp" class="button">Modifica informazioni</a>
    </div>  --%>
<!--== Page Title Area End ==-->

<!--== Page Content Wrapper Start ==-->
<div id="page-content-wrapper" class="p-9">
<div class="container">
        <div class="row">
            <div class="col-lg-12">
	<div class="tab-pane fade" id="orders" role="tabpanel">
        <div class="myaccount-content">
        <h3>Metodo di Pagamento</h3>
        <form action="info" method="get">
            <input type="hidden" name="mode" value="add">
            <input type="hidden" name="target" value="metodoPagamento">
            <button class="button">Aggiungi Metodo di Pagamento</button>
        </form>

        <div class="myaccount-table table-responsive text-center">
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>Tipo</th>
                        <th>Numero carta</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        PaymentMethodDAO metodoDiPagamentoDAO = new PaymentMethodDAO();
                        List<PaymentMethodBean> metodiDiPagamento = null;

                        try {
                            metodiDiPagamento = metodoDiPagamentoDAO.doRetrieveByEmail(userEmail);
                        } catch (Exception e) {
                            out.println("Errore: " + e.getMessage());
                        }

                        if (metodiDiPagamento != null && !metodiDiPagamento.isEmpty()) {
                            for (PaymentMethodBean metodo : metodiDiPagamento) {
                                if (metodo.getType().equals("IBAN")) {
                                    continue; // ignora IBAN
                                }
                    %>
                    <tr>
                        <td><%= metodo.getType() %></td>
                        <td><%= metodo.getCardNumber() %></td>
                    </tr>
                    <%       }
                        } else { %>
                    <tr>
                        <td colspan="2">Nessun metodo di pagamento trovato per l'email <%= userEmail %>.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="tab-pane fade" id="orders" role="tabpanel">
        <div class="myaccount-content">
            <h3>I tuoi Ordini</h3>
            <div class="myaccount-table table-responsive text-center">
                <table class="table table-bordered">
                    <thead class="thead-light">
                        <tr>
                            <th>Data</th>
                            <th>Costo Totale</th>
                            <th>Dettagli</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            OrderDAO ordineDAO = new OrderDAO();
                            List<OrderBean> ordini = null;

                            try {
                                ordini = ordineDAO.doRetrieveByEmail(userEmail);
                            } catch (Exception e) {
                                out.println("Errore: " + e.getMessage());
                            }

                            if (ordini != null && !ordini.isEmpty()) {
                                for (OrderBean ordine : ordini) {
                        %>
                        <tr>
                            <td><%= ordine.getDate() %></td>
                            <td><%= ordine.getTotalCost() %></td>
                            <td><a href="visualizzaOrdine.jsp?id=<%= ordine.getId() %>" class="button">Visualizza</a></td>
                        </tr>
                        <%   }
                            } else { %>
                        <tr>
                            <td colspan="3">Nessun ordine trovato per l'email <%= userEmail %>.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

                                <!-- Single Tab Content Start -->
                                <!-- Single Tab Content Start -->
<div class="tab-pane fade" id="address" role="tabpanel">
    <div class="myaccount-content">
        <h3>I tuoi Indirizzi di Spedizione</h3>
		<form action="info" method="get">
	  		<input type="hidden" value="<% out.print(request.getSession().getAttribute("utente"));%>" name="utente">
	  		<input type="hidden" name="mode" value="add">
	  		<input type="hidden" name="target" value="indirizzo">
	        <button class="button">Aggiungi Indirizzo</button>
        </form>
        <div class="myaccount-table table-responsive text-center">
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>Città</th>
                        <th>Provincia</th>
                        <th>CAP</th>
                        <th>Via</th>
                        <th>Civico</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Recupero gli indirizzi di spedizione dell'utente loggato --%>
                    <%
                        AddressDAO indirizzoDAO = new AddressDAO();
                        List<AddressBean> indirizzi = null;

                        if (userEmail != null && !userEmail.isEmpty()) {
                            try {
                                indirizzi = indirizzoDAO.doRetrieveByEmail(userEmail);
                            } catch (Exception e) {
                                out.println("Errore: " + e.getMessage());
                            }
                        }

                        if (indirizzi != null && !indirizzi.isEmpty()) {
                            for (AddressBean indirizzo : indirizzi) {
                    %>
                    <tr>
                        <td><%= indirizzo.getCity() %></td>
                        <td><%= indirizzo.getProvince() %></td>
                        <td><%= indirizzo.getPostalCode() %></td>
                        <td><%= indirizzo.getStreet() %></td>
                        <td><%= indirizzo.getStreetNumber() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6">Nessun indirizzo trovato per l'email <%= userEmail %>.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Single Tab Content Start -->
<div class="tab-pane fade" id="account-info" role="tabpanel">
	<div class="myaccount-content">
		<h3>Dettagli dell'Account</h3>

        <div class="account-details-form">
			<form action="info" id="modificaInfo" method="post">
				<input type="hidden" name="utente" value="<%= request.getSession().getAttribute("email") %>">
				<input type="hidden" name="target" value="utente">
				<input type="hidden" name="mode" value="update">

                <div class="row">
					<div class="col-lg-6">
						<div class="single-input-item">
							<label for="nome">Nome</label>
							<input type="text" id="nomeNuovo" name="nomeNuovo"
								placeholder="Nome" value="<%= userBean.getFirstName() %>"/>
							<p class="errors" style="color:red;" id="error-name"></p>
						</div>
					</div>

					<div class="col-lg-6">
						<div class="single-input-item">
							<label for="cognome">Cognome</label>
							<input type="text" id="cognomeNuovo" name="cognomeNuovo"
								placeholder="Cognome" value="<%= userBean.getLastName() %>"/>
							<p class="errors" style="color:red;" id="error-surname"></p>
						</div>
					</div>
				</div>

				<div class="single-input-item">
					<label for="username">Username</label>
					<input type="text" id="usernameNuovo" name="usernameNuovo"
						placeholder="Username" value="<%= userBean.getUsername() %>"/>
					<p class="errors" style="color:red;" id="error-username"></p>
				</div>

				<fieldset>
				<legend>Cambio Password</legend>

				<div class="row">
					<div class="col-lg-6">
						<div class="single-input-item">
                        	<label for="password">Nuova Password</label>
							<input type="password" id="password" name="password"
								placeholder="Nuova Password (lascia vuoto se non vuoi cambiarla)"/>
						</div>
					</div>

					<div class="col-lg-6">
						<div class="single-input-item">
							<label for="passwordCheck">Ripeti Password</label>
							<input type="password" id="passwordCheck" name="passwordCheck"
								placeholder="Ripeti Nuova Password"/>
							<p class="errors" style="color:red;" id="error-pwd"></p>
						</div>
					</div>
				</div>
				</fieldset>

				<div class="single-input-item">
					<input class="button" type="submit" id="modificaInfoBtn" value="Modifica dati">
				</div>
			</form>
		</div>
	</div>
</div>

                                <!-- Single Tab Content End -->
</div>
</div>
        </div>
    </div>
</div>
<!--== Page Content Wrapper End ==-->
<%@ include file="fragments/Footer.jsp" %>
