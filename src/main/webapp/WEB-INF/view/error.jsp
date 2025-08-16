<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error - Pahana Edu</title>
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
<body class="bg-gray-50 min-h-screen flex items-center justify-center">
	<div class="max-w-md mx-auto text-center">
		<!-- Error Icon -->
		<div
			class="w-24 h-24 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-6">
			<i class="fas fa-exclamation-triangle text-red-600 text-3xl"></i>
		</div>

		<!-- Error Title -->
		<h1 class="text-3xl font-bold text-gray-900 mb-4">Oops! Something
			went wrong</h1>

		<!-- Error Message -->
		<div class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
			<p class="text-red-800 font-medium">
				<c:choose>
					<c:when test="${not empty errorMessage}">
                        ${errorMessage}
                    </c:when>
					<c:otherwise>
                        An unexpected error occurred. Please try again later.
                    </c:otherwise>
				</c:choose>
			</p>
		</div>

		<!-- Action Buttons -->
		<div class="space-y-3">
			<a href="javascript:history.back()"
				class="inline-flex items-center justify-center w-full bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 font-medium">
				<i class="fas fa-arrow-left mr-2"></i> Go Back
			</a> <a href="auth?action=login"
				class="inline-flex items-center justify-center w-full bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 font-medium">
				<i class="fas fa-home mr-2"></i> Back to Login
			</a>
		</div>

		<!-- Support Info -->
		<div class="mt-8 text-sm text-gray-500">
			<p>If this problem persists, please contact support:</p>
			<div class="flex items-center justify-center mt-2 space-x-4">
				<span class="flex items-center"> <i
					class="fas fa-envelope mr-1"></i> support@pahanaedu.com
				</span> <span class="flex items-center"> <i
					class="fas fa-phone mr-1"></i> +94 11 234 5678
				</span>
			</div>
		</div>
	</div>
</body>
</html>
