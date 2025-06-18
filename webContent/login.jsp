<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LaVita Jewels - Accedi</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <jsp:include page="fragments/Header.jsp" />

    <main class="login-page-content">
        <div class="login-container">
            <h2>Accedi al tuo account</h2>
            <%--<form action="LoginServlet" method="post" class="login-form">  --%>
            <form action="<%= request.getContextPath() %>/LoginServlet" method="post" class="login-form">
                <div class="form-group">
                    <label for="username">Nome Utente o Email</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="button-primary">Accedi</button>
                <div class="form-links">
                    <a href="#">Password dimenticata?</a>
                    <span>Non hai un account? <a href="<%= request.getContextPath() %>/register.jsp" >Registrati ora</a></span>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="fragments/Footer.jsp" />
</body>
</html>