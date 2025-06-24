<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Informazioni</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<!--== Page Title Area Start ==-->
<div id="page-title-area">
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <div class="page-title-content">
                    <h1>Aggiungi Informazioni</h1>
                    <ul class="breadcrumb">
                        <li><a href="HomePage.jsp">Home</a></li>
                       	<li><a href="memberArea.jsp">Indietro</a></li>
                        <li><a class="active">Aggiungi Informazoni</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--== Page Title Area End ==-->

<% String target = (String) request.getAttribute("target"); 
		String utente = (String) request.getAttribute("email");
		String error = (String) request.getSession().getAttribute("error");
		
		if(error != null){%> 
			<p><%out.println(error); %>
		<% request.getSession().removeAttribute("error");
		}
		if (target.equals("indirizzo")) {%>

<!--== Page Content Wrapper Start ==-->



<!--== Page Content Wrapper End ==-->
<%}%>
<jsp:include page="fragments/Footer.jsp" />
</body>
</html>
