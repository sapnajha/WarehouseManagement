package com.warehouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.warehouse.entities.*;

@Repository
public interface AdminRepository extends JpaRepository<Customer,String>  {
	@Query("select s from Item s")
	public List<Item> itemList();
	
	@Query("select s from Item s where s.item_code=?1")
	public List<Item> itemDetails(int itemcode);
	@Modifying
	@Transactional
	@Query("UPDATE Item c SET c.item_stock = :item_stock WHERE  c.item_code = :item_code")
	int updateItem(@Param("item_stock") int item_stock, @Param("item_code") int item_code);

}
