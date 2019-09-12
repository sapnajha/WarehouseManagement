package com.warehouse.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.entities.*;
import com.warehouse.repository.*;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginrepository;

	public String check(Login login) {
		Login loginRes = loginrepository.validate(login.getUsername(), login.getPassword());
		if (loginRes == null) {
			return "Inavlid";
		} else
			return "success";
	}
}
