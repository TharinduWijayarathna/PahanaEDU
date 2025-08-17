<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - Pahana Edu Billing System</title>
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
	<div class="bg-white rounded-2xl shadow-2xl p-8 max-w-md w-full mx-4">
		<!-- Logo and Header -->
		<div class="text-center mb-8">
			<div
				class="w-20 h-20 bg-gradient-to-r from-orange-500 to-orange-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
				<i class="fas fa-graduation-cap text-white text-3xl"></i>
			</div>
			<h1 class="text-3xl font-bold text-gray-800 mb-2">Welcome Back</h1>
			<p class="text-gray-600">Sign in to your account</p>
		</div>

		<!-- Alerts -->
		<c:if test="${not empty error}">
			<div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
				<div class="flex items-center">
					<i class="fas fa-exclamation-circle text-red-500 mr-2"></i> <span
						class="text-red-800">${error}</span>
				</div>
			</div>
		</c:if>

		<c:if test="${not empty success}">
			<div class="mb-6 bg-green-50 border border-green-200 rounded-lg p-4">
				<div class="flex items-center">
					<i class="fas fa-check-circle text-green-500 mr-2"></i> <span
						class="text-green-800">${success}</span>
				</div>
			</div>
		</c:if>
		
		<!-- Validation Error Messages -->
		<c:if test="${not empty validationErrors}">
			<div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
				<div class="flex items-start">
					<i class="fas fa-exclamation-triangle text-red-500 mr-2 mt-1"></i>
					<div class="flex-1">
						<h3 class="text-red-800 font-medium mb-2">Please correct the following errors:</h3>
						<ul class="list-disc list-inside text-red-700 space-y-1">
							<c:forEach var="error" items="${validationErrors}">
								<li>${error}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
		
		<!-- Field-specific validation errors -->
		<c:if test="${not empty fieldErrors}">
			<div class="mb-6 bg-yellow-50 border border-yellow-200 rounded-lg p-4">
				<div class="flex items-start">
					<i class="fas fa-exclamation-triangle text-yellow-500 mr-2 mt-1"></i>
					<div class="flex-1">
						<h3 class="text-yellow-800 font-medium mb-2">Please check the following fields:</h3>
						<ul class="list-disc list-inside text-yellow-700 space-y-1">
							<c:forEach var="fieldError" items="${fieldErrors}">
								<li><strong>${fieldError.key}:</strong> ${fieldError.value}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>

		<!-- Login Form -->
		<form action="auth" method="post" class="space-y-6">
			<input type="hidden" name="action" value="login">

			<div>
				<label for="username"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-user mr-2"></i>Username
				</label> <input type="text" id="username" name="username" required
					value="${username}"
					class="w-full px-4 py-3 border ${fieldErrors.username != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200"
					placeholder="Enter your username">
				<c:if test="${fieldErrors.username != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.username}</p>
				</c:if>
			</div>

			<div>
				<label for="password"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-lock mr-2"></i>Password
				</label> <input type="password" id="password" name="password" required
					class="w-full px-4 py-3 border ${fieldErrors.password != null ? 'border-red-300 focus:ring-red-500 focus:border-red-500' : 'border-gray-300 focus:ring-orange-500 focus:border-orange-500'} rounded-lg transition-colors duration-200"
					placeholder="Enter your password">
				<c:if test="${fieldErrors.password != null}">
					<p class="mt-1 text-sm text-red-600">${fieldErrors.password}</p>
				</c:if>
			</div>

			<button type="submit"
				class="w-full bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold py-3 px-6 rounded-lg shadow-lg hover:shadow-xl transform hover:-translate-y-1 transition-all duration-200 flex items-center justify-center">
				<i class="fas fa-sign-in-alt mr-2"></i> Sign In
			</button>
		</form>

		<!-- Back to Home -->
		<div class="mt-6 text-center">
			<a href="index.jsp"
				class="text-gray-500 hover:text-gray-700 text-sm flex items-center justify-center">
				<i class="fas fa-arrow-left mr-2"></i> Back to Home
			</a>
		</div>
	</div>
</body>
</html>
