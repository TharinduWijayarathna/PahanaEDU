<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Create New Bill</h1>
			<p class="text-gray-600 mt-1">Generate a new invoice for a
				customer</p>
		</div>
		<a href="bill?action=list"
			class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
			<i class="fas fa-list mr-2"></i>Back to Bills
		</a>
	</div>

	<!-- Alerts -->
	<c:if test="${not empty error}">
		<div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
			<div class="flex items-center">
				<i class="fas fa-exclamation-circle text-red-500 mr-2"></i> <span
					class="text-red-800">${error}</span>
			</div>
		</div>
	</c:if>

	<!-- Bill Form Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<form action="bill" method="post" class="space-y-6">
			<input type="hidden" name="action" value="create">

			<!-- Customer Selection -->
			<div>
				<label for="customerId"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-user mr-2"></i>Select Customer
				</label> <select name="customerId" id="customerId" required
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
					<option value="">Choose a customer...</option>
					<c:forEach var="customer" items="${customers}">
						<option value="${customer.customerId}">${customer.name}
							(${customer.accountNumber})</option>
					</c:forEach>
				</select>
			</div>

			<!-- Bill Items Section -->
			<div class="pt-4">
				<h3
					class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
					<i class="fas fa-shopping-cart mr-2 text-orange-600"></i>Bill Items
				</h3>
				<div id="items-container" class="space-y-3">
					<div
						class="grid grid-cols-12 gap-3 item-row bg-orange-50 border border-orange-200 rounded-lg p-3">
						<div class="col-span-12 md:col-span-5">
							<select name="productId" required
								class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
								<option value="">Select Product...</option>
								<c:forEach var="product" items="${products}">
									<option value="${product.productId}"
										data-price="${product.price}">${product.name} - Rs.
										${product.price}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-span-6 md:col-span-2">
							<input type="number" name="quantity" min="1" value="1" required
								class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200"
								placeholder="Qty">
						</div>
						<div class="col-span-6 md:col-span-2">
							<input type="text"
								class="unit-price w-full px-3 py-2 border border-gray-300 rounded-lg bg-gray-50"
								placeholder="Unit Price" readonly>
						</div>
						<div class="col-span-6 md:col-span-2">
							<input type="text"
								class="subtotal w-full px-3 py-2 border border-gray-300 rounded-lg bg-gray-50"
								placeholder="Subtotal" readonly>
						</div>
						<div
							class="col-span-6 md:col-span-1 flex items-center justify-end">
							<button type="button"
								class="remove-btn bg-red-600 text-white px-3 py-2 rounded hover:bg-red-700 transition-colors duration-200"
								onclick="removeItem(this)">
								<i class="fas fa-times"></i>
							</button>
						</div>
					</div>
				</div>
				<button type="button"
					class="mt-3 bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition-colors duration-200 flex items-center"
					onclick="addItem()">
					<i class="fas fa-plus mr-2"></i>Add Item
				</button>
			</div>

			<!-- Total Amount -->
			<div
				class="bg-orange-50 border border-orange-200 rounded-lg p-4 text-right">
				<h3 class="text-lg font-semibold text-gray-900">
					Total Amount: <span id="total-amount" class="text-orange-600">Rs.
						0.00</span>
				</h3>
			</div>

			<!-- Submit Button -->
			<div class="text-center">
				<button type="submit"
					class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center mx-auto">
					<i class="fas fa-file-invoice-dollar mr-2"></i>Create Bill
				</button>
			</div>
		</form>
	</div>
</div>

<script>
                    function addItem() {
                        const container = document.getElementById('items-container');
                        const row = container.children[0].cloneNode(true);
                        row.querySelector('select').value = '';
                        row.querySelector('input[name="quantity"]').value = '1';
                        row.querySelector('.unit-price').value = '';
                        row.querySelector('.subtotal').value = '';
                        addEventListeners(row);
                        container.appendChild(row);
                    }

                    function removeItem(btn) {
                        const container = document.getElementById('items-container');
                        if (container.children.length > 1) {
                            btn.closest('.item-row').remove();
                            calculateTotal();
                        }
                    }

                    function addEventListeners(row) {
                        const select = row.querySelector('select');
                        const quantity = row.querySelector('input[name="quantity"]');
                        const unitPrice = row.querySelector('.unit-price');
                        const subtotal = row.querySelector('.subtotal');

                        select.addEventListener('change', function () {
                            const option = this.options[this.selectedIndex];
                            const price = option.getAttribute('data-price');
                            unitPrice.value = price ? ('Rs. ' + price) : '';
                            calculateSubtotal(row);
                        });

                        quantity.addEventListener('input', function () {
                            calculateSubtotal(row);
                        });
                    }

                    function calculateSubtotal(row) {
                        const select = row.querySelector('select');
                        const quantity = row.querySelector('input[name="quantity"]');
                        const unitPrice = row.querySelector('.unit-price');
                        const subtotal = row.querySelector('.subtotal');

                        const option = select.options[select.selectedIndex];
                        const price = option.getAttribute('data-price');
                        const qty = quantity.value;

                        if (price && qty) {
                            const value = parseFloat(price) * parseInt(qty);
                            subtotal.value = 'Rs. ' + value.toFixed(2);
                        } else {
                            subtotal.value = '';
                        }
                        calculateTotal();
                    }

                    function calculateTotal() {
                        const subtotals = document.querySelectorAll('.subtotal');
                        let total = 0;
                        subtotals.forEach(subtotal => {
                            const value = subtotal.value.replace('Rs. ', '');
                            if (value) {
                                total += parseFloat(value);
                            }
                        });
                        document.getElementById('total-amount').textContent = 'Rs. ' + total.toFixed(2);
                    }
                </script>