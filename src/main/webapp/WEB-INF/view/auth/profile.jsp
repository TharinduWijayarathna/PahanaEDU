<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Profile - Pahana Edu Billing System</title>
<script src="https://cdn.tailwindcss.com"></script>
<script>
	tailwind.config = {
		theme : {
			extend : {
				colors : {
					primary : {
						50 : '#fff7ed',
						100 : '#ffedd5',
						200 : '#fed7aa',
						300 : '#fdba74',
						400 : '#fb923c',
						500 : '#f97316',
						600 : '#ea580c',
						700 : '#c2410c',
						800 : '#9a3412',
						900 : '#7c2d12',
					}
				}
			}
		}
	}
</script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
</head>
<body
	class="bg-gradient-to-br from-orange-600 via-orange-700 to-orange-800 min-h-screen flex items-center justify-center p-4">
	<div class="bg-white rounded-2xl shadow-2xl p-8 max-w-xl w-full mx-4">
		<div class="text-center mb-8">
			<div
				class="w-20 h-20 bg-gradient-to-r from-orange-500 to-orange-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
				<i class="fas fa-user text-white text-3xl"></i>
			</div>
			<h1 class="text-3xl font-bold text-gray-800 mb-2">User Profile</h1>
			<p class="text-gray-600">Account information</p>
		</div>

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

		<c:if test="${not empty user}">
			<div class="space-y-4">
				<div>
					<label class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-user mr-2 text-orange-600"></i>Username
					</label>
					<div
						class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg">${user.username}</div>
				</div>
				<div>
					<label class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-user-tag mr-2 text-orange-600"></i>Role
					</label>
					<div
						class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg capitalize">${user.role}</div>
				</div>
				<div>
					<label class="block text-sm font-medium text-gray-700 mb-2">
						<i class="fas fa-clock mr-2 text-orange-600"></i>Login Time
					</label>
					<div
						class="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-lg">${sessionScope.loginTime}</div>
				</div>
			</div>
		</c:if>

		<div class="flex items-center justify-center gap-3 mt-8">
			<a href="dashboard"
				class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
				<i class="fas fa-home mr-2"></i>Dashboard
			</a> <a href="auth?action=logout"
				class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200 flex items-center">
				<i class="fas fa-sign-out-alt mr-2"></i>Logout
			</a>
		</div>

		<div class="mt-6 text-center">
			<a href="dashboard?action=help"
				class="text-gray-500 hover:text-gray-700 text-sm flex items-center justify-center">
				<i class="fas fa-question-circle mr-2"></i>Help
			</a>
		</div>
	</div>
</body>
</html>
