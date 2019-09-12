package com.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.warehouse.entities.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String>  {
	

}
