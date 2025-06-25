<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*" %>
<%@ include file="fragments/Header.jsp" %>
<link rel="stylesheet" href="assets/css/style.css">
<%
    UsersBean user = (UsersBean) session.getAttribute("user");
    Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
    double totalCost = 0;
    ProductDAO productDAO = new ProductDAO();
%>

<div class="container">
    <h2 class="title">Checkout</h2>

    <!-- Riepilogo ordine -->
    <div class="section">
        <h3>Riepilogo ordine</h3>
        <table class="cart-table">
            <tr>
                <th>Immagine</th>
                <th>Nome</th>
                <th>Quantità</th>
                <th>Prezzo</th>
                <th>Subtotale</th>
            </tr>
            <%
                if (cart != null) {
                    for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                        int productId = entry.getKey();
                        int quantity = entry.getValue();
                        ProductBean p = productDAO.doRetrieveById(productId);
                        double subtotal = p.getPrice() * quantity;
                        totalCost += subtotal;
            %>
            <tr>
                <td><img src="<%=p.getImage()%>" width="100"/></td>
                <td><%=p.getName()%></td>
                <td><%=quantity%></td>
                <td>€<%=p.getPrice()%></td>
                <td>€<%=subtotal%></td>
            </tr>
            <%      }
                } else {
            %>
            <tr><td colspan="5">Il carrello è vuoto.</td></tr>
            <% } %>
        </table>

        <h3 class="cart-total">Totale: €<%=totalCost%></h3>
    </div>

    <!-- Form conferma ordine -->
    <form action="OrdineServlet" method="post">
        <!-- Indirizzo di fatturazione -->
        <div class="section">
            <label for="addressId">Seleziona un indirizzo:</label>
            <select name="addressId" id="addressId" required>
                <%
                    AddressDAO addressDAO = new AddressDAO();
                    List<AddressBean> addresses = addressDAO.doRetrieveByEmail(user.getEmail());
                    for (AddressBean addr : addresses) {
                %>
                    <option value="<%=addr.getId()%>">
                        <%=addr.getStreet()%>, <%=addr.getStreetNumber()%> - <%=addr.getCity()%> (<%=addr.getProvince()%>)
                    </option>
                <%
                    }
                %>
            </select>
            <button type="button" class="button-secondary" onclick="toggleAddressForm()">Aggiungi nuovo indirizzo</button>

            <div id="newAddressForm" style="display:none; margin-top:20px;">
                <div class="single-input-item">
                    <label>Città:</label><input type="text" name="newCity">
                    <label>Provincia:</label><input type="text" name="newProvince">
                    <label>CAP:</label><input type="text" name="newPostalCode">
                    <label>Via:</label><input type="text" name="newStreet">
                    <label>Civico:</label><input type="text" name="newStreetNumber">
                </div>
            </div>
        </div>

        <!-- Metodo di pagamento -->
        <div class="section">
            <label for="paymentId">Metodo di pagamento:</label>
            <select name="paymentId" id="paymentId" required>
                <%
                    PaymentMethodDAO payDAO = new PaymentMethodDAO();
                    List<PaymentMethodBean> methods = payDAO.doRetrieveByEmail(user.getEmail());
                    for (PaymentMethodBean method : methods) {
                        String desc = method.getType().toString();
                        if (method.getType().toString().equals("CARD")) {
                            desc += " - " + method.getCardNumber();
                        } else {
                            desc += " - " + method.getIban();
                        }
                %>
                    <option value="<%=method.getId()%>"><%=desc%></option>
                <%
                    }
                %>
            </select>
            <button type="button" class="button-secondary" onclick="togglePaymentForm()">Aggiungi nuovo metodo</button>

            <div id="newPaymentForm" style="display:none; margin-top:20px;">
                <div class="single-input-item">
                    <label>Tipo:</label>
                    <select name="newPaymentType">
                        <option value="CARD">Carta</option>
                        <option value="IBAN">IBAN</option>
                    </select>
                    <label>Numero Carta:</label><input type="text" name="newCardNumber">
                    <label>IBAN:</label><input type="text" name="newIban">
                </div>
            </div>
        </div>

        <input type="hidden" name="totalCost" value="<%=totalCost%>" />
        <input type="submit" class="button-primary" value="Conferma ordine" />
    </form>
</div>

<script>
function toggleAddressForm() {
    const form = document.getElementById('newAddressForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

function togglePaymentForm() {
    const form = document.getElementById('newPaymentForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}
</script>

<%@ include file="fragments/Footer.jsp" %>
