<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Book Management</h1>
			<p class="text-gray-600 mt-1">Manage your books and educational
				materials</p>
		</div>
		<a href="product?action=add"
			class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
			<i class="fas fa-plus mr-2"></i>Add Book
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
		<form method="get" action="product"
			class="flex flex-col md:flex-row gap-4">
			<input type="hidden" name="action" value="list">
			<div class="flex-1">
				<input type="text" name="search" value="${param.search}"
					placeholder="Search books by title, author, or ISBN..."
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>
			<button type="submit"
				class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center justify-center">
				<i class="fas fa-search mr-2"></i>Search
			</button>
			<c:if test="${not empty param.search}">
				<a href="product?action=list"
					class="bg-gray-500 text-white px-6 py-3 rounded-lg hover:bg-gray-600 transition-colors duration-200 flex items-center justify-center">
					<i class="fas fa-times mr-2"></i>Clear
				</a>
			</c:if>
		</form>
	</div>

	<!-- Search Results Info -->
	<c:if test="${not empty param.search}">
		<div class="mb-4 bg-blue-50 border border-blue-200 rounded-lg p-4">
			<div class="flex items-center">
				<i class="fas fa-search text-blue-500 mr-2"></i>
				<span class="text-blue-800">
					Search results for "<strong>${param.search}</strong>" - 
					<c:choose>
						<c:when test="${empty products}">No books found</c:when>
						<c:otherwise>${fn:length(products)} book(s) found</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
	</c:if>

	<!-- Products Table -->
	<div
		class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
		<div class="overflow-x-auto">
			<table class="min-w-full divide-y divide-gray-200">
				<thead class="bg-gray-50">
					<tr>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Book</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Author</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							ISBN</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Price</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Stock</th>
						<th
							class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Actions</th>
					</tr>
				</thead>
				<tbody class="bg-white divide-y divide-gray-200">
					<c:forEach var="product" items="${products}">
						<tr class="hover:bg-gray-50 transition-colors duration-200">
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="flex items-center">
									<div
										class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center mr-3">
										<i class="fas fa-book text-green-600 text-lg"></i>
									</div>
									<div>
										<div class="text-sm font-medium text-gray-900">${product.name}
										</div>
										<div class="text-sm text-gray-500">ID:
											${product.productId}</div>
									</div>
								</div>
							</td>
							<td class="px-6 py-4">
								<div class="text-sm text-gray-900">
									<c:choose>
										<c:when test="${not empty product.author}">${product.author}</c:when>
										<c:otherwise><em class="text-gray-500">Not specified</em></c:otherwise>
									</c:choose>
								</div>
							</td>
							<td class="px-6 py-4">
								<div class="text-sm text-gray-900">
									<c:choose>
										<c:when test="${not empty product.isbn}">${product.isbn}</c:when>
										<c:otherwise><em class="text-gray-500">Not specified</em></c:otherwise>
									</c:choose>
								</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm font-medium text-gray-900">
									<fmt:formatNumber value="${product.price}" type="currency"
										currencySymbol="Rs. " />
								</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm">
									<c:choose>
										<c:when test="${product.quantity > 0}">
											<span class="px-2 py-1 text-xs bg-green-100 text-green-800 rounded-full">
												${product.quantity} in stock
											</span>
										</c:when>
										<c:otherwise>
											<span class="px-2 py-1 text-xs bg-red-100 text-red-800 rounded-full">
												Out of stock
											</span>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
								<div class="flex items-center space-x-2">
									<a href="product?action=view&id=${product.productId}"
										class="text-orange-600 hover:text-orange-900 transition-colors duration-200">
										<i class="fas fa-eye"></i>
									</a> <a href="product?action=edit&id=${product.productId}"
										class="text-blue-600 hover:text-blue-900 transition-colors duration-200">
										<i class="fas fa-edit"></i>
									</a> <a href="product?action=delete&id=${product.productId}"
										onclick="return confirm('Are you sure you want to delete this book?')"
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

		<c:if test="${empty products}">
			<div class="text-center py-12">
				<div
					class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
					<i class="fas fa-book text-gray-400 text-2xl"></i>
				</div>
				<h3 class="text-lg font-medium text-gray-900 mb-2">No books
					found</h3>
				<p class="text-gray-500 mb-4">Get started by adding your first
					book.</p>
				<a href="product?action=add"
					class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors duration-200">
					Add Book </a>
			</div>
		</c:if>
	</div>
</div>