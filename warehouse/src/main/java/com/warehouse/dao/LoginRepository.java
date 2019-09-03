package com.warehouse.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.entities.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login,String> {
	
}
