package com.warehouse.restcontroller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.entities.*;
import com.warehouse.services.*;

/** 
 * 
 * @author sapnajhasapnajha
 *@
 */
@RestController
@RequestMapping("/operation")
public class AdminRest {
	@Autowired
	AdminService adminservice;
	/**
	 * 
	 * @param customer
	 * @return String status
	 */
	@RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
	   public String addCustomer(@RequestBody Customer customer) {
		String status=adminservice.addCustomer(customer);
			return status;
		
		   }
	/**
	 * 
	 * @return List<Customer> customers
	 */
	@RequestMapping(value = "/view_customers", method = RequestMethod.GET)
	   public List<Customer> viewCustomers() {
		
		List<Customer> customers=adminservice.viewCustomers();
			return customers;	
		   }
	/**
	 * 
	 * @param customer_name
	 * @return Optional<Customer> customer_data
	 */
	@RequestMapping(value = "/view_a_customer/{customer_name}", method = RequestMethod.GET)
	   public Optional<Customer> view_A_Customer(@PathVariable("customer_name") String customer_name) {
		Optional<Customer> customer_data=adminservice.view_A_Customer(customer_name);
			return customer_data;	
		   }
	
	/** 
	 * 
	 * 
	 * @return item
	 */
	@RequestMapping(value = "/view_items", method = RequestMethod.GET)
	   public List<Item> viewItems() {
		
		List<Item> item=adminservice.viewItems();
			return item;	
		   }
	/**
	 * 
	 * @param item
	 * @return String status
	 */
	@RequestMapping(value = "/addstock", method = RequestMethod.PUT)
	   public String addCustomer(@RequestBody Item item) {
		String status=adminservice.addStock(item);
			return status;
		
		   }
	/**
	 * 
	 * @param customer_code
	 * @param item_code
	 * @param item_stock
	 * @return String status
	 */
	@RequestMapping(value = "/place_order/{customer_code}/{item_code}/{item_stock}", method = RequestMethod.POST)
	   public String placeOrder(@PathVariable("customer_code")String customer_code,@PathVariable("item_code")int item_code,@PathVariable("item_stock")int item_stock) {
		String status=adminservice.placeOrder(customer_code,item_code,item_stock);
			return status;
		
		   }
	/**
	 * 
	 * @param date
	 * @return List<Purchase> transaction_details
	 */
	@RequestMapping(value = "/purchase_details/{date}", method = RequestMethod.GET)
	   public List<Purchase> purchaseDetails(@PathVariable("date") String date) {
		
		List<Purchase> transaction_details=adminservice.purchaseDetails(LocalDate.parse(date));
			return transaction_details;	
		   }
	/**
	 * 
	 * @param item
	 * @return String status
	 */
	@RequestMapping(value = "/add_item", method = RequestMethod.POST)
	   public String addItem(@RequestBody Item item) {
		String status=adminservice.addItem(item);
			return status;
		
		   }
}
