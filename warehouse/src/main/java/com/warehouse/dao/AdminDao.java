package com.warehouse.dao;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.entities.Customer;
import com.warehouse.entities.Item;
import com.warehouse.entities.Purchase;

@Service
public class AdminDao {
	@Autowired
	AdminRepository adminrepository;
	@Autowired
	PurchaseRepository purchaserepository;
	@Autowired
	ItemRepository itemRepository;

	// Add a customer
	public String addCustomer(Customer customer) {
		adminrepository.save(customer);
		return "success";
	}

	// View All the customers
	/*
	 * This method will retrieve the list of customers from the database named
	 * Customer.
	 */
	public List<Customer> viewCustomers() {
		List<Customer> customer = adminrepository.findAll();
		return customer;
	}

	// Delete a Customer
	/* This method will delete a Particular Customer details from the database. */
	public String deleteCustomer(Customer customer) {
		adminrepository.delete(customer);
		return "success";
	}

	// View All the items in warehouse
	/* This method will retrieve the list of Items from the database named Item. */
	public List<Item> viewItems() {
		List<Item> item = adminrepository.itemList();
		return item;
	}

	// Add A Stock of item
	/*
	 * This method will add stock of a particular item in the database named Item.
	 */
	public String addStock(Item item) {
		List<Item> itemlist = adminrepository.itemDetails(item.getItem_code());
		int stock = itemlist.get(0).getItem_stock();
		stock += item.getItem_stock();
		item.setItem_stock(stock);
		adminrepository.updateItem(item.getItem_stock(), item.getItem_code());
		return "success";
	}

	// Place an order for the customer
	/*
	 * This method is used to place an order for existing customer.If the particular
	 * item is not present in database or if the item is out of stock then the order
	 * can not be placed. Otherwise it will return the transaction_id of the order.
	 */
	public String placeOrder(String customer_name, int item_code, int item_stock) {
		Purchase purchase = new Purchase();
		purchase.setCustomer(adminrepository.findById(customer_name).get());
		purchase.setItem(itemRepository.findById(item_code).get());
		String status = null;
		if (purchase.getCustomer() != null) {
			if (purchase.getItem() != null) {
				if (purchase.getItem().getItem_stock() >= item_stock) {
					LocalDate date = LocalDate.now();
					purchase.setDate_Of_purchase(date);
					purchase.setQuantity_purchased(item_stock);
					Item item1 = itemRepository.findById(item_code).get();
					item1.setItem_stock(item1.getItem_stock() - item_stock);
					itemRepository.saveAndFlush(item1);
					purchaserepository.saveAndFlush(purchase);
					status = "transaction_id is" + purchase.getTransaction_id();
				} else {
					status = "Stock is Less";
				}

			} else {
				status = "Item not found";
			}
		} else {
			status = "Invalid User";
		}
		return status;
	}

	// View Transaction Details
	/*
	 * This method will retrieve the list of Transaction which were made on a
	 * particular date.
	 */
	public List<Purchase> purchaseDetails(LocalDate date) {
		List<Purchase> transaction_details = (List<Purchase>) purchaserepository.findByDate_Of_purchase(date);
		return transaction_details;
	}

	// Add a new item in warehouse
	/*
	 * This method is used to add an item in the database along with its detail such
	 * as item_code, item_price,item_name and item_stock.
	 */
	public String addItem(Item item) {
		String status = "Invalid";
		itemRepository.saveAndFlush(item);
		status = "success";
		return status;
	}

	// View a particular customer
	/*This method will retrieve the detail of a particular customer based on its name(primary key)
	 */
	public Optional<Customer> view_A_Customer(String customer_name) {
		Optional<Customer> customer_data = adminrepository.findById(customer_name);
		return customer_data;
	}

}
