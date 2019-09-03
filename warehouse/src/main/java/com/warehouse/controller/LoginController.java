package com.warehouse.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.warehouse.entities.Login;

@Controller
@RequestMapping("/login")
public class LoginController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loginController()
	{
		ModelAndView mv=new ModelAndView("login.jsp");
		Login login=new Login();
		mv.addObject(login);
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String logincontrol(Login login,HttpSession session,HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject(login);
		session =request.getSession(true);
		session.setAttribute("sessionname",login.getUsername());
		String url="http://localhost:8181/valid/check";
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