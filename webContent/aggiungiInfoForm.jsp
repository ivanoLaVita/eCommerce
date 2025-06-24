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

<div id="page-content-wrapper" class="p-9">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 m-auto">
                <div class="login-register-wrapper">
                    <div class="tab-content" id="login-reg-tabcontent">
                        <div class="tab-pane fade show active" id="register" role="tabpanel">
                            <div class="login-reg-form-wrap">
                                <form action="modificaInfo" method="post">
                                <input type="hidden" name="utente" value="<%= request.getSession().getAttribute("email")%>">
								<input type="hidden" name="target" value="indirizzo">
								<input type="hidden" name="mode" value="add">
	                                <div class="single-input-item">
	                                    <input type="text" placeholder="Via" id="via" name="via" required>
	                                </div>
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Città" id="citta" name="citta" required>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="text" placeholder="CAP" id="CAP" name="CAP" required></input>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Civico" id="civico" name="civico" required>
                                    </div>
                                    <div class="single-input-item">
                                        <input type="text" placeholder="Provincia" id="provincia" name="provincia" required>
                                    </div>
                                    <div class="single-input-item">
                                        <button class="btn-login" type="submit">Aggiungi Indirizzo</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--== Page Content Wrapper End ==-->

<!--== Page Content Wrapper End ==-->
<%}%>
<jsp:include page="fragments/Footer.jsp" />
</body>
</html>
