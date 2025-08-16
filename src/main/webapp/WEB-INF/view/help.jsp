<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Welcome Section -->
	<div
		class="bg-gradient-to-r from-orange-600 to-orange-700 rounded-xl p-8 mb-8 text-white">
		<div class="flex items-center mb-4">
			<div
				class="w-12 h-12 bg-white bg-opacity-20 rounded-lg flex items-center justify-center mr-4">
				<i class="fas fa-question-circle text-white text-2xl"></i>
			</div>
			<div>
				<h1 class="text-3xl font-bold">Help & Support Center</h1>
				<p class="text-orange-100">Everything you need to know about
					using Pahana Edu Billing System</p>
			</div>
		</div>
	</div>

	<!-- Quick Navigation -->
	<div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
		<a href="#getting-started"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow duration-200 hover:border-orange-300">
			<div class="flex items-center mb-4">
				<div
					class="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center mr-3">
					<i class="fas fa-rocket text-orange-600"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900">Getting Started</h3>
			</div>
			<p class="text-gray-600">Learn the basics of using the system</p>
		</a> <a href="#customer-management"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow duration-200 hover:border-orange-300">
			<div class="flex items-center mb-4">
				<div
					class="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center mr-3">
					<i class="fas fa-users text-orange-600"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900">Customer
					Management</h3>
			</div>
			<p class="text-gray-600">Manage customer information and accounts</p>
		</a> <a href="#billing-system"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow duration-200 hover:border-orange-300">
			<div class="flex items-center mb-4">
				<div
					class="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center mr-3">
					<i class="fas fa-file-invoice-dollar text-orange-600"></i>
				</div>
				<h3 class="text-lg font-semibold text-gray-900">Billing System</h3>
			</div>
			<p class="text-gray-600">Create and manage customer bills</p>
		</a>
	</div>

	<!-- Help Content -->
	<div class="space-y-8">
		<!-- Getting Started Section -->
		<div id="getting-started"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-8">
			<div class="flex items-center mb-6">
				<div
					class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center mr-4">
					<i class="fas fa-rocket text-orange-600 text-xl"></i>
				</div>
				<h2 class="text-2xl font-bold text-gray-900">Getting Started</h2>
			</div>

			<div class="prose max-w-none">
				<p class="text-gray-600 mb-4">Welcome to Pahana Edu Billing
					System! This comprehensive system helps you manage customers,
					products, and billing information efficiently. Here's how to get
					started:</p>

				<div class="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
					<div class="bg-orange-50 rounded-lg p-6 border border-orange-200">
						<h3 class="text-lg font-semibold text-gray-900 mb-3">1. Add
							Customers</h3>
						<p class="text-gray-600 mb-3">Start by adding your customers
							to the system:</p>
						<ul class="list-disc list-inside text-gray-600 space-y-1">
							<li>Go to Customer Management</li>
							<li>Click "Add Customer"</li>
							<li>Fill in customer details</li>
							<li>Save the customer information</li>
						</ul>
					</div>

					<div class="bg-orange-50 rounded-lg p-6 border border-orange-200">
						<h3 class="text-lg font-semibold text-gray-900 mb-3">2. Add
							Products</h3>
						<p class="text-gray-600 mb-3">Add your products to the
							inventory:</p>
						<ul class="list-disc list-inside text-gray-600 space-y-1">
							<li>Go to Product Management</li>
							<li>Click "Add Product"</li>
							<li>Enter product details and price</li>
							<li>Set stock quantity</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!-- Customer Management Section -->
		<div id="customer-management"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-8">
			<div class="flex items-center mb-6">
				<div
					class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center mr-4">
					<i class="fas fa-users text-orange-600 text-xl"></i>
				</div>
				<h2 class="text-2xl font-bold text-gray-900">Customer
					Management</h2>
			</div>

			<div class="prose max-w-none">
				<p class="text-gray-600 mb-4">Manage your customer database
					efficiently with our comprehensive customer management features.</p>

				<div class="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
					<div>
						<h3 class="text-lg font-semibold text-gray-900 mb-3">Adding
							Customers</h3>
						<ul class="list-disc list-inside text-gray-600 space-y-2">
							<li>Navigate to Customer Management</li>
							<li>Click the "Add Customer" button</li>
							<li>Fill in all required fields</li>
							<li>Include contact information</li>
							<li>Add billing address</li>
						</ul>
					</div>

					<div>
						<h3 class="text-lg font-semibold text-gray-900 mb-3">Managing
							Customers</h3>
						<ul class="list-disc list-inside text-gray-600 space-y-2">
							<li>View all customers in the list</li>
							<li>Search for specific customers</li>
							<li>Edit customer information</li>
							<li>View customer billing history</li>
							<li>Delete customers if needed</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!-- Billing System Section -->
		<div id="billing-system"
			class="bg-white rounded-lg shadow-sm border border-gray-200 p-8">
			<div class="flex items-center mb-6">
				<div
					class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center mr-4">
					<i class="fas fa-file-invoice-dollar text-orange-600 text-xl"></i>
				</div>
				<h2 class="text-2xl font-bold text-gray-900">Billing System</h2>
			</div>

			<div class="prose max-w-none">
				<p class="text-gray-600 mb-4">Create and manage customer bills
					with our intuitive billing system.</p>

				<div class="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
					<div>
						<h3 class="text-lg font-semibold text-gray-900 mb-3">Creating
							Bills</h3>
						<ul class="list-disc list-inside text-gray-600 space-y-2">
							<li>Go to Bill Management</li>
							<li>Click "Create Bill"</li>
							<li>Select a customer</li>
							<li>Add products to the bill</li>
							<li>Set quantities and prices</li>
							<li>Generate the final bill</li>
						</ul>
					</div>

					<div>
						<h3 class="text-lg font-semibold text-gray-900 mb-3">Managing
							Bills</h3>
						<ul class="list-disc list-inside text-gray-600 space-y-2">
							<li>View all bills in the system</li>
							<li>Search and filter bills</li>
							<li>Print bills for customers</li>
							<li>View bill details</li>
							<li>Delete bills if necessary</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!-- Support Contact -->
		<div
			class="bg-gradient-to-r from-orange-50 to-orange-100 rounded-lg p-8 border border-orange-200">
			<div class="text-center">
				<div
					class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-4">
					<i class="fas fa-headset text-orange-600 text-2xl"></i>
				</div>
				<h2 class="text-2xl font-bold text-gray-900 mb-4">Need More
					Help?</h2>
				<p class="text-gray-600 mb-6">If you need additional support or
					have questions not covered here, please contact our support team.</p>
				<div class="flex flex-col sm:flex-row gap-4 justify-center">
					<div class="flex items-center justify-center">
						<i class="fas fa-envelope text-orange-600 mr-2"></i> <span
							class="text-gray-700">support@pahanaedu.com</span>
					</div>
					<div class="flex items-center justify-center">
						<i class="fas fa-phone text-orange-600 mr-2"></i> <span
							class="text-gray-700">+94 11 234 5678</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>