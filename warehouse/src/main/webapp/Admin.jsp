<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="javax.servlet.RequestDispatcher"
	import="javax.servlet.http.HttpServletResponse"
	import="javax.servlet.http.HttpServletRequest"
	import="com.warehouse.entities.*"
	import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 25%;
	background-color: #f1f1f1;
	height: 100%;
	position: fixed;
	overflow: auto;
}

li a {
	display: block;
	color: #000;
	padding: 8px 16px;
	text-decoration: none;
}

/* Change the link color on hover */
li a:hover {
	background-color: #555;
	color: white;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

input {
	margin-bottom: 10px;
	diplay: block;
}
</style>
<title>Admin operations</title>
</head>
<body>
	<h2>WAREHOUSE MANAGEMENT</h2>
	<h3>
		Welcome
		<%=session.getAttribute("sessionname")%></h3>
		<center>${msg}</center>
	<div>
		<ul>
			<li><a href="Admin.jsp?operation=Add_Customer">Add Customer</a></li>
			<li><a href="ViewCustomers">View All Customers</a></li>
			<li><a href="Admin.jsp?operation=View_Customer">Customer Details</a></li>
			<li><a href="Admin.jsp?operation=Delete_Customer">Delete Customer</a></li>		
			<li><a href="ViewItems">Items Details</a></li>
			<li><a href="Admin.jsp?operation=Add_Item">Add Item</a>
			<li><a href="view_stock">Add stock</a></li>		
			<li><a href="Admin.jsp?operation=Order">Place Order</a>
			<li><a href="Admin.jsp?operation=Purchase_Date">Display Purchase Details</a></li>	
			<li><a href="login.jsp">Logout</a></li>
		</ul>
	</div>
	
<%
String operation=(String)request.getParameter("operation");
if(operation!=null){
	if(operation.equals("Add_Customer")){%>
	<form action="/add" method="post">
		<center><h1>Add Customer Details</h1></center>
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Customer Code:<input type="text" name="customer_code" required><br><br>
			Name:<input type="text" name="customer_name" required><br><br>
			Contact details:<input type="text" name="phone_number" id="s" required><br><br>
			Address:<input type="text" name="address" required><br><br>
			<input type="submit" value="submit" onclick="phonenumber(document.getElementId(s))"><br><br>
			 <input type="button" value="back" onclick="history.back()"><br><br>
				
		</div>
	</form>
	<%
	}
	if(operation.equals("view_customers")){

%>

<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
	<center><h1>Customer Details</h1></center>
		<table style="width: 80%" id="a" border=1 align="center">
			<tr>
				
				
				<th>Customer Code</th>
				<th>Customer Name</th>
				<th>Address</th>
				<th>Phone Number</th>
			</tr>

			<%
				@SuppressWarnings("unchecked")
						List<Customer> customers = (List<Customer>) session.getAttribute("customer_details");
			System.out.print(customers);
						for (Customer c : customers) {
			%>
			<tr>
				<td><%=c.getCustomer_code()%></td>
				<td><%=c.getCustomer_name()%></td>
				<td><%=c.getAddress()%></td>
				<td><%=c.getPhone_number()%></td>
			</tr>

			<%
				}
	}
			%>
		</table>
	</div>
	<%
	if(operation.equals("View_Customer")){
	%>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
	<center><h1>View Customer</h1></center>
	<form action="/view_customer" method="Post">
		Customer_name:<input type="text" name="customer_name" required><br><br>
		<input type="submit" value="View Customer">
		</form></div>
	
<%} 
	if(operation.equals("view_a_customer")){
%>
<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
	<h1>Customer Details</h1>
		<table style="width: 80%" id="a" border=1>
			<tr>
				
				
				<th>Customer Code</th>
				<th>Customer Name</th>
				<th>Address</th>
				<th>Phone Number</th>
			</tr>

			<%
				@SuppressWarnings("unchecked")
			Optional<Customer> customers = (Optional<Customer>) session.getAttribute("customer_details");
			if(customers.get().getCustomer_name()!=null){
			%>
			<tr>
				<td><%=customers.get().getCustomer_code()%></td>
				<td><%=customers.get().getCustomer_name()%></td>
				<td><%=customers.get().getAddress()%></td>
				<td><%=customers.get().getPhone_number()%></td>
			</tr>
				</table>
				<%} %>
	</div>
<%
}
	
	if(operation.equals("Delete_Customer")){
	%>
	<center><h1>Delete Customer</h1></center>
		<form action="deletecustomer" method="post">
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">

			Customer Name:<input type="text" name="customer_name" required><br>
			<input type="submit" value="submit">
		</div>
	</form>
	<%}
	
	if(operation.equals("view_items")){%>
	
	<center><h1> List of Items</h1></center>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a" border=1 align="center">
			<tr>
				<th>Item Code</th>
				<th>Item Name</th>
				<th>Item Price</th>
				<th>After discount</th>
				<th>Item Stock</th>
			</tr>


			<%
			double discount=0;
			double difference;
				@SuppressWarnings("unchecked")
						List<Item> items = (List<Item>) session.getAttribute("item_details");
						for (Item item : items) {
							if(item.getItem_price()>10 || item.getItem_price()<100){
					    		difference = (int) (item.getItem_price() * 0.1);
					    	discount=(item.getItem_price()-difference);
					    	}
					    	if(item.getItem_price()>100){
					    		difference = (int) (item.getItem_price() * 0.2);
					    	discount=(item.getItem_price()-difference);
					    	}
					    	if(item.getItem_price()<10){
					    	discount=item.getItem_price();
					    	}
			%>

			<tr>
				<td><%=item.getItem_code()%></td>
				<td><%=item.getItem_name()%></td>
				<td><%=item.getItem_price()%></td>
				<td><%=discount%></td>
				<td><%=item.getItem_stock() %></td>
			</tr>

			<%
				}
			%>

		</table>
	</div>
	
	
<%	}
	if(operation.equals("Add_Item")){
		%>
		<center><h1>Add An Item</h1></center>
		<form action="/add_item" method="post">

		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Item Name:	<input type="text" name="item_name" required><br><br>
			Item Price:	<input type="number" name="item_price" required><br><br>
			Item Stock:<input type="number" name="item_stock" required><br><br>
			<br> <input type="submit" value="ADD Stock">
		</div>
	</form>
		
		<%
	}
	if(operation.equals("Add_Stock")){
		%>
		<center><h1>Add Stock</h1></center>
		<form action="/add_stock" method="post">

		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Item Name:<select name="item_code">
			<% @SuppressWarnings("unchecked")
			List<Item> item = (List<Item>) request.getAttribute("item_details");
			for (Item i : item) {
			out.println("<option value="+i.getItem_code()+">"+i.getItem_name()+"</option>");
				} %>	</select>	<br><br>
			Quantity:<input type="number" name="item_stock" required><br><br>
			<br> <input type="submit" value="ADD Stock">
		</div>
	</form>
		<%
	}
	if(operation.equals("Purchase_Date")){
		%>
		<center><h1>Purchase Date</h1></center><form action="purchase_details" method="post">
		
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		Transaction_Date:<input type="date" name="date_Of_purchase" required><br><br>
		<input type="submit" name="check_Transaction"><br><br>
		</form>
		<%
	}
	if(operation.equals("View_Purchase")){
		%>
		<center><h1>Transaction details</h1></center>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a"  border=1>
			<tr>
				<th>transaction_id</th>
				<th>date_Of_purchase</th>
				<th>quantity_purchased</th>
				<th>customer_name</th>
				<th>item_code</th>
			</tr>
			<%
						@SuppressWarnings("unchecked")
						List<Purchase> purchases = (List<Purchase>) session.getAttribute("transaction_details");
						for (Purchase purchase : purchases) {
			%>
			<tr>
				<td><%=purchase.getTransaction_id()%></td>
				<td><%=purchase.getDate_Of_purchase()%></td>
				<td><%=purchase.getQuantity_purchased()%></td>
				<td><%=purchase.getCustomer().getCustomer_name()%></td>
				<td><%=purchase.getItem().getItem_name()%></td>
			</tr>

			<%
				}
			%>
		</table>
	</div>
		<%
	}
	if(operation.equals("Order")){
		%>
		<form  method="post" action="/place_order">
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<h1>Place Order</h1>
		<table border="1" align="center">
			<tr>
				<td>Customer Name:</td>
				<td><input type="text" name="customer_code" value="" required></td>
			</tr>
			<tr>
				<td>Item Code:</td>
				<td><input type="text" name="item_code" value="" required ></td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td><input type="text" name="item_stock" value="" required ></td>
			</tr>
			<tr>
				<td><input type="reset" name="index_clear" value="Clear"></td>
				<td><input type="submit" value="Place Order"></td>
			</tr>
		</table>
		</div>
	</form>
		
		
		<%
	}
}%>
<script>
function phonenumber(inputtxt)
{
  var phoneno = /^\d{10}$/;
  var phone1=/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  var phone2= /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
  if((inputtxt.value.match(phoneno))
        {
      return true;
        }
  else if((inputtxt.value.match(phone1))
		  {
	  return true;
	  }
  else if((inputtxt.value.match(phone2))
		  {
	  return true;
	  }
  
      else
        {
        alert("message");
        return false;
        }
}</script>
</body>
</html>