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

	<!-- Revenue Analytics Section -->
	<div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
		<!-- Monthly Revenue Chart -->
		<div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-900">Revenue Analytics</h3>
				<div class="flex space-x-2">
					<button class="px-3 py-1 text-xs bg-blue-100 text-blue-700 rounded-full">${revenueData.currentMonthName}</button>
					<button class="px-3 py-1 text-xs bg-gray-100 text-gray-600 rounded-full">${revenueData.lastMonthName}</button>
				</div>
			</div>
			<div class="grid grid-cols-2 gap-4 mb-6">
				<div class="bg-green-50 rounded-lg p-4">
					<div class="flex items-center justify-between">
						<div>
							<p class="text-sm text-gray-600">Current Month</p>
							<p class="text-2xl font-bold text-green-600">Rs. ${revenueData.currentMonthRevenue}</p>
						</div>
						<i class="fas fa-chart-line text-green-500 text-xl"></i>
					</div>
				</div>
				<div class="bg-blue-50 rounded-lg p-4">
					<div class="flex items-center justify-between">
						<div>
							<p class="text-sm text-gray-600">Last Month</p>
							<p class="text-2xl font-bold text-blue-600">Rs. ${revenueData.lastMonthRevenue}</p>
						</div>
						<i class="fas fa-chart-bar text-blue-500 text-xl"></i>
					</div>
				</div>
			</div>
			<div class="bg-gray-50 rounded-lg p-4">
				<div class="flex items-center justify-between">
					<div>
						<p class="text-sm text-gray-600">Growth Rate</p>
						<c:choose>
							<c:when test="${revenueData.percentageChange >= 0}">
								<p class="text-lg font-bold text-green-600">+${revenueData.percentageChange}%</p>
							</c:when>
							<c:otherwise>
								<p class="text-lg font-bold text-red-600">${revenueData.percentageChange}%</p>
							</c:otherwise>
						</c:choose>
					</div>
					<c:choose>
						<c:when test="${revenueData.percentageChange >= 0}">
							<i class="fas fa-arrow-up text-green-500 text-xl"></i>
						</c:when>
						<c:otherwise>
							<i class="fas fa-arrow-down text-red-500 text-xl"></i>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>

		<!-- Quick Stats Summary -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<h3 class="text-lg font-semibold text-gray-900 mb-6">Quick Stats</h3>
			<div class="space-y-4">
				<div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
					<div>
						<p class="text-sm text-gray-600">This Month Revenue</p>
						<p class="text-lg font-bold text-green-600">Rs. ${quickStats.thisMonthRevenue}</p>
					</div>
					<i class="fas fa-dollar-sign text-green-500"></i>
				</div>
				<div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
					<div>
						<p class="text-sm text-gray-600">Bills Generated</p>
						<p class="text-lg font-bold text-blue-600">${quickStats.billsGenerated}</p>
					</div>
					<i class="fas fa-file-invoice text-blue-500"></i>
				</div>
				<div class="flex items-center justify-between p-3 bg-purple-50 rounded-lg">
					<div>
						<p class="text-sm text-gray-600">New Customers</p>
						<p class="text-lg font-bold text-purple-600">${quickStats.newCustomers}</p>
					</div>
					<i class="fas fa-user-plus text-purple-500"></i>
				</div>
				<div class="flex items-center justify-between p-3 bg-orange-50 rounded-lg">
					<div>
						<p class="text-sm text-gray-600">Avg. Bill Value</p>
						<p class="text-lg font-bold text-orange-600">Rs. ${quickStats.avgBillValue}</p>
					</div>
					<i class="fas fa-calculator text-orange-500"></i>
				</div>
			</div>
		</div>
	</div>

	<!-- Recent Activity and Recent Bills -->
	<div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
		<!-- Recent Activity Timeline -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-900">Recent Activity</h3>
				<a href="activity?action=list" class="text-sm text-orange-600 hover:text-orange-700">View All</a>
			</div>
			<div class="space-y-4">
				<c:choose>
					<c:when test="${not empty recentActivities}">
						<c:forEach var="activity" items="${recentActivities}">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-${activity.color}-500 rounded-full mt-2"></div>
								<div class="flex-1">
									<p class="text-sm font-medium text-gray-900">${activity.description}</p>
									<c:if test="${not empty activity.entityName}">
										<p class="text-xs text-gray-500">${activity.entityName}</p>
									</c:if>
									<p class="text-xs text-gray-400">${activity.timeAgo}</p>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="text-center py-8">
							<i class="fas fa-info-circle text-gray-400 text-2xl mb-2"></i>
							<p class="text-sm text-gray-500">No recent activities</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<!-- Recent Bills Table -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-900">Recent Bills</h3>
				<a href="bill?action=list" class="text-sm text-orange-600 hover:text-orange-700">View All</a>
			</div>
			<div class="overflow-x-auto">
				<table class="min-w-full">
					<thead>
						<tr class="border-b border-gray-200">
							<th class="text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bill ID</th>
							<th class="text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Customer</th>
							<th class="text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Amount</th>
							<th class="text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
						</tr>
					</thead>
					<tbody class="divide-y divide-gray-200">
						<c:forEach var="bill" items="${recentBills}">
							<tr>
								<td class="py-2 text-sm text-gray-900">${bill.billId}</td>
								<td class="py-2 text-sm text-gray-600">${bill.customerName}</td>
								<td class="py-2 text-sm font-medium text-green-600">Rs. ${bill.amount}</td>
								<td class="py-2">
									<c:choose>
										<c:when test="${bill.status == 'paid'}">
											<span class="px-2 py-1 text-xs bg-green-100 text-green-800 rounded-full">Paid</span>
										</c:when>
										<c:when test="${bill.status == 'pending'}">
											<span class="px-2 py-1 text-xs bg-yellow-100 text-yellow-800 rounded-full">Pending</span>
										</c:when>
										<c:otherwise>
											<span class="px-2 py-1 text-xs bg-gray-100 text-gray-800 rounded-full">${bill.status}</span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Top Customers and System Status -->
	<div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
		<!-- Top Customers -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-900">Top Customers</h3>
				<a href="customer?action=list" class="text-sm text-orange-600 hover:text-orange-700">View All</a>
			</div>
			<div class="space-y-4">
				<c:forEach var="customer" items="${topCustomers}" varStatus="status">
					<div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
						<div class="flex items-center space-x-3">
							<c:choose>
								<c:when test="${status.index == 0}">
									<div class="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center">
										<span class="text-sm font-medium text-blue-600">${customer.initials}</span>
									</div>
								</c:when>
								<c:when test="${status.index == 1}">
									<div class="w-8 h-8 bg-green-100 rounded-full flex items-center justify-center">
										<span class="text-sm font-medium text-green-600">${customer.initials}</span>
									</div>
								</c:when>
								<c:otherwise>
									<div class="w-8 h-8 bg-purple-100 rounded-full flex items-center justify-center">
										<span class="text-sm font-medium text-purple-600">${customer.initials}</span>
									</div>
								</c:otherwise>
							</c:choose>
							<div>
								<p class="text-sm font-medium text-gray-900">${customer.name}</p>
								<p class="text-xs text-gray-500">${customer.billCount} bills • Rs. ${customer.totalRevenue} total</p>
							</div>
						</div>
						<span class="text-sm font-medium text-green-600">Rs. ${customer.totalRevenue}</span>
					</div>
				</c:forEach>
			</div>
		</div>

		<!-- System Status -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-900">System Status</h3>
				<span class="px-2 py-1 text-xs bg-green-100 text-green-800 rounded-full">${systemStatus.overallStatus}</span>
			</div>
			<div class="space-y-4">
				<div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
					<div class="flex items-center space-x-3">
						<div class="w-3 h-3 bg-green-500 rounded-full"></div>
						<span class="text-sm text-gray-900">Database Connection</span>
					</div>
					<span class="text-xs text-green-600">${systemStatus.databaseConnection}</span>
				</div>
				<div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
					<div class="flex items-center space-x-3">
						<div class="w-3 h-3 bg-green-500 rounded-full"></div>
						<span class="text-sm text-gray-900">Web Server</span>
					</div>
					<span class="text-xs text-green-600">${systemStatus.webServer}</span>
				</div>
				<div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
					<div class="flex items-center space-x-3">
						<div class="w-3 h-3 bg-green-500 rounded-full"></div>
						<span class="text-sm text-gray-900">File System</span>
					</div>
					<span class="text-xs text-green-600">${systemStatus.fileSystem}</span>
				</div>
				<div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
					<div class="flex items-center space-x-3">
						<div class="w-3 h-3 bg-green-500 rounded-full"></div>
						<span class="text-sm text-gray-900">Backup System</span>
					</div>
					<span class="text-xs text-green-600">${systemStatus.backupSystem}</span>
				</div>
			</div>
		</div>
	</div>

	<!-- Quick Actions Footer -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<h3 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h3>
		<div class="grid grid-cols-2 md:grid-cols-4 gap-4">
			<a href="bill?action=create" class="flex flex-col items-center p-4 bg-orange-50 rounded-lg hover:bg-orange-100 transition-colors duration-200">
				<i class="fas fa-plus-circle text-orange-600 text-xl mb-2"></i>
				<span class="text-sm font-medium text-orange-700">Create Bill</span>
			</a>
			<a href="customer?action=add" class="flex flex-col items-center p-4 bg-blue-50 rounded-lg hover:bg-blue-100 transition-colors duration-200">
				<i class="fas fa-user-plus text-blue-600 text-xl mb-2"></i>
				<span class="text-sm font-medium text-blue-700">Add Customer</span>
			</a>
			<a href="product?action=add" class="flex flex-col items-center p-4 bg-green-50 rounded-lg hover:bg-green-100 transition-colors duration-200">
				<i class="fas fa-plus text-green-600 text-xl mb-2"></i>
				<span class="text-sm font-medium text-green-700">Add Product</span>
			</a>
			<a href="dashboard?action=reports" class="flex flex-col items-center p-4 bg-purple-50 rounded-lg hover:bg-purple-100 transition-colors duration-200">
				<i class="fas fa-chart-bar text-purple-600 text-xl mb-2"></i>
				<span class="text-sm font-medium text-purple-700">View Reports</span>
			</a>
		</div>
	</div>
</div>