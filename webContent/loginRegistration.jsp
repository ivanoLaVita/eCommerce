<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Sign In / Sign Up</title>

  <!-- Normalize.css e Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

  <!-- CSS locale -->
  <link rel="stylesheet" href="assets/css/styleLogin_Registration.css">
</head>
<body>

<div class="container">

  <!-- Messaggio dinamico dal server -->
  <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
  %>
    <div class="server-message"><%= message %></div>
  <%
    }
  %>

  <div class="forms-container">
    <div class="signin-signup">

      <!-- SIGN IN FORM -->
      <form action="LoginServlet" method="post" class="sign-in-form">
        <h2 class="title">Sign In</h2>
        <div class="input-field">
          <i class="fas fa-user"></i>
          <input type="text" name="username" placeholder="Username" required>
        </div>
        <div class="input-field">
          <i class="fas fa-lock"></i>
          <input type="password" name="password" placeholder="Password" required>
        </div>
        <input type="submit" value="Login" class="btn solid">
      </form>

      <!-- SIGN UP FORM -->
      <form action="RegistrationServlet" method="post" class="sign-up-form">
        <h2 class="title">Sign Up</h2>
        <div class="input-field">
          <i class="fas fa-user"></i>
          <input type="text" name="username" placeholder="Username" required>
        </div>
        <div class="input-field">
          <i class="fas fa-envelope"></i>
          <input type="email" name="email" placeholder="Email" required>
        </div>
        <div class="input-field">
          <i class="fas fa-user"></i>
          <input type="text" name="firstName" placeholder="First Name" required>
        </div>
        <div class="input-field">
          <i class="fas fa-user"></i>
          <input type="text" name="lastName" placeholder="Last Name">
        </div>
        <div class="input-field">
          <i class="fas fa-lock"></i>
          <input type="password" name="password" placeholder="Password" required>
        </div>
        <div class="input-field">
          <i class="fas fa-lock"></i>
          <input type="password" name="passwordConfirm" placeholder="Confirm Password" required>
        </div>
        <input type="submit" value="Sign Up" class="btn solid">
      </form>

    </div>
  </div>

  <!-- Pannelli animati -->
  <div class="panels-container">
    <div class="panel left-panel">
      <div class="content">
        <h3>New Here?</h3>
        <p>Join us and explore unique jewelry crafted with care and elegance.</p>
        <button class="btn transparent" id="sign-up-btn">Sign Up</button>
      </div>
      <img src="assets/img/reg.svg" class="image" alt="Sign Up Illustration">
    </div>

    <div class="panel right-panel">
      <div class="content">
        <h3>Already a member?</h3>
        <p>Welcome back! Login and continue your shopping journey with us.</p>
        <button class="btn transparent" id="sign-in-btn">Sign In</button>
      </div>
      <img src="assets/img/login.svg" class="image" alt="Sign In Illustration">
    </div>
  </div>

</div>

<!-- JavaScript per animazioni/interazioni -->
<script src="js/app.js" defer></script>

</body>
</html>
