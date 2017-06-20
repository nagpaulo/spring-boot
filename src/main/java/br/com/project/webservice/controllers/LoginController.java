package br.com.project.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.webservice.auth.services.SecurityServiceImpl;
import br.com.project.webservice.auth.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/authentication")
public class LoginController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@RequestMapping(value = "/loginin", method = RequestMethod.POST)
	public ResponseEntity<?> acesso(@RequestParam("username") String username, @RequestParam("password") String password){
		securityServiceImpl.autologin(username, password);
		HttpHeaders headers = new HttpHeaders();
		
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			UserDetails user =  userDetailsServiceImpl.loadUserByUsername(username);
			headers.add("user", user.getUsername());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	}
}
