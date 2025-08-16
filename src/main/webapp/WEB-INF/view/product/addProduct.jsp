<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../shared/layout.jsp"%>
<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
	<!-- Page Header -->
	<div class="flex items-center justify-between mb-6">
		<div>
			<h1 class="text-3xl font-bold text-gray-900">Add New Book</h1>
			<p class="text-gray-600 mt-1">Create a new book for your
				inventory</p>
		</div>
		<a href="product?action=list"
			class="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
			<i class="fas fa-list mr-2"></i>Back to Products
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

	<!-- Form Card -->
	<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
		<form action="product?action=add" method="post" class="space-y-6">
			<div>
				<label for="name"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-book mr-2"></i>Book Title
				</label> <input type="text" id="name" name="name" required
					placeholder="Enter book title"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>

			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<div>
					<label for="isbn"
						class="block text-sm font-medium text-gray-700 mb-2"> <i
						class="fas fa-barcode mr-2"></i>ISBN
					</label> <input type="text" id="isbn" name="isbn"
						placeholder="e.g., 978-0134685991"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
				</div>

				<div>
					<label for="price"
						class="block text-sm font-medium text-gray-700 mb-2"> <i
						class="fas fa-tag mr-2"></i>Price (Rs.)
					</label> <input type="number" step="0.01" id="price" name="price" required
						placeholder="0.00" min="0"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
				</div>
			</div>

			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<div>
					<label for="author"
						class="block text-sm font-medium text-gray-700 mb-2"> <i
						class="fas fa-user mr-2"></i>Author
					</label> <input type="text" id="author" name="author"
						placeholder="Enter author name"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
				</div>

				<div>
					<label for="publisher"
						class="block text-sm font-medium text-gray-700 mb-2"> <i
						class="fas fa-building mr-2"></i>Publisher
					</label> <input type="text" id="publisher" name="publisher"
						placeholder="Enter publisher name"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
				</div>
			</div>

			<div>
				<label for="publicationDate"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-calendar mr-2"></i>Publication Date
				</label> <input type="date" id="publicationDate" name="publicationDate"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200">
			</div>

			<div>
				<label for="description"
					class="block text-sm font-medium text-gray-700 mb-2"> <i
					class="fas fa-align-left mr-2"></i>Description
				</label>
				<textarea id="description" name="description"
					placeholder="Enter book description (optional)"
					class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-colors duration-200 min-h-[120px] resize-none"></textarea>
			</div>

			<div class="flex items-center gap-4 pt-4">
				<button type="submit"
					class="bg-orange-600 text-white px-6 py-3 rounded-lg hover:bg-orange-700 transition-colors duration-200 flex items-center">
					<i class="fas fa-save mr-2"></i>Add Book
				</button>
				<a href="product?action=list"
					class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
					<i class="fas fa-times mr-2"></i>Cancel
				</a>
			</div>
		</form>
	</div>
</div>