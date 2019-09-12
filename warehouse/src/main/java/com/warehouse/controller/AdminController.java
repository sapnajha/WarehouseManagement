package com.warehouse.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.warehouse.Constants;
import com.warehouse.constant.*;
import com.warehouse.entities.*;

/**
 * 
 * @author sapnajhasapnajha
 *
 */
@Controller
public class AdminController {
	@Autowired
	Environment environment;

	// add customer
	/**
	 * 
	 * @param customer
	 * @param session
	 * @return String status
	 */
	@RequestMapping("/add")
	public ModelAndView addCustomer(Customer customer, HttpSession session) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(customer);
		Login login = new Login();
		login.setUsername((String) session.getAttribute("sessionname"));
		customer.setLogin(login);
		String url = Constants.url+"/operation/addcustomer";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, customer, String.class);
		if (status.equals("success")) {
			modelandview.setViewName("/Admin.jsp");
			modelandview.addObject("message", WMSConstants.ADD_USER_SUCCESS_MSG);
		} else {
			modelandview.setViewName("/Admin.jsp");
			modelandview.addObject("message", WMSConstants.ADD_USER_ERROR_MSG);
		}
		return modelandview;
	}

	// view customers
	/**
	 * 
	 * @param session
	 * @param request
	 * @return ResponseEntity<List<Customer>> customerlist
	 */
	@RequestMapping("/ViewCustomers")
	public ModelAndView viewCustomers(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		RestTemplate restTemplate = new RestTemplate();
		String url = Constants.url+ "/operation/view_customers";
		ResponseEntity<List<Customer>> customerlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>() {
				});
		List<Customer> customer_details = customerlist.getBody();
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("/Admin.jsp?operation=view_customers");
		session.setAttribute("customer_details", customer_details);
		return modelandview;
	}

	// view a particular customer
	/**
	 * 
	 * @param session
	 * @param request
	 * @return String customer_name
	 */
	@RequestMapping("/view_customer")
	public ModelAndView view_A_Customer(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String customer_name = request.getParameter("customer_name");
		RestTemplate restTemplate = new RestTemplate();
		String url = Constants.url+"/operation/view_a_customer/";
		ResponseEntity<Optional<Customer>> customer_data = restTemplate.exchange(url + customer_name, HttpMethod.GET,
				null, new ParameterizedTypeReference<Optional<Customer>>() {
				});
		Optional<Customer> customer_details = customer_data.getBody();
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("/Admin.jsp?operation=view_a_customer");
		session.setAttribute("customer_details", customer_details);
		return modelandview;
	}


	// viewing items
	/**
	 * 
	 * @param session
	 * @param request
	 * @return List<Item> item_details
	 */
	@RequestMapping("/ViewItems")
	public ModelAndView viewItems(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		RestTemplate restTemplate = new RestTemplate();
		String url = Constants.url+ "/operation/view_items";
		ResponseEntity<List<Item>> itemlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Item>>() {
				});
		List<Item> item_details = itemlist.getBody();
		ModelAndView modelandview = new ModelAndView("/Admin.jsp?operation=view_items");
		session.setAttribute("item_details", item_details);
		return modelandview;
	}

	// viewing stock items
	/**
	 * 
	 * @return List<Item> item_details
	 */
	@RequestMapping("/view_stock")
	public ModelAndView viewStock() {
		RestTemplate restTemplate = new RestTemplate();
		String url = Constants.url+ "/operation/view_items";
		ResponseEntity<List<Item>> itemlist = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Item>>() {
				});
		List<Item> item_details = itemlist.getBody();
		ModelAndView modelandview = new ModelAndView("/Admin.jsp?operation=Add_Stock");
		modelandview.addObject("item_details", item_details);
		return modelandview;
	}

	// add stock
	/**
	 * 
	 * @param item
	 * @return String status
	 */
	@RequestMapping(value = "/add_stock")
	public ModelAndView addStock(Item item) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(item);
		String url = Constants.url+ "/operation/addstock";
		RestTemplate resttemplate = new RestTemplate();
		resttemplate.put(url, item, String.class);
		modelandview.setViewName("/Admin.jsp");
		modelandview.addObject("message", WMSConstants.STOCK_ADD_SUCCESS_MSG);
		return modelandview;
	}

	// Place Order
	/**
	 * 
	 * @param customer_code
	 * @param item_code
	 * @param item_stock
	 * @return String status
	 */
	@RequestMapping("/place_order")
	public ModelAndView placeOrder(@RequestParam("customer_code") String customer_code,
			@RequestParam("item_code") int item_code, @RequestParam("item_stock") int item_stock) {
		ModelAndView modelandview = new ModelAndView();
		String url = Constants.url+ "/operation/place_order/" + customer_code + "/" + item_code + "/"
				+ item_stock;
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, null, String.class);
		modelandview.setViewName("/Admin.jsp");
		modelandview.addObject("message", status);

		return modelandview;
	}

	// viewing purchase details
	/**
	 * 
	 * @param session
	 * @param request
	 * @return List<Purchase> transaction_details
	 */
	@RequestMapping("/purchase_details")
	public ModelAndView purchaseDetails(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String date = request.getParameter("date_Of_purchase");
		RestTemplate restTemplate = new RestTemplate();
		String url =Constants.url+ "/operation/purchase_details/";
		ResponseEntity<List<Purchase>> purchase_details = restTemplate.exchange(url + date, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Purchase>>() {
				});
		List<Purchase> transaction_details = purchase_details.getBody();
		ModelAndView modelandview = new ModelAndView("/Admin.jsp?operation=View_Purchase");
		session.setAttribute("transaction_details", transaction_details);
		return modelandview;
	}

	// adding an item
	/**
	 * 
	 * @param item
	 * @param session
	 * @return String status
	 */
	@RequestMapping("/add_item")
	public ModelAndView addItem(Item item, HttpSession session) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(item);
		Login login = new Login();
		login.setUsername((String) session.getAttribute("sessionname"));
		item.setLogin(login);
		String url = Constants.url+"/operation/add_item";
		RestTemplate resttemplate = new RestTemplate();
		String status = resttemplate.postForObject(url, item, String.class);
		if (status.equals("success")) {
			modelandview.setViewName("/Admin.jsp");
			modelandview.addObject("message", WMSConstants.ITEM_ADD_SUCCESS_MSG);
		} else {
			modelandview.setViewName("/Admin.jsp");
			modelandview.addObject("message", WMSConstants.ITEM_ADD_ERROR_MSG);
		}
		return modelandview;
	}

}
