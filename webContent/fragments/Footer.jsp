<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<footer class="main-footer">
    <div class="footer-container">
        <div class="footer-section about-us">
            <h3>LaVita Jewels</h3>
            <p>Creiamo gioielli che riflettono la tua storia e la tua bellezza.</p>
            <p>&copy; <%= java.time.Year.now() %> LaVita Jewels. Tutti i diritti riservati.</p>
        </div>
        <div class="footer-section quick-links">
            <h3>Link Utili</h3>
            <ul>
                <li><a href="<%= request.getContextPath() %>/HomePage.jsp">Home</a></li>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Politica Resi</a></li>
                <li><a href="#">Termini e Condizioni</a></li>
                <li><a href="#">Privacy Policy</a></li>
            </ul>
        </div>
        <div class="footer-section social-media">
            <h3>Seguici</h3>
            <div class="social-icons">
                <a href="#"><img src="<%= request.getContextPath() %>/assets/img/icon_facebook.png" alt="Facebook"></a>
                <a href="#"><img src="<%= request.getContextPath() %>/assets/img/icon_instagram.png" alt="Instagram"></a>
                <a href="#"><img src="<%= request.getContextPath() %>/assets/img/icon_pinterest.png" alt="Pinterest"></a>
            </div>
        </div>
    </div>
</footer>