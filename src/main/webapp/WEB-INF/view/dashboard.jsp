<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="shared/layout.jsp"%>
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
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

	<!-- Welcome Section -->
	<div class="mb-8">
		<h1 class="text-3xl font-bold text-gray-900 mb-2">Welcome back,
			${sessionScope.username}!</h1>
		<p class="text-gray-600">Here's an overview of your billing system</p>
	</div>

	<!-- Statistics Cards -->
	<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
		<!-- Total Customers Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-sm font-medium text-gray-600 mb-1">Total
						Customers</p>
					<p class="text-3xl font-bold text-gray-900">${totalCustomers}</p>
					<p class="text-xs text-green-600 mt-1">
						<i class="fas fa-arrow-up mr-1"></i>Active customers
					</p>
				</div>
				<div
					class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
					<i class="fas fa-users text-blue-600 text-xl"></i>
				</div>
			</div>
		</div>

		<!-- Total Bills Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-sm font-medium text-gray-600 mb-1">Total Bills</p>
					<p class="text-3xl font-bold text-gray-900">${totalBills}</p>
					<p class="text-xs text-green-600 mt-1">
						<i class="fas fa-arrow-up mr-1"></i>Generated bills
					</p>
				</div>
				<div
					class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
					<i class="fas fa-file-invoice-dollar text-green-600 text-xl"></i>
				</div>
			</div>
		</div>

		<!-- Total Items Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-sm font-medium text-gray-600 mb-1">Total Items</p>
					<p class="text-3xl font-bold text-gray-900">${totalProducts}</p>
					<p class="text-xs text-green-600 mt-1">
						<i class="fas fa-arrow-up mr-1"></i>Available products
					</p>
				</div>
				<div
					class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
					<i class="fas fa-book text-purple-600 text-xl"></i>
				</div>
			</div>
		</div>

		<!-- Total Users Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-sm font-medium text-gray-600 mb-1">Total Users</p>
					<p class="text-3xl font-bold text-gray-900">${totalUsers}</p>
					<p class="text-xs text-green-600 mt-1">
						<i class="fas fa-arrow-up mr-1"></i>System users
					</p>
				</div>
				<div
					class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
					<i class="fas fa-user-cog text-orange-600 text-xl"></i>
				</div>
			</div>
		</div>
	</div>

	<!-- Quick Actions -->
	<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
		<a href="customer?action=list" class="group">
			<div
				class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-200 group-hover:border-orange-300">
				<div class="flex items-center justify-between mb-4">
					<div
						class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
						<i class="fas fa-users text-blue-600 text-xl"></i>
					</div>
					<i
						class="fas fa-arrow-right text-gray-400 group-hover:text-orange-500 transition-colors duration-200"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900 mb-2">Customer
					Management</h3>
				<p class="text-gray-600 text-sm">Add, edit, and manage customer
					accounts</p>
			</div>
		</a> <a href="product?action=list" class="group">
			<div
				class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-200 group-hover:border-orange-300">
				<div class="flex items-center justify-between mb-4">
					<div
						class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
						<i class="fas fa-book text-green-600 text-xl"></i>
					</div>
					<i
						class="fas fa-arrow-right text-gray-400 group-hover:text-orange-500 transition-colors duration-200"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900 mb-2">Product
					Management</h3>
				<p class="text-gray-600 text-sm">Manage books and other products</p>
			</div>
		</a> <a href="bill?action=list" class="group">
			<div
				class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-200 group-hover:border-orange-300">
				<div class="flex items-center justify-between mb-4">
					<div
						class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
						<i class="fas fa-file-invoice-dollar text-purple-600 text-xl"></i>
					</div>
					<i
						class="fas fa-arrow-right text-gray-400 group-hover:text-orange-500 transition-colors duration-200"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900 mb-2">Billing
					System</h3>
				<p class="text-gray-600 text-sm">Create and manage customer
					bills</p>
			</div>
		</a>

		<c:if test="${sessionScope.role == 'admin'}">
			<a href="user-management?action=list" class="group">
				<div
					class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-200 group-hover:border-orange-300">
					<div class="flex items-center justify-between mb-4">
						<div
							class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
							<i class="fas fa-user-cog text-orange-600 text-xl"></i>
						</div>
						<i
							class="fas fa-arrow-right text-gray-400 group-hover:text-orange-500 transition-colors duration-200"></i>
					</div>
					<h3 class="text-lg font-semibold text-gray-900 mb-2">User
						Management</h3>
					<p class="text-gray-600 text-sm">Manage system users and
						permissions</p>
				</div>
			</a>
		</c:if>

		<a href="dashboard?action=help" class="group">
			<div
				class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-lg transition-all duration-200 group-hover:border-orange-300">
				<div class="flex items-center justify-between mb-4">
					<div
						class="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center">
						<i class="fas fa-question-circle text-gray-600 text-xl"></i>
					</div>
					<i
						class="fas fa-arrow-right text-gray-400 group-hover:text-orange-500 transition-colors duration-200"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900 mb-2">Help &
					Support</h3>
				<p class="text-gray-600 text-sm">System usage guidelines and
					support</p>
			</div>
		</a>
	</div>
</div>