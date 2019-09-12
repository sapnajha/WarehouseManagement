package com.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.warehouse.entities.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	@Query("select item from Item item")
	public List<Item> itemList();
	
	@Query("select item from Item item where item.item_code=?1")
	public List<Item> itemDetails(int itemcode);
	@Modifying
	@Transactional
	@Query("UPDATE Item item SET item.item_stock = :item_stock WHERE  item.item_code = :item_code")
	int updateItem(@Param("item_stock") int item_stock, @Param("item_code") int item_code);
}
