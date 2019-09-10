package com.warehouse.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.warehouse.entities.Customer;
import com.warehouse.entities.Item;
import com.warehouse.entities.Login;
import com.warehouse.entities.Purchase;

@Controller

public class AdminController {

	// add customer
	/*
	 * This method will take customer details from Admin.jsp page and add in
	 * customer bean object in parameters and send the customer details to AdminRest
	 * through the url mentioned below. If customers is added successfully AdminRest
	 * will return a success message and it will redirect it to Admin.jsp Page with
	 * a success message else it will return unsuccessful message.
	 */
	@RequestMapping("/add")
	public ModelAndView addCustomer(Customer customer, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(customer);
		Login login = new Login();
		login.setUsername((String) session.getAttribute("sessionname"));
		customer.setLogin(login);
		String url = "http://localhost:8181/operation/addcustomer";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, customer, String.class);
		if (status.equals("success")) {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "User added Successfully");
		} else {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "User Could not be added ");
		}
		return mv;
	}

	// view customers
	/*
	 * This method will go to AdminRest through the url mentioned below. AdminRest
	 * will return the list of all the customers in the warehouse and then it will
	 * be redirected to the Admin.jsp Page along with the list of customer_details.
	 */
	@RequestMapping("/ViewCustomers")
	public ModelAndView viewCustomers(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8181/operation/view_customers";
		ResponseEntity<List<Customer>> customerlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>() {
				});
		List<Customer> customer_details = customerlist.getBody();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/Admin.jsp?operation=view_customers");
		session.setAttribute("customer_details", customer_details);
		return mv;
	}

	// view a particular customer
	/*
	 * This method will accept the customer_name and send it to AdminRest through
	 * the url mentioned below. AdminRest will return the detail of that particular
	 * customer and then it will be redirected to the Admin.jsp Page along with the
	 * list of customer_detail.
	 */
	@RequestMapping("/view_customer")
	public ModelAndView view_A_Customer(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String customer_name = request.getParameter("customer_name");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8181/operation/view_a_customer/";
		ResponseEntity<Optional<Customer>> customer_data = restTemplate.exchange(url + customer_name, HttpMethod.GET,
				null, new ParameterizedTypeReference<Optional<Customer>>() {
				});
		Optional<Customer> customer_details = customer_data.getBody();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/Admin.jsp?operation=view_a_customer");
		session.setAttribute("customer_details", customer_details);
		return mv;
	}

	// delete a customer
	/*
	 * This method will accept the customer_name and send it to AdminRest through
	 * the url mentioned below. AdminRest will return a success message if the
	 * particular customer is deleted successfully and then it will be redirected to
	 * the Admin.jsp Page with a success message , otherwise it will given unsucess
	 * message.
	 */
	@RequestMapping("/deletecustomer")
	public ModelAndView deleteCustomer(Customer customer) {
		System.out.println(customer.getCustomer_name());
		ModelAndView mv = new ModelAndView();
		mv.addObject(customer);
		String url = "http://localhost:8181/operation/deletecustomer";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, customer, String.class);
		if (status.equals("success")) {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "User deleted Successfully");
		} else {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "User could not be deleted");
		}
		return mv;
	}

	// viewing items
	/*
	 * This method will go to AdminRest through the url mentioned below. AdminRest
	 * will return the list of all the items present in the warehouse and then it
	 * will be redirected to the Admin.jsp Page along with the list of item_details.
	 */
	@RequestMapping("/ViewItems")
	public ModelAndView viewItems(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8181/operation/view_items";
		ResponseEntity<List<Item>> itemlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Item>>() {
				});
		List<Item> item_details = itemlist.getBody();
		ModelAndView mv = new ModelAndView("/Admin.jsp?operation=view_items");
		session.setAttribute("item_details", item_details);
		return mv;
	}

	// viewing stock items
	/*
	 * This method will go to AdminRest through the url mentioned below. AdminRest
	 * will return the list of all the items in the warehouse and then it will be
	 * redirected to the Admin.jsp Page along with the list of items and display in
	 * dropdown menu for selection of the item during adding stock.
	 */
	@RequestMapping("/view_stock")
	public ModelAndView viewStock() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8181/operation/view_items";
		ResponseEntity<List<Item>> itemlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Item>>() {
				});
		List<Item> item_details = itemlist.getBody();
		ModelAndView mv = new ModelAndView("/Admin.jsp?operation=Add_Stock");
		mv.addObject("item_details", item_details);
		return mv;
	}

	// add stock
	/*
	 * This method will accept the item request (such as item_code and item_stock)
	 * and go to AdminRest through the url mentioned below. AdminRest will return
	 * the a success message if the stock is added succsssfully and then it will be
	 * redirected to the Admin.jsp Page with a success message, Otherwise it will
	 * return an error message
	 */
	@RequestMapping(value = "/add_stock")
	public ModelAndView addStock(Item item) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(item);
		String url = "http://localhost:8181/operation/addstock";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, item, String.class);
		if (status.equals("success")) {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "Stock added Successfully");
		} else {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "Stock could not be added");
		}
		return mv;
	}

	// Place Order
	/*
	 * This method will accept all the order details (such as customer_name,
	 * item_code and item_stock) and go to AdminRest through the url mentioned
	 * below. AdminRest will return the transaction_id if the order is placed
	 * successfully.Otherwise it will return an appropriate message based on the
	 * condition if the order can not be placed successfully.
	 */
	@RequestMapping("/place_order")
	public ModelAndView placeOrder(@RequestParam("customer_code") String customer_code,
			@RequestParam("item_code") int item_code, @RequestParam("item_stock") int item_stock) {
		ModelAndView mv = new ModelAndView();
		// get List of items
		String url = "http://localhost:8181/operation/place_order/" + customer_code + "/" + item_code + "/"
				+ item_stock;
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, null, String.class);
		mv.setViewName("/Admin.jsp");
		mv.addObject("message", status);

		return mv;
	}

	// viewing purchase details
	/*
	 * This method will take the date from Admin.jsp Page and will go to AdminRest
	 * through the url mentioned below. AdminRest will return the list of all the
	 * transaction happend on that particular date and then it will be redirected to
	 * the Admin.jsp Page along with the list of purchase_details.
	 */
	@RequestMapping("/purchase_details")
	public ModelAndView purchaseDetails(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String date = request.getParameter("date_Of_purchase");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8181/operation/purchase_details/";
		ResponseEntity<List<Purchase>> purchase_details = restTemplate.exchange(url + date, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Purchase>>() {
				});
		List<Purchase> transaction_details = purchase_details.getBody();
		ModelAndView mv = new ModelAndView("/Admin.jsp?operation=View_Purchase");
		session.setAttribute("transaction_details", transaction_details);
		return mv;
	}

	// adding an item
	/*
	 * This method will accept the item details (such as item_name ,item_price and
	 * item_stock) and go to AdminRest through the url mentioned below. AdminRest
	 * will return the a success message if the item is added succsssfully in the
	 * database and then it will be redirected to the Admin.jsp Page with a success
	 * message, Otherwise it will return with an error message.
	 */
	@RequestMapping("/add_item")
	public ModelAndView addItem(Item item, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(item);
		Login login = new Login();
		login.setUsername((String) session.getAttribute("sessionname"));
		item.setLogin(login);
		String url = "http://localhost:8181/operation/add_item";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, item, String.class);
		if (status.equals("success")) {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "Item added Successfully");
		} else {
			mv.setViewName("/Admin.jsp");
			mv.addObject("message", "Item could not be Added");
		}
		return mv;
	}

}
