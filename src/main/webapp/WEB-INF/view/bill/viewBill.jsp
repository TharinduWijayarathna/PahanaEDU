<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Bill Details</h1>
			<p class="text-gray-600 mt-1">View and manage bill information</p>
		</div>
		<a href="bill?action=list"
			class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
			<i class="fas fa-list mr-2"></i>Back to Bills
		</a>
	</div>

	<!-- Alerts -->
	<c:if test="${not empty success}">
		<div class="mb-6 bg-green-50 border border-green-200 rounded-lg p-4">
			<div class="flex items-center">
				<i class="fas fa-check-circle text-green-500 mr-2"></i> <span
					class="text-green-800">${success}</span>
			</div>
		</div>
	</c:if>

	<c:if test="${not empty error}">
		<div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
			<div class="flex items-center">
				<i class="fas fa-exclamation-circle text-red-500 mr-2"></i> <span
					class="text-red-800">${error}</span>
			</div>
		</div>
	</c:if>

	<!-- Bill Details Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<!-- Bill Header Information -->
		<div
			class="grid grid-cols-1 md:grid-cols-2 gap-6 border-b border-gray-200 pb-6 mb-6">
			<!-- Customer Information -->
			<div>
				<h3
					class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
					<i class="fas fa-user mr-2 text-orange-600"></i>Customer
					Information
				</h3>
				<div class="space-y-2">
					<p class="text-gray-700">
						<i class="fas fa-user-circle mr-2 text-gray-500"></i> <strong>Name:</strong>
						${bill.customerName}
					</p>
					<p class="text-gray-700">
						<i class="fas fa-hashtag mr-2 text-gray-500"></i> <strong>Account
							Number:</strong> ${bill.accountNumber}
					</p>
					<p class="text-gray-700">
						<i class="fas fa-file-invoice mr-2 text-gray-500"></i> <strong>Bill
							ID:</strong> ${bill.billId}
					</p>
					<p class="text-gray-700">
						<i class="fas fa-calendar mr-2 text-gray-500"></i> <strong>Date:</strong>
						${bill.billDate}
					</p>
				</div>
			</div>

			<!-- Bill Status -->
			<div class="text-right md:text-left">
				<h3
					class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
					<i class="fas fa-info-circle mr-2 text-orange-600"></i>Bill Status
				</h3>
				<div class="mb-4">
					<span
						class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium capitalize
                            ${bill.status == 'paid' ? 'bg-green-100 text-green-700' : ''}
                            ${bill.status == 'pending' ? 'bg-yellow-100 text-yellow-700' : ''}
                            ${bill.status == 'cancelled' ? 'bg-red-100 text-red-700' : ''}">
						<i class="fas fa-circle mr-1 text-xs"></i>${bill.status}
					</span>
				</div>

				<!-- Status Update Form -->
				<form action="bill" method="post"
					class="inline-flex items-center gap-2">
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="billId" value="${bill.billId}"> <label
						for="status" class="text-sm text-gray-600">Update Status:</label>
					<select name="status" id="status"
						class="px-3 py-2 border ${fieldErrors.status != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200">
						<option value="pending"
							${status != null ? (status == 'pending' ? 'selected' : '') : (bill.status=='pending' ? 'selected' : '')}>Pending</option>
						<option value="paid" ${status != null ? (status == 'paid' ? 'selected' : '') : (bill.status=='paid' ? 'selected' : '')}>Paid</option>
						<option value="cancelled"
							${status != null ? (status == 'cancelled' ? 'selected' : '') : (bill.status=='cancelled' ? 'selected' : '')}>
							Cancelled</option>
					</select>
					<button type="submit"
						class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors duration-200">
						<i class="fas fa-save mr-1"></i>Update
					</button>
				</form>
				<c:if test="${fieldErrors.status != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.status}</p>
				</c:if>
			</div>
		</div>

		<!-- Bill Items Section -->
		<div class="mb-6">
			<h3
				class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
				<i class="fas fa-shopping-cart mr-2 text-orange-600"></i>Bill Items
			</h3>
			<c:choose>
				<c:when test="${not empty bill.items}">
					<div class="overflow-x-auto border border-gray-200 rounded-lg">
						<table class="min-w-full divide-y divide-gray-200">
							<thead class="bg-orange-50">
								<tr>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-orange-700 uppercase tracking-wider">
										<i class="fas fa-box mr-1"></i>Product
									</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-orange-700 uppercase tracking-wider">
										<i class="fas fa-hashtag mr-1"></i>Quantity
									</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-orange-700 uppercase tracking-wider">
										<i class="fas fa-tag mr-1"></i>Unit Price
									</th>

									<th
										class="px-6 py-3 text-left text-xs font-medium text-orange-700 uppercase tracking-wider">
										<i class="fas fa-calculator mr-1"></i>Subtotal
									</th>
								</tr>
							</thead>
							<tbody class="bg-white divide-y divide-gray-200">
								<c:forEach var="item" items="${bill.items}">
									<tr class="hover:bg-gray-50 transition-colors duration-200">
										<td class="px-6 py-4 text-gray-900 font-medium">
											${item.productName}</td>
										<td class="px-6 py-4 text-gray-700">${item.quantity}</td>
										<td class="px-6 py-4 text-gray-700"><fmt:formatNumber
												value="${item.unitPrice}" type="currency"
												currencySymbol="Rs. " /></td>

										<td class="px-6 py-4 text-gray-900 font-semibold"><fmt:formatNumber
												value="${item.subtotal}" type="currency"
												currencySymbol="Rs. " /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="text-center text-gray-600 py-8 bg-gray-50 rounded-lg">
						<i class="fas fa-shopping-cart text-4xl text-gray-400 mb-4"></i>
						<p>No items found for this bill.</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Bill Summary -->
		<div class="bg-gray-50 rounded-lg p-6 mb-6">
			<h3 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
				<i class="fas fa-calculator mr-2 text-orange-600"></i>Bill Summary
			</h3>
			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<div class="bg-white rounded-lg p-4 border border-gray-200">
					<div class="flex justify-between items-center mb-2">
						<span class="text-sm text-gray-600">Subtotal:</span>
						<span class="text-sm text-gray-900">
							<fmt:formatNumber value="${bill.totalAmount + (bill.discount > 0 ? bill.totalAmount * bill.discount / 100 : 0)}" type="currency" currencySymbol="Rs. " />
						</span>
					</div>
					<c:if test="${bill.discount > 0}">
						<div class="flex justify-between items-center mb-2">
							<span class="text-sm text-gray-600">Discount (${bill.discount}%):</span>
							<span class="text-sm text-green-600">
								- <fmt:formatNumber value="${bill.totalAmount * bill.discount / 100}" type="currency" currencySymbol="Rs. " />
							</span>
						</div>
					</c:if>
					<hr class="my-2">
					<div class="flex justify-between items-center">
						<span class="text-lg font-semibold text-gray-900">Total Amount:</span>
						<span class="text-xl font-bold text-orange-600">
							<fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="Rs. " />
						</span>
					</div>
				</div>
			</div>
		</div>

		<!-- Action Buttons -->
		<div class="flex items-center gap-4 mt-8 justify-center">
			<a href="bill?action=print&id=${bill.billId}"
				class="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition-colors duration-200 flex items-center">
				<i class="fas fa-print mr-2"></i>Print Bill
			</a> <a href="bill?action=list"
				class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
				<i class="fas fa-arrow-left mr-2"></i>Back
			</a>
		</div>
	</div>
</div>