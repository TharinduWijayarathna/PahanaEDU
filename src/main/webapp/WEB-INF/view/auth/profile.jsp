<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-4xl mx-auto">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">User Profile</h1>
			<p class="text-gray-600 mt-1">Update your account information</p>
		</div>
		<a href="dashboard"
			class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
			<i class="fas fa-home mr-2"></i>Back to Dashboard
		</a>
	</div>

	<!-- Profile Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
		<!-- Profile Header -->
		<div class="text-center mb-8">
			<div class="w-20 h-20 bg-gradient-to-r from-orange-500 to-orange-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
				<i class="fas fa-user text-white text-3xl"></i>
			</div>
			<h2 class="text-2xl font-bold text-gray-800 mb-2">Update Profile</h2>
			<p class="text-gray-600">Modify your account details</p>
		</div>

		<form action="auth?action=updateProfile" method="post" class="space-y-6">
			<input type="hidden" name="userId" value="${user.userId}">
			
			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<!-- Username -->
				<div>
					<label for="username" class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-user mr-2 text-orange-600"></i>Username
					</label>
					<input type="text" id="username" name="username" required
						value="${username != null ? username : user.username}"
						class="w-full px-4 py-3 border ${fieldErrors.username != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200">
					<c:if test="${fieldErrors.username != null}">
						<p class="mt-1 text-sm text-red-600">${fieldErrors.username}</p>
					</c:if>
				</div>
				
				<!-- Role (Read-only) -->
				<div>
					<label class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-user-tag mr-2 text-orange-600"></i>Role
					</label>
					<div class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg text-gray-900 capitalize">
						${user.role}
					</div>
				</div>
				
				<!-- User ID (Read-only) -->
				<div>
					<label class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-id-card mr-2 text-orange-600"></i>User ID
					</label>
					<div class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg text-gray-900">
						${user.userId}
					</div>
				</div>
				
				<!-- New Password (Optional) -->
				<div>
					<label for="newPassword" class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-lock mr-2 text-orange-600"></i>New Password (Optional)
					</label>
					<input type="password" id="newPassword" name="newPassword"
						placeholder="Leave empty to keep current password"
						class="w-full px-4 py-3 border ${fieldErrors.newPassword != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200">
					<c:if test="${fieldErrors.newPassword != null}">
						<p class="mt-1 text-sm text-red-600">${fieldErrors.newPassword}</p>
					</c:if>
				</div>
			</div>

			<!-- Action Buttons -->
			<div class="flex items-center justify-center gap-4 mt-8 pt-6 border-t border-gray-200">
				<button type="submit"
					class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
					<i class="fas fa-save mr-2"></i>Update Profile
				</button>
				<a href="dashboard"
					class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
					<i class="fas fa-home mr-2"></i>Dashboard
				</a>
			</div>
		</form>
	</div>
</div>
