$(document).ready(function () {
    $("#search-input").on("keyup", function () {
        let query = $(this).val().trim();

        if (query.length < 2) {
            $("#searchResults").html("");
            return;
        }

        $.ajax({
            type: "GET",
            url: "search",
            data: { query: query },
            dataType: "json",
            success: function (data) {
                $("#searchResults").html("");

                if (data.length === 0) {
                    $("#searchResults").html("<p>Nessun risultato trovato</p>");
                    return;
                }

                data.forEach(function (product) {
                    $("#searchResults").append(`
                        <div class="search-item">
                            <a href="product.jsp?id=${product.id}">
                                ${product.name} - €${product.price.toFixed(2)}
                            </a>
                        </div>
                    `);
                });
            },
            error: function () {
                $("#searchResults").html("<p>Errore durante la ricerca</p>");
            }
        });
    });
});
