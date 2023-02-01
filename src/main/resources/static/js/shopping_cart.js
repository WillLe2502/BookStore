decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.'; 

$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});

	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});	
	
	$(".linkRemove").on("click", function(evt) {
		evt.preventDefault();
		removeBook($(this));
	});
});

function decreaseQuantity(link) {
	bookId = link.attr("pid");
	quantityInput = $("#quantity" + bookId);
	newQuantity = parseInt(quantityInput.val()) - 1;

	if (newQuantity > 0) {
		quantityInput.val(newQuantity);
		updateQuantity(bookId, newQuantity);
	} else {
		showWarningModal('Minimum quantity is 1');
	}	
}

function increaseQuantity(link) {
		bookId = link.attr("pid");
		quantityInput = $("#quantity" + bookId);
		newQuantity = parseInt(quantityInput.val()) + 1;

		if (newQuantity <= 5) {
			quantityInput.val(newQuantity);
			updateQuantity(bookId, newQuantity);
		} else {
			showWarningModal('Maximum quantity is 5');
		}	
}

function updateQuantity(bookId, quantity) {
	url = contextPath + "cart/update/" + bookId + "/" + quantity;

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, bookId);
		updateTotal();
	}).fail(function() {
		showErrorModal("Error while updating product quantity.");
	});	
}

function updateSubtotal(updatedSubtotal, bookId) {
	$("#subtotal" + bookId).text(formatCurrency(updatedSubtotal));
}

function updateTotal() {
	total = 0.0;
	bookCount = 0;

	$(".subtotal").each(function(index, element) {
		bookCount++;
		total += parseFloat(clearCurrencyFormat(element.innerHTML));
	});

	if (bookCount < 1) {
		showEmptyShoppingCart();
	} else {
		$("#total").text(formatCurrency(total));		
	}
}

function showEmptyShoppingCart() {
	$("#sectionTotal").hide();
	$("#sectionEmptyCartMessage").removeClass("d-none");
}

function removeBook(link) {
	url = link.attr("href");

	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
		removeProductHTML(rowNumber);
		updateTotal();
		updateCountNumbers();

		showModalDialog("Shopping Cart", response);

	}).fail(function() {
		showErrorModal("Error while removing product.");
	});				
}

function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
}

function updateCountNumbers() {
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	}); 
}

function formatCurrency(amount) {
	return $.number(amount, decimalDigits, decimalSeparator, thousandsSeparator);
}

function clearCurrencyFormat(numberString) {
	result = numberString.replaceAll(thousandsSeparator, "");
	return result.replaceAll(decimalSeparator, ".");
}