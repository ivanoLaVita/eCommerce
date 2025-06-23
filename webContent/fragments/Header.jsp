<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    Integer cartSize = (Integer) session.getAttribute("cartSize");
    if (cartSize == null) {
        cartSize = 0;
    }

    Boolean logged = (Boolean) session.getAttribute("logged");
    if (logged == null) logged = false;
%>

<header class="main-header">
    <div class="header-container">
        <div class="logo">
            <a href="<%= request.getContextPath() %>/HomePage.jsp">
                <img src="<%= request.getContextPath() %>/assets/img/Logo.png" alt="LaVita Jewels Logo">
            </a>
        </div>
        <nav class="main-nav">
            <ul>
                <li><a href="<%= request.getContextPath() %>/HomePage.jsp">Home</a></li>
                <li><a href="#">Collezioni</a></li>
                <li><a href="#">Novità</a></li>
                <li><a href="#">Chi Siamo</a></li>
                <li><a href="#">Contatti</a></li>
            </ul>
        </nav>
        <div class="header-actions">
            <% if (!logged) { %>
                <a href="<%= request.getContextPath() %>/login.jsp" class="button-secondary">Accedi</a>
                <a href="<%= request.getContextPath() %>/register.jsp" class="button-secondary">Registrati</a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/memberArea.jsp" class="button-secondary">Area Personale</a>
                <a href="<%= request.getContextPath() %>/LogoutServlet" class="button-secondary">Logout</a>
            <% } %>
            <a href="<%= request.getContextPath() %>/cart.jsp" class="cart-icon">
                🛒 <span class="cart-count"><%= cartSize %></span>
            </a>
        </div>
    </div>
</header>
