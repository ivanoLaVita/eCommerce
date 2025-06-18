<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LaVita Jewels - Registrati</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <jsp:include page="fragments/Header.jsp" />

    <main class="register-page-content">
        <div class="register-container">
            <h2>Crea il tuo account LaVita Jewels</h2>
            <%--<form action="RegistrationServlet" method="post" class="register-form"> --%>
            <form action="<%= request.getContextPath() %>/RegistrationServlet" method="post" class="register-form">
                <div class="form-group">
                    <label for="firstName">Nome</label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>
                <div class="form-group">
                    <label for="lastName">Cognome</label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>
                <div class="form-group">
                    <label for="username">Nome Utente</label> <%-- NUOVA SEZIONE PER L'USERNAME --%>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                    <p class="password-note">La password deve contenere almeno 8 caratteri, inclusa una maiuscola, una minuscola e un numero.</p>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Conferma Password</label>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" required>
                </div>
                <button type="submit" class="button-primary">Registrati</button>
                <div class="form-links">
                    <span>Hai già un account? <a href="login.jsp">Accedi qui</a></span>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="fragments/Footer.jsp" />
</body>
</html>