<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pahana Edu - Bill #${bill.billId}</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background: white;
	color: #333;
	line-height: 1.6;
}

.print-header {
	text-align: center;
	margin-bottom: 30px;
	padding: 20px;
	border-bottom: 3px solid #ea580c;
	background: linear-gradient(135deg, #fff7ed 0%, #ffedd5 100%);
}

.company-name {
	font-size: 32px;
	font-weight: bold;
	color: #ea580c;
	margin-bottom: 8px;
	text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.company-tagline {
	font-size: 16px;
	color: #c2410c;
	margin-bottom: 12px;
	font-style: italic;
}

.company-info {
	font-size: 13px;
	color: #7c2d12;
	line-height: 1.8;
}

.bill-header {
	display: flex;
	justify-content: space-between;
	margin-bottom: 30px;
	padding: 25px;
	background: linear-gradient(135deg, #fff7ed 0%, #fed7aa 100%);
	border-radius: 8px;
	border: 1px solid #fdba74;
}

.customer-info, .bill-info {
	flex: 1;
}

.customer-info h3, .bill-info h3 {
	color: #ea580c;
	margin-bottom: 18px;
	font-size: 20px;
	border-bottom: 2px solid #fdba74;
	padding-bottom: 8px;
}

.customer-info p, .bill-info p {
	margin-bottom: 8px;
	font-size: 14px;
	color: #7c2d12;
}

.customer-info strong, .bill-info strong {
	color: #c2410c;
}

.items-table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 30px;
	border-radius: 8px;
	overflow: hidden;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.items-table th, .items-table td {
	padding: 15px;
	text-align: left;
	border-bottom: 1px solid #fed7aa;
}

.items-table th {
	background: linear-gradient(135deg, #ea580c 0%, #c2410c 100%);
	font-weight: 600;
	color: white;
	font-size: 14px;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

.items-table tr:nth-child(even) {
	background: #fff7ed;
}

.items-table tr:hover {
	background: #fed7aa;
}

.items-table td {
	color: #7c2d12;
	font-size: 14px;
}

.total-section {
	text-align: right;
	margin-top: 30px;
	padding: 25px;
	background: linear-gradient(135deg, #ea580c 0%, #c2410c 100%);
	border-radius: 8px;
	color: white;
}

.total-amount {
	font-size: 28px;
	font-weight: bold;
	color: white;
	text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
}

.footer {
	margin-top: 50px;
	padding: 25px;
	border-top: 2px solid #fdba74;
	text-align: center;
	font-size: 13px;
	color: #7c2d12;
	background: #fff7ed;
	border-radius: 8px;
}

.status {
	padding: 6px 12px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: 600;
	display: inline-block;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

.status-pending {
	background: #fef3c7;
	color: #92400e;
	border: 1px solid #f59e0b;
}

.status-paid {
	background: #d1fae5;
	color: #065f46;
	border: 1px solid #10b981;
}

.status-cancelled {
	background: #fee2e2;
	color: #991b1b;
	border: 1px solid #ef4444;
}

.print-btn {
	position: fixed;
	top: 20px;
	right: 20px;
	background: linear-gradient(135deg, #ea580c 0%, #c2410c 100%);
	color: white;
	padding: 12px 24px;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	font-size: 16px;
	font-weight: 600;
	z-index: 1000;
	box-shadow: 0 4px 12px rgba(234, 88, 12, 0.3);
	transition: all 0.3s ease;
}

.print-btn:hover {
	background: linear-gradient(135deg, #c2410c 0%, #9a3412 100%);
	transform: translateY(-2px);
	box-shadow: 0 6px 16px rgba(234, 88, 12, 0.4);
}

.back-btn {
	position: fixed;
	top: 20px;
	left: 20px;
	background: linear-gradient(135deg, #6b7280 0%, #4b5563 100%);
	color: white;
	padding: 12px 24px;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	font-size: 16px;
	font-weight: 600;
	text-decoration: none;
	z-index: 1000;
	box-shadow: 0 4px 12px rgba(107, 114, 128, 0.3);
	transition: all 0.3s ease;
}

.back-btn:hover {
	background: linear-gradient(135deg, #4b5563 0%, #374151 100%);
	transform: translateY(-2px);
	box-shadow: 0 6px 16px rgba(107, 114, 128, 0.4);
}

.bill-number {
	font-size: 18px;
	font-weight: bold;
	color: #ea580c;
	margin-bottom: 5px;
}

.bill-date {
	color: #7c2d12;
	font-size: 14px;
}

@media print {
	.print-btn, .back-btn {
		display: none;
	}
	body {
		background: white;
	}
	.print-header {
		border-bottom: 3px solid #ea580c;
		background: white;
	}
	.bill-header {
		background: white;
		border: 2px solid #fdba74;
	}
	.items-table th {
		background: #ea580c;
		color: white;
	}
	.total-section {
		background: #ea580c;
		color: white;
	}
	.footer {
		background: white;
		border: 1px solid #fdba74;
	}
}

@media ( max-width : 768px) {
	.bill-header {
		flex-direction: column;
		gap: 20px;
	}
	.items-table {
		font-size: 12px;
	}
	.items-table th, .items-table td {
		padding: 10px;
	}
	.company-name {
		font-size: 24px;
	}
	.total-amount {
		font-size: 24px;
	}
}
</style>
</head>
<body>
	<button onclick="window.print()" class="print-btn">
		<i class="fas fa-print"></i> Print Bill
	</button>
	<a href="bill?action=view&id=${bill.billId}" class="back-btn"> <i
		class="fas fa-arrow-left"></i> Back to Bill
	</a>

	<div class="print-header">
		<div class="company-name">Pahana Edu Bookshop</div>
		<div class="company-tagline">Your Trusted Source for Quality
			Books</div>
		<div class="company-info">
			<strong>Address:</strong> Colombo City, Sri Lanka<br> <strong>Phone:</strong>
			+94 11 2345678 | <strong>Email:</strong> info@pahanaedu.com<br>
			<strong>Website:</strong> www.pahanaedu.com
		</div>
	</div>

	<div class="bill-header">
		<div class="customer-info">
			<h3>Bill To:</h3>
			<p>
				<strong>Name:</strong> ${bill.customerName}
			</p>
			<p>
				<strong>Account Number:</strong> ${bill.accountNumber}
			</p>
			<p>
				<strong>Date:</strong> ${bill.billDate}
			</p>
		</div>

		<div class="bill-info">
			<h3>Bill Information:</h3>
			<div class="bill-number">Bill #${bill.billId}</div>
			<div class="bill-date">Generated: ${bill.billDate}</div>
			<p>
				<strong>Status:</strong> <span class="status status-${bill.status}">${bill.status}</span>
			</p>
		</div>
	</div>

	<table class="items-table">
		<thead>
			<tr>
				<th>#</th>
				<th>Product</th>
				<th>Quantity</th>
				<th>Unit Price</th>
				<th>Subtotal</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${bill.items}" varStatus="status">
				<tr>
					<td><strong>${status.index + 1}</strong></td>
					<td><strong>${item.productName}</strong></td>
					<td>${item.quantity}</td>
					<td><fmt:formatNumber value="${item.unitPrice}"
							type="currency" currencySymbol="Rs. " /></td>
					<td><strong><fmt:formatNumber
								value="${item.subtotal}" type="currency" currencySymbol="Rs. " /></strong>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="total-section">
		<h3>
			Total Amount: <span class="total-amount"> <fmt:formatNumber
					value="${bill.totalAmount}" type="currency" currencySymbol="Rs. " />
			</span>
		</h3>
	</div>

	<div class="footer">
		<p>
			<strong>Thank you for your business!</strong>
		</p>
		<p>For any queries, please contact us at info@pahanaedu.com or
			call +94 11 2345678</p>
		<p>This is a computer generated bill. No signature required.</p>
		<p style="margin-top: 15px; font-size: 11px; color: #9a3412;">
			<strong>Pahana Edu Bookshop</strong> - Empowering Education Through
			Quality Books
		</p>
	</div>

	<script>
		// Auto-print when page loads (optional)
		// window.onload = function() {
		//     window.print();
		// };
	</script>
</body>
</html>
