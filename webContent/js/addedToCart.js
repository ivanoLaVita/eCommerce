function addToCart(productId) {
	let formData = {
		mode: "add",
		productId: productId,
		quantity: "1"
	};

	$.ajax({
		type: "GET",
		url: "cart",
		data: formData,
		dataType: "html"
	});
}

function addToCartN(productId) {
	let formData = {
		mode: "add",
		productId: productId,
		quantity: $("#quantita").val()
	};

	$.ajax({
		type: "GET",
		url: "cart",
		data: formData,
		dataType: "html"
	});
}
