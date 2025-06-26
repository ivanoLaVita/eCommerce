$(document).ready(function() {
    updatePrice();
});

function updateCart(input, productId) {
    let quantity = $(input).val();
    let maxQuantity = parseInt($(input).attr('max'));

    if (quantity > maxQuantity) {
        $(input).val(maxQuantity);
        quantity = maxQuantity;
    }

    let formData = {
        productId: productId,
        mode: "update",
        quantity: quantity
    };

    $.ajax({
        type: "GET",
        url: "cart", 
        data: formData,
        dataType: "html",
        success: function(data) {
            if (data === "reload") {
                location.replace("./cart.jsp");
            } else {
                updatePrice();
            }
        }
    });
}

function updatePrice() {
    let formData = {
        mode: "getTotal"
    };

    $.ajax({
        type: "GET",
        url: "cart", 
        data: formData,
        dataType: "html",
        success: function(data) {
            let price = parseFloat(data.replace(",", "."));
            console.log(price);

            $("#netto").html(price.toFixed(2));
            $("#spedizione").html("10.00");
            price += 10;
            $("#prezzoTot").html(price.toFixed(2));
        }
    });
}

function proseguiOrdine() {
    let formData = {
        totalCost: $("#prezzoTot").html() 
    };

    $.ajax({
        type: "GET",
        url: "order", 
        data: formData,
        dataType: "html",
        success: function(data) {
            console.log($("#prezzoTot").html());
            window.location.replace(data);
        }
    });
}
