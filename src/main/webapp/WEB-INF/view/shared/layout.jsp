<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${pageTitle}- Pahana Edu</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script>
	tailwind.config = {
		theme : {
			extend : {
				colors : {
					orange : {
						50 : '#fff7ed',
						100 : '#ffedd5',
						500 : '#f97316',
						600 : '#ea580c',
					}
				}
			}
		}
	}
</script>
</head>
<body class="bg-gray-50 min-h-screen">
	<!-- Shared Layout Component -->
	<script>
		function toggleMobileMenu() {
			const mobileMenu = document.getElementById('mobile-menu');
			mobileMenu.classList.toggle('hidden');
		}
	</script>

	<!-- Top Navigation Bar -->
	<nav class="bg-white shadow-lg border-b border-gray-200">
		<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
			<div class="flex justify-between h-16">
				<!-- Logo and Brand -->
				<div class="flex items-center">
					<div class="flex-shrink-0 flex items-center">
						<div
							class="w-10 h-10 bg-gradient-to-r from-orange-500 to-orange-600 rounded-lg flex items-center justify-center mr-3">
							<i class="fas fa-graduation-cap text-white text-lg"></i>
						</div>
						<span class="text-xl font-bold text-gray-900">Pahana Edu</span>
					</div>
				</div>

				<!-- Navigation Links -->
				<div class="hidden md:flex items-center space-x-8">
					<a href="dashboard"
						class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'dashboard') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
						<i class="fas fa-tachometer-alt mr-2"></i>Dashboard
					</a> <a href="customer?action=list"
						class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'customer') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
						<i class="fas fa-users mr-2"></i>Customers
					</a> <a href="product?action=list"
						class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'product') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
						<i class="fas fa-book mr-2"></i>Products
					</a> <a href="bill?action=list"
						class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'bill') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
						<i class="fas fa-file-invoice-dollar mr-2"></i>Bills
					</a>
					<c:if test="${sessionScope.role == 'admin'}">
						<a href="user-management?action=list"
							class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'user-management') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
							<i class="fas fa-user-cog mr-2"></i>Users
						</a>
					</c:if>
					<a href="dashboard?action=help"
						class="text-gray-600 hover:text-orange-600 px-3 py-2 text-sm font-medium transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'help') ? 'text-orange-600 border-b-2 border-orange-600' : ''}">
						<i class="fas fa-question-circle mr-2"></i>Help
					</a>
				</div>

				<!-- User Menu -->
				<div class="flex items-center space-x-4">
					<div class="flex items-center space-x-3">
						<div
							class="w-8 h-8 bg-orange-100 rounded-full flex items-center justify-center">
							<i class="fas fa-user text-orange-600 text-sm"></i>
						</div>
						<div class="hidden md:block">
							<p class="text-sm font-medium text-gray-900">${sessionScope.username}</p>
							<p class="text-xs text-gray-500 capitalize">${sessionScope.role}</p>
						</div>
						<a href="dashboard?action=logout"
							class="text-gray-400 hover:text-red-500 transition-colors duration-200">
							<i class="fas fa-sign-out-alt"></i>
						</a>
					</div>
				</div>

				<!-- Mobile menu button -->
				<div class="md:hidden flex items-center">
					<button onclick="toggleMobileMenu()"
						class="text-gray-500 hover:text-gray-700">
						<i class="fas fa-bars text-xl"></i>
					</button>
				</div>
			</div>
		</div>

		<!-- Mobile menu -->
		<div id="mobile-menu"
			class="hidden md:hidden bg-white border-t border-gray-200">
			<div class="px-2 pt-2 pb-3 space-y-1">
				<a href="dashboard"
					class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'dashboard') ? 'text-orange-600 bg-orange-50' : ''}">
					<i class="fas fa-tachometer-alt mr-2"></i>Dashboard
				</a> <a href="customer?action=list"
					class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'customer') ? 'text-orange-600 bg-orange-50' : ''}">
					<i class="fas fa-users mr-2"></i>Customers
				</a> <a href="product?action=list"
					class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'product') ? 'text-orange-600 bg-orange-50' : ''}">
					<i class="fas fa-book mr-2"></i>Products
				</a> <a href="bill?action=list"
					class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'bill') ? 'text-orange-600 bg-orange-50' : ''}">
					<i class="fas fa-file-invoice-dollar mr-2"></i>Bills
				</a>
				<c:if test="${sessionScope.role == 'admin'}">
					<a href="user-management?action=list"
						class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'user-management') ? 'text-orange-600 bg-orange-50' : ''}">
						<i class="fas fa-user-cog mr-2"></i>Users
					</a>
				</c:if>
				<a href="dashboard?action=help"
					class="text-gray-600 hover:text-orange-600 hover:bg-orange-50 block px-3 py-2 text-base font-medium rounded-md transition-colors duration-200 ${fn:contains(pageContext.request.requestURI, 'help') ? 'text-orange-600 bg-orange-50' : ''}">
					<i class="fas fa-question-circle mr-2"></i>Help
				</a>
			</div>
		</div>
	</nav>

	<!-- Main Content Wrapper -->
	<!-- <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
       
        <div class="mb-6">
            <h1 class="text-3xl font-bold text-gray-900">${pageTitle}</h1>
            <c:if test="${not empty pageSubtitle}">
                <p class="text-gray-600 mt-2">${pageSubtitle}</p>
            </c:if>
        </div>

  
        <c:if test="${not empty success}">
            <div class="mb-6 bg-green-50 border border-green-200 rounded-lg p-4">
                <div class="flex items-center">
                    <i class="fas fa-check-circle text-green-500 mr-2"></i>
                    <span class="text-green-800">${success}</span>
                </div>
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
                <div class="flex items-center">
                    <i class="fas fa-exclamation-circle text-red-500 mr-2"></i>
                    <span class="text-red-800">${error}</span>
                </div>
            </div>
        </c:if>
        

    </div> -->
</body>
</html>
