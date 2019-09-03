package com.warehouse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.dao.*;
import com.warehouse.entities.*;

@RestController
@RequestMapping("/operation")
public class AdminRest {
	@Autowired
	AdminDao admindao;
	
	@RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
	   public String addCustomer(@RequestBody Customer customer) {
		String status=admindao.addCustomer(customer);
			return status;
		
		   }
	@RequestMapping(value = "/view_customers", method = RequestMethod.GET)
	   public List<Customer> viewCustomers() {
		
		List<Customer> customers=admindao.viewCustomers();
			return customers;	
		   }
	@RequestMapping(value = "/view_a_customer/{customer_name}", method = RequestMethod.GET)
	   public Optional<Customer> view_A_Customer(@PathVariable("customer_name") String customer_name) {
		Optional<Customer> customer_data=admindao.view_A_Customer(customer_name);
			return customer_data;	
		   }
	@RequestMapping(value = "/deletecustomer", method = RequestMethod.POST)
	   public String deleteCustomer(@RequestBody Customer customer) {
		String status=admindao.deleteCustomer(customer);
		return status;	
		   }
	
	@RequestMapping(value = "/view_items", method = RequestMethod.GET)
	   public List<Item> viewItems() {
		
		List<Item> item=admindao.viewItems();
			return item;	
		   }
	@RequestMapping(value = "/addstock", method = RequestMethod.POST)
	   public String addCustomer(@RequestBody Item item) {
		String status=admindao.addStock(item);
			return status;
		
		   }
	@RequestMapping(value = "/place_order/{customer_code}/{item_code}/{item_stock}", method = RequestMethod.POST)
	   public String placeOrder(@PathVariable("customer_code")String customer_code,@PathVariable("item_code")int item_code,@PathVariable("item_stock")int item_stock) {
		String status=admindao.placeOrder(customer_code,item_code,item_stock);
			return status;
		
		   }
	@RequestMapping(value = "/purchase_details/{date}", method = RequestMethod.GET)
	   public List<Purchase> purchaseDetails(@PathVariable("date") String date) {
		
		List<Purchase> transaction_details=admindao.purchaseDetails(LocalDate.parse(date));
			return transaction_details;	
		   }

	@RequestMapping(value = "/add_item", method = RequestMethod.POST)
	   public String addItem(@RequestBody Item item) {
		String status=admindao.addItem(item);
			return status;
		
		   }
}
