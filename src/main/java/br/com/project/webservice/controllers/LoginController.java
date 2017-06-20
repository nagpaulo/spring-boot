package br.com.project.webservice.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.webservice.auth.services.SecurityServiceImpl;

@RestController
@RequestMapping("/authentication")
public class LoginController {
	
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@Autowired(required = true)
	private HttpServletRequest requestServlet;
	
	@RequestMapping(value = "/loginin", method = RequestMethod.POST)
	public ResponseEntity<?> acesso(@RequestParam("username") String username, @RequestParam("password") String password){
		securityServiceImpl.autologin(username, password);
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = requestServlet.getSession(true);
		session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null){
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	}
}
