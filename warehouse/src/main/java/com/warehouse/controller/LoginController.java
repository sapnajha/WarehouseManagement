package com.warehouse.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.warehouse.Constants;
import com.warehouse.entities.Login;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	Environment environment;
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loginController()
	{
		ModelAndView modelandview=new ModelAndView("login.jsp");
		Login login=new Login();
		modelandview.addObject(login);
		return modelandview;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String logincontrol(Login login,HttpSession session,HttpServletRequest request)
	{
		ModelAndView modelandview=new ModelAndView();
		modelandview.addObject(login);
		session =request.getSession(true);
		session.setAttribute("sessionname",login.getUsername());
		String url=Constants.url+"/valid/check";
		RestTemplate resttemplate=new RestTemplate();
		String status=resttemplate.postForObject(url, login, String.class);
        if(status.equals("success")) {
        	return "redirect:Admin.jsp";
        }
        else {
        	return "redirect:Error.jsp";
        }
    }
    }