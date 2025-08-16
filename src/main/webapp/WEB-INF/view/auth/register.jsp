<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - Pahana Edu Billing System</title>
<script src="https://cdn.tailwindcss.com"></script>
<script>
	tailwind.config = {
		theme : {
			extend : {
				colors : {
					primary : {
						50 : '#eff6ff',
						100 : '#dbeafe',
						200 : '#bfdbfe',
						300 : '#93c5fd',
						400 : '#60a5fa',
						500 : '#3b82f6',
						600 : '#2563eb',
						700 : '#1d4ed8',
						800 : '#1e40af',
						900 : '#1e3a8a'
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
	class="bg-gradient-to-br from-primary-600 via-primary-700 to-primary-800 min-h-screen flex items-center justify-center p-4">
	<div class="bg-white rounded-2xl shadow-2xl p-8 max-w-xl w-full mx-4">
		<div class="text-center mb-8">
			<div
				class="w-20 h-20 bg-gradient-to-r from-primary-500 to-primary-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
				<i class="fas fa-user-plus text-white text-3xl"></i>
			</div>
			<h1 class="text-3xl font-bold text-gray-800 mb-2">Create Account</h1>
			<p class="text-gray-600">Sign up to start using the system</p>
		</div>

		<c:if test="${not empty success}">
			<div class="mb-6 bg-green-50 border border-green-200 rounded-lg p-4">
				<div class="flex items-center">
					<i class="fas fa-check-circle text-green-500 mr-2"></i><span
						class="text-green-800">${success}</span>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
				<div class="flex items-center">
					<i class="fas fa-exclamation-circle text-red-500 mr-2"></i><span
						class="text-red-800">${error}</span>
				</div>
			</div>
		</c:if>

		<form action="auth" method="post" class="space-y-6">
			<input type="hidden" name="action" value="register">

			<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
				<div>
					<label for="firstName"
						class="block text-sm font-medium text-gray-700 mb-2"><i
						class="fas fa-id-card mr-2"></i>First Name</label> <input type="text"
						id="firstName" name="firstName" required
						placeholder="Enter first name" value="${param.firstName}"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
				</div>
				<div>
					<label for="lastName"
						class="block text-sm font-medium text-gray-700 mb-2"><i
						class="fas fa-id-card mr-2"></i>Last Name</label> <input type="text"
						id="lastName" name="lastName" required
						placeholder="Enter last name" value="${param.lastName}"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
				</div>
			</div>

			<div>
				<label for="username"
					class="block text-sm font-medium text-gray-700 mb-2"><i
					class="fas fa-user mr-2"></i>Username</label> <input type="text"
					id="username" name="username" required
					placeholder="Choose a username" value="${param.username}"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
			</div>

			<div>
				<label for="email"
					class="block text-sm font-medium text-gray-700 mb-2"><i
					class="fas fa-envelope mr-2"></i>Email</label> <input type="email"
					id="email" name="email" required placeholder="Enter your email"
					value="${param.email}"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
			</div>

			<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
				<div>
					<label for="password"
						class="block text-sm font-medium text-gray-700 mb-2"><i
						class="fas fa-lock mr-2"></i>Password</label> <input type="password"
						id="password" name="password" required
						placeholder="Choose a password"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
				</div>
				<div>
					<label for="confirmPassword"
						class="block text-sm font-medium text-gray-700 mb-2"><i
						class="fas fa-lock mr-2"></i>Confirm Password</label> <input
						type="password" id="confirmPassword" name="confirmPassword"
						required placeholder="Confirm your password"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
				</div>
			</div>

			<button type="submit"
				class="w-full bg-gradient-to-r from-primary-500 to-primary-600 text-white font-semibold py-3 px-6 rounded-lg shadow-lg hover:shadow-xl transform hover:-translate-y-1 transition-all duration-200 flex items-center justify-center">
				<i class="fas fa-user-plus mr-2"></i>Create Account
			</button>
		</form>

		<div class="mt-8 text-center">
			<p class="text-gray-600 text-sm">
				Already have an account? <a href="auth?action=login"
					class="text-primary-600 hover:text-primary-700 font-medium">Login
					here</a>
			</p>
		</div>

		<div class="mt-6 text-center">
			<a href="index.jsp"
				class="text-gray-500 hover:text-gray-700 text-sm flex items-center justify-center"><i
				class="fas fa-arrow-left mr-2"></i>Back to Home</a>
		</div>
	</div>
</body>
</html>
