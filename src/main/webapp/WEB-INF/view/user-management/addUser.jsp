<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Add New User</h1>
			<p class="text-gray-600 mt-1">Create a new user account for the
				system</p>
		</div>
		<a href="user-management?action=list"
			class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
			<i class="fas fa-list mr-2"></i>Back to Users
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

	<!-- User Form Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<form action="user-management?action=add" method="post"
			class="space-y-5">
			<!-- Username Field -->
			<div>
				<label for="username"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-user mr-2 text-orange-600"></i>Username
				</label> <input type="text" id="username" name="username" required
					minlength="3" maxlength="50" placeholder="Enter username"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>

			<!-- Password Field -->
			<div>
				<label for="password"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-lock mr-2 text-orange-600"></i>Password
				</label> <input type="password" id="password" name="password" required
					minlength="6" placeholder="Enter password"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
				<div class="mt-2 text-xs text-gray-500 flex items-center">
					<i class="fas fa-info-circle mr-1"></i> Minimum 6 characters,
					include letters and numbers.
				</div>
			</div>

			<!-- Confirm Password Field -->
			<div>
				<label for="confirmPassword"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-lock mr-2 text-orange-600"></i>Confirm Password
				</label> <input type="password" id="confirmPassword" name="confirmPassword"
					required placeholder="Confirm password"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>

			<!-- Role Field -->
			<div>
				<label for="role"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-user-tag mr-2 text-orange-600"></i>Role
				</label> <select id="role" name="role" required
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
					<option value="">Select Role</option>
					<option value="user">User</option>
					<option value="manager">Manager</option>
					<option value="admin">Admin</option>
				</select>
			</div>

			<!-- Action Buttons -->
			<div class="flex items-center gap-3 pt-4">
				<button type="submit"
					class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
					<i class="fas fa-save mr-2"></i>Add User
				</button>
				<a href="user-management?action=list"
					class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
					<i class="fas fa-times mr-2"></i>Cancel
				</a>
			</div>
		</form>
	</div>
</div>

<script>
	// Password validation
	const password = document.getElementById('password');
	const confirmPassword = document.getElementById('confirmPassword');

	function validate() {
		if (password.value !== confirmPassword.value) {
			confirmPassword.setCustomValidity('Passwords do not match');
		} else {
			confirmPassword.setCustomValidity('');
		}
	}

	password.addEventListener('input', validate);
	confirmPassword.addEventListener('input', validate);
</script>