<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pahana Edu - Billing System</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            50: '#fff7ed',
                            100: '#ffedd5',
                            200: '#fed7aa',
                            300: '#fdba74',
                            400: '#fb923c',
                            500: '#f97316',
                            600: '#ea580c',
                            700: '#c2410c',
                            800: '#9a3412',
                            900: '#7c2d12',
                        }
                    }
                }
            }
        }
    </script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-gradient-to-br from-orange-600 via-orange-700 to-orange-800 min-h-screen flex items-center justify-center p-4">
    <div class="bg-white rounded-2xl shadow-2xl p-8 max-w-md w-full mx-4 transform transition-all duration-300 hover:scale-105">
        <div class="text-center">
            <!-- Logo and Icon -->
            <div class="mb-8">
                <div class="w-20 h-20 bg-gradient-to-r from-orange-500 to-orange-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
                    <i class="fas fa-graduation-cap text-white text-3xl"></i>
                </div>
                <h1 class="text-3xl font-bold text-gray-800 mb-2">Pahana Edu</h1>
                <p class="text-gray-600 text-lg">Billing System</p>
            </div>
            
            <!-- Description -->
            <p class="text-gray-600 mb-8 leading-relaxed">
                Welcome to the comprehensive billing management system for Pahana Edu Bookshop. 
                Manage customers, products, and billing information efficiently with our modern interface.
            </p>
            
            <!-- Action Buttons -->
            <div class="space-y-4">
                <a href="auth?action=login" 
                   class="w-full bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold py-3 px-6 rounded-lg shadow-lg hover:shadow-xl transform hover:-translate-y-1 transition-all duration-200 flex items-center justify-center">
                    <i class="fas fa-sign-in-alt mr-2"></i>
                    Login to System
                </a>
                
                <a href="dashboard?action=help" 
                   class="w-full bg-gray-100 text-gray-700 font-semibold py-3 px-6 rounded-lg hover:bg-gray-200 transition-all duration-200 flex items-center justify-center">
                    <i class="fas fa-question-circle mr-2"></i>
                    View Help
                </a>
            </div>
            
            <!-- Loading Animation -->
            <div id="loading" class="hidden mt-8">
                <div class="flex items-center justify-center space-x-2">
                    <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-600"></div>
                    <p class="text-gray-600">Redirecting to login...</p>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Auto-redirect to login after 2 seconds
        setTimeout(function() {
            document.getElementById('loading').classList.remove('hidden');
            setTimeout(function() {
                window.location.href = 'auth?action=login';
            }, 1000);
        }, 2000);
    </script>
</body>
</html>
