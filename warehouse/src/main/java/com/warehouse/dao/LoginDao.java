package com.warehouse.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.entities.Login;
@Service
public class LoginDao {
	@Autowired
	LoginRepository loginrepository;
	public String check(Login login) {
		String status="Inavlid";
		ArrayList<Login> loginbean=(ArrayList<Login>) loginrepository.findAll();
		for (int i = 0; i < loginbean.size(); i++) {
			if(login.getUsername().equals(loginbean.get(i).getUsername())&&login.getPassword().equals(loginbean.get(i).getPassword())){
				status="success";
			}
			
		}
		return status;
	}
	}

