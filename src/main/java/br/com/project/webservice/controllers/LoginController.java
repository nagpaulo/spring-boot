package br.com.project.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.project.webservice.auth.services.SecurityServiceImpl;
import br.com.project.webservice.auth.services.UserDetailsServiceImpl;

@Controller
public class LoginController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@RequestMapping(value = "/loginin", method = RequestMethod.POST)
	@ResponseBody
	public String acesso(@ModelAttribute("username") String username, @ModelAttribute("password") String password){
		securityServiceImpl.autologin(username, password);
		
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			userDetailsServiceImpl.loadUserByUsername(username);			
		}
		
		return null;
	}
}
