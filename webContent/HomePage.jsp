<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>LaVitaJewelry - Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #fefefe;
        }

        header {
            background-color: #d4af37;
            padding: 20px;
            color: white;
            text-align: center;
        }

        nav {
            background-color: #f5f5f5;
            padding: 10px;
            text-align: center;
        }

        nav a {
            margin: 0 15px;
            text-decoration: none;
            color: #333;
            font-weight: bold;
        }

        .hero {
            background-image: url('images/jewelry-banner.jpg');
            background-size: cover;
            background-position: center;
            height: 400px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            text-shadow: 1px 1px 4px #000;
            font-size: 2em;
        }

        main {
            padding: 40px;
            text-align: center;
        }

        footer {
            background-color: #222;
            color: white;
            text-align: center;
            padding: 20px;
        }

        .cta-button {
            background-color: #d4af37;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            text-decoration: none;
            margin-top: 20px;
            display: inline-block;
        }
    </style>
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<div class="hero">
    <div>Scopri la nuova collezione primavera</div>
</div>

<main>
    <h2>Benvenuto su LaVitaJewelry</h2>
    <p>Gioielli unici per ogni occasione. Realizzati con cura e passione.</p>
    <a class="cta-button" href="products.jsp">Sfoglia i nostri prodotti</a>
</main>

<jsp:include page="fragments/Footer.jsp" />

</body>
</html>