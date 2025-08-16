<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Bill Management</h1>
			<p class="text-gray-600 mt-1">Create and manage customer bills</p>
		</div>
		<a href="bill?action=create"
			class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
			<i class="fas fa-plus mr-2"></i>Create Bill
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

	<!-- Search Form -->
	<div
		class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 mb-6">
		<form method="get" action="bill"
			class="flex flex-col md:flex-row gap-4">
			<input type="hidden" name="action" value="list">
			<div class="flex-1">
				<input type="text" name="search" value="${param.search}"
					placeholder="Search bills by customer or bill number..."
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>
			<button type="submit"
				class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center justify-center">
				<i class="fas fa-search mr-2"></i>Search
			</button>
		</form>
	</div>

	<!-- Bills Table -->
	<div
		class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
		<div class="overflow-x-auto">
			<table class="min-w-full divide-y divide-gray-200">
				<thead class="bg-gray-50">
					<tr>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bill
							Info</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Customer</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Amount</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
					</tr>
				</thead>
				<tbody class="bg-white divide-y divide-gray-200">
					<c:forEach var="bill" items="${bills}">
						<tr class="hover:bg-gray-50 transition-colors duration-200">
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="flex items-center">
									<div
										class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mr-3">
										<i class="fas fa-file-invoice-dollar text-purple-600 text-lg"></i>
									</div>
									<div>
										<div class="text-sm font-medium text-gray-900">Bill
											#${bill.billId}</div>
										<div class="text-sm text-gray-500 capitalize">${bill.status}</div>
									</div>
								</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm font-medium text-gray-900">${bill.customerName}</div>
								<div class="text-sm text-gray-500">Acc:
									${bill.accountNumber}</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm font-medium text-gray-900">
									<fmt:formatNumber value="${bill.totalAmount}" type="currency"
										currencySymbol="Rs. " />
								</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm text-gray-900">${bill.billDate}</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
								<div class="flex items-center space-x-2">
									<a href="bill?action=view&id=${bill.billId}"
										class="text-orange-600 hover:text-orange-900 transition-colors duration-200">
										<i class="fas fa-eye"></i>
									</a> <a href="bill?action=print&id=${bill.billId}"
										class="text-green-600 hover:text-green-900 transition-colors duration-200">
										<i class="fas fa-print"></i>
									</a> <a href="bill?action=delete&id=${bill.billId}"
										onclick="return confirm('Are you sure you want to delete this bill?')"
										class="text-red-600 hover:text-red-900 transition-colors duration-200">
										<i class="fas fa-trash"></i>
									</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<c:if test="${empty bills}">
			<div class="text-center py-12">
				<div
					class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
					<i class="fas fa-file-invoice-dollar text-gray-400 text-2xl"></i>
				</div>
				<h3 class="text-lg font-medium text-gray-900 mb-2">No bills
					found</h3>
				<p class="text-gray-500 mb-4">Get started by creating your first
					bill.</p>
				<a href="bill?action=create"
					class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors duration-200">
					Create Bill </a>
			</div>
		</c:if>
	</div>
</div>
