package com.warehouse;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.warehouse.dao.*;
import com.warehouse.entities.*;

@RestController
@RequestMapping("/valid")
public class LoginRest {
	@Autowired
	LoginDao logindao;
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	   public String insert(@RequestBody Login login){
			String status=logindao.check(login);
				return status;
		   }
	}