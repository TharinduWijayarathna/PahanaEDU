<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="mb-6">
		<h1 class="text-3xl font-bold text-gray-900">Add New Customer</h1>
		<p class="text-gray-600 mt-1">Create a new customer account</p>
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

	<!-- Form Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<c:if test="${not empty accountNumber}">
			<div
				class="mb-6 bg-orange-50 border border-orange-200 rounded-lg p-4 text-orange-700">
				<div class="flex items-center">
					<i class="fas fa-info-circle mr-2"></i> <strong>Generated
						Account Number:</strong> ${accountNumber}
				</div>
			</div>
		</c:if>

		<form action="customer" method="post" class="space-y-6">
			<input type="hidden" name="action" value="add">

			<div>
				<label for="accountNumber"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-hashtag mr-2"></i>Account Number
				</label> <input type="text" id="accountNumber" name="accountNumber" required
					value="${accountNumber}" readonly
					class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg text-gray-600">
			</div>

			<div>
				<label for="name"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-user mr-2"></i>Customer Name
				</label> <input type="text" id="name" name="name" required
					placeholder="Enter customer name" value="${name}"
					class="w-full px-4 py-3 border ${fieldErrors.name != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200">
				<c:if test="${fieldErrors.name != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.name}</p>
				</c:if>
			</div>

			<div>
				<label for="address"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-map-marker-alt mr-2"></i>Address
				</label>
				<textarea id="address" name="address" required
					placeholder="Enter customer address"
					class="w-full px-4 py-3 border ${fieldErrors.address != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200 min-h-[120px] resize-none">${address}</textarea>
				<c:if test="${fieldErrors.address != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.address}</p>
				</c:if>
			</div>

			<div>
				<label for="telephone"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-phone mr-2"></i>Telephone
				</label> <input type="tel" id="telephone" name="telephone" required
					placeholder="Enter telephone number" value="${telephone}"
					class="w-full px-4 py-3 border ${fieldErrors.telephone != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200">
				<c:if test="${fieldErrors.telephone != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.telephone}</p>
				</c:if>
			</div>

			<div class="flex items-center gap-4 pt-4">
				<button type="submit"
					class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
					<i class="fas fa-save mr-2"></i>Add Customer
				</button>
				<a href="customer?action=list"
					class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
					<i class="fas fa-times mr-2"></i>Cancel
				</a>
			</div>
		</form>
	</div>
</div>
