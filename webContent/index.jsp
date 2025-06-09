<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registrazione</title>
  <link rel="stylesheet" href="css/index.css">
</head>
<body>

<%
  String error = (String) request.getAttribute("error");
  String registered = request.getParameter("registered");
%>

<% if (error != null) { %>
  <div class="alert alert-danger"><%= error %></div>
<% } %>

<% if ("true".equals(registered)) { %>
  <div class="alert alert-success">Registration successful! You can now log in.</div>
<% } %>

<a href="loginRegistration.html" class="back">Back to login</a>

</body>
</html>
