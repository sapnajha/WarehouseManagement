package com.warehouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.entities.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

}
