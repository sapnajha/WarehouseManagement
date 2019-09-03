package com.warehouse.dao;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.warehouse.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
	@Query("select p from Purchase p where p.date_Of_purchase = :date")
	List<Purchase> findByDate_Of_purchase(@PathVariable("date") LocalDate date);

}
