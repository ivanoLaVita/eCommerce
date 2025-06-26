<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

            <form id="registrazione" action="<%= request.getContextPath() %>/RegistrationServlet" method="post" class="register-form">

                <div class="form-group">
                    <label for="firstName">Nome</label>
                    <input type="text" id="firstName" name="firstName" required>
                    <div id="error-name" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="lastName">Cognome</label>
                    <input type="text" id="lastName" name="lastName" required>
                    <div id="error-surname" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="username">Nome Utente</label>
                    <input type="text" id="username" name="username" required>
                    <div id="error-username" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                    <div id="error-email" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                    <p class="password-note">La password deve contenere almeno 5 caratteri, inclusa una maiuscola, una minuscola e un numero.</p>
                    <div id="error-pwd" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="passwordConfirm">Conferma Password</label>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" required>
                    <div id="error-pwdchk" class="error-message"></div>
                </div>

                <button type="submit" class="button-primary">Registrati</button>

                <div class="form-links">
                    <span>Hai già un account? <a href="login.jsp">Accedi qui</a></span>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="fragments/Footer.jsp" />

    <!-- Inclusione jQuery (se non già incluso nel progetto) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Script validazione -->
    <script src="js/registerValidation.js"></script>
</body>
</html>
