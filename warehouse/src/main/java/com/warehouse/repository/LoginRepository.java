package com.warehouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.entities.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login,String> {
	@Query("select login from Login login where login.username= :username and login.password= :password")
	public Login validate(@Param(value = "username") String username, @Param(value = "password") String password);
}
