<%@page import="java.sql.SQLException"%>
<%@page import="model.ProductBean"%>
<%@page import="model.ProductDAO"%>
<%@page import="model.OrderDAO"%>
<%@page import="model.OrderBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html class="no-js" lang="zxx">

<% 
if(request.getSession().getAttribute("admin") != Boolean.TRUE)
	response.sendRedirect("catalogo?mode=home");

// Inizializza la lista dei prodotti solo se non è già presente nella sessione
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="meta description">

    <title>Area utente</title>

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

<%--<!--== Page Content Wrapper Start ==-->
<div id="page-content-wrapper" class="p-9">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <!-- My Account Page Start -->
                <div class="myaccount-page-wrapper">
                    <!-- My Account Tab Menu Start -->
                    <div class="row">
                        <div class="col-lg-3">
                            <div class="myaccount-tab-menu nav" role="tablist">
                                <a href="#dashboad" class="active" data-toggle="tab"><i class="fa fa-dashboard"></i> Area Utente</a>

                                <a href="#orders" data-toggle="tab"><i class="fa fa-cart-arrow-down"></i>Gestisci Ordini</a>

                                <a href="#products" data-toggle="tab"><i class="fa fa-map-marker"></i>Gestisci Prodotti</a>

                 
                                <a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
                            </div>
                        </div>
                  	<!-- My Account Tab Menu End -->
--%>
                 <!-- My Account Tab Content Start -->
                 <div class="col-lg-9 mt-5 mt-lg-0">
                          <div class="tab-content" id="myaccountContent">
                            <!-- Single Tab Content Start -->
                            <div class="tab-pane fade show active" id="dashboad" role="tabpanel">
                                <div class="myaccount-content">
                                    <h3>Area Utente</h3>

                                    <div class="welcome">
                                    <p>
                                    <%
									Boolean isLoggedIn = (Boolean) session.getAttribute("logged");
							        if (isLoggedIn != null && isLoggedIn) {
							            String nome = (String) session.getAttribute("nome");
							            String cognome = (String) session.getAttribute("cognome");
							            out.println("<p>Bentornato, " + nome + " " + cognome + "!</p>");
							        } else {
							            out.println("<p>Perfavore <a href='loginPage.jsp'>login</a> per continuare.</p>");
							        }
			
									%>
                                    </div>

                                     <p class="mb-0">Dalla dashboard del tuo account. puoi facilmente controllare e visualizzare il tuo
                                        ordini recenti, gestisci i tuoi indirizzi di spedizione e fatturazione e modifica i tuoi
                                        password e dettagli dell'account.
                                     </p>
                                </div>
                            </div>
                     
					 <!-- Single Tab Content End -->
				 	 <div class="tab-pane fade" id="orders" role="tabpanel">
							<div class="myaccount-content">
								        <h3>Gestisci Ordini</h3>
								
								        <!-- Form per inserire l'email e intervallo di date -->
								        <form action="adminArea.jsp#orders" method="GET">
								            <div class="form-group">
								                <label for="inputEmail">Inserisci l'email:</label>
								                &nbsp&nbsp
								                <input type="text" id="inputEmail" name="email">
								            </div>
								            <div class="form-group">
								                <label for="inputDataInizio">Data Inizio:</label>
								                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								                <input type="date" id="inputDataInizio" name="dataInizio">
								            </div>
								            <div class="form-group">
								                <label for="inputDataFine">Data Fine:</label>
								                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								                <input type="date" id="inputDataFine" name="dataFine">
								            </div>
								            <br><br>
								            <button type="submit">Cerca ordini</button>
								            <button type="button" onclick="resetFilters()">Azzerare filtri</button>
								        </form>
								
								        <div class="myaccount-table table-responsive text-center">
								            <table class="table table-bordered">
								                <thead class="thead-light">
								                    <tr>
								                        <th>ID</th>
								                        <th>Data</th>
								                        <th>Costo Totale</th>
								                        <th>Email Utente</th>
								                        <th>Dettagli</th>
								                    </tr>
								                </thead>
								                <tbody>
								                    <%-- Recupero tutti gli ordini se non viene fornita un'email --%>
								                    <%
								                    OrderDAO ordineDAO = new OrderDAO();
								                    List<OrderBean> ordini = null;
								
								                    String userEmail = request.getParameter("email");
								                    String dataInizio = request.getParameter("dataInizio");
								                    String dataFine = request.getParameter("dataFine");
								                    String reset = request.getParameter("reset");
								
								                    if (reset != null && reset.equals("true")) {
								                        // Azzeramento dei filtri, recupera tutti gli ordini
								                        try {
								                            ordini = ordineDAO.doRetrieveAll(null);
								                        } catch (Exception e) {
								                            out.println("Errore: " + e.getMessage());
								                        }
								                    } else if (userEmail != null && !userEmail.isEmpty()) {
								                        // Recupera gli ordini per email specificata
								                        try {
								                            ordini = ordineDAO.doRetrieveByEmail(userEmail);
								                        } catch (Exception e) {
								                            out.println("Errore: " + e.getMessage());
								                        }
								                    } else if (dataInizio != null && dataFine != null && !dataInizio.isEmpty() && !dataFine.isEmpty()) {
								                        // Recupera gli ordini per intervallo di date
								                        try {
								                            ordini = ordineDAO.doRetrieveByDateRange(dataInizio, dataFine);
								                        } catch (Exception e) {
								                            out.println("Errore: " + e.getMessage());
								                        }
								                    } else {
								                        // Recupera tutti gli ordini se non viene fornita un'email o un intervallo di date
								                        try {
								                            ordini = ordineDAO.doRetrieveAll(null);
								                        } catch (Exception e) {
								                            out.println("Errore: " + e.getMessage());
								                        }
								                    }
								
								                    if (ordini != null && !ordini.isEmpty()) {
								                        for (OrderBean ordine : ordini) {
								                    %>
								                    <tr>
								                        <td><%= ordine.getId() %></td>
								                        <td><%= ordine.getDate() %></td>
								                        <td><%= ordine.getTotalCost() %></td>
								                        <td><%= ordine.getUserEmail() %></td>
								                        <td><a href="visualizzaOrdine.jsp?id=<%= ordine.getId()%>" class="btn-add-to-cart">Visualizza</a></td>
								                    </tr>
								                    <%
								                        }
								                    } else {
								                    %>
								                    <tr>
								                        <td colspan="5">Nessun ordine trovato<% if (userEmail != null && !userEmail.isEmpty()) out.print(" per l'email " + userEmail); %>.</td>
								                    </tr>
								                    <%
								                    }
								                    %>
								                </tbody>
								            </table>
								        </div>
							</div>
					 </div>

                     <!-- Single Tab Content End -->

                  
                      

                   
                    
                     <!-- Single Tab Content Start -->                       
					 <div class="tab-pane fade" id="products" role="tabpanel">
									    <div class="myaccount-content">
									        <h3>Gestisci Prodotti</h3>
									        
									        
									        <a href="aggiungiProdottoForm.jsp" class="btn btn-primary mb-3">Aggiungi Prodotto</a>
									        
									
									        <div class="myaccount-table table-responsive text-center">
									            <table class="table table-bordered">
									                <thead class="thead-light">
									                    <tr>
									                        <th>ID</th>
									                        <th>Nome</th>
									                        <th>Quantità</th>
									                        <th>Costo</th>
									                        <th>Sesso</th>
									                        <th>Categoria</th>
									                        <th>Action</th>
									                    </tr>
									                </thead>
									                <tbody>
									                    <%-- Recupero tutti i prodotti --%>
									                    <%
									                        ProductBean prodottoBean = new ProductBean();
									
									
									                        if (prodotti != null && !prodotti.isEmpty()) {
									                            for (ProductBean prodotto : prodotti) {
									                            	prodottoBean = prodotto;
									                    %>
									                    <tr>
									                        <td><%= prodotto.getId() %></td>
									                        <td><%= prodotto.getName() %></td>
									                        <td><%= prodotto.getQuantity() %></td>
									                        <td><%= prodotto.getPrice() %></td>
									                        <td><%= prodotto.getGender() %></td>
									                        <td><%= prodotto.getCategoryName() %></td>
									                        <td>
									                            <a href="adminEditProduct?mode=edit&prodotto=<% out.println(prodotto.getId());%>" class="btn btn-info btn-sm">Modifica</a>
									                            <a href="adminEditProduct?mode=delete&prodotto=<%= prodottoBean.getId() %>" class="btn btn-danger btn-sm">Elimina</a>
									                        </td>
									                    </tr>
									                    <%
									                            }
									                        } else {
									                    %>
									                    <tr>
									                        <td colspan="7">Nessun prodotto trovato.</td>
									                    </tr>
									                    <%
									                        }
									                    %>
									                </tbody>
									            </table>
									        </div>
									    </div>
									</div>

                     <!-- Single Tab Content End -->
                 </div>
                 </div>
                 <!-- My Account Tab Content End -->
                </div>
                </div>
                <!-- My Account Page End -->
            </div>
        </div>
    </div>
</div>
<!--== Page Content Wrapper End ==-->

<jsp:include page="fragments/Footer.jsp" />

<!-- Scroll to Top Start -->
<a href="#" class="scrolltotop"><i class="fa fa-angle-up"></i></a>
<!-- Scroll to Top End -->

<script>
function resetFilters() {
    document.getElementById("inputEmail").value = "";
    document.getElementById("inputDataInizio").value = "";
    document.getElementById("inputDataFine").value = "";
    // Aggiungi un parametro reset per azzerare i filtri
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
