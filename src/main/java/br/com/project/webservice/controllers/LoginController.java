package br.com.project.webservice.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.webservice.auth.model.Usuario;
import br.com.project.webservice.auth.repository.UsuarioRepository;
import br.com.project.webservice.auth.services.SecurityServiceImpl;

@RestController
@RequestMapping("/authentication")
public class LoginController {
	
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@Autowired(required = true)
	private HttpServletRequest requestServlet;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value = "/loginin", method = RequestMethod.POST)
	public ResponseEntity<?> acesso(@RequestParam("username") String username, @RequestParam("password") String password){
		securityServiceImpl.autologin(username, password);
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = requestServlet.getSession(true);
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if(securityContext != null && securityContext.getAuthentication().isAuthenticated()){
			Usuario user = (Usuario) session.getAttribute("SECURITY_USER");
			headers.add("session.user", user.getUsuario());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registration(){
		Usuario userForm = new Usuario();
		HttpHeaders headers = new HttpHeaders();
		
		userForm.setUsuario("PAULO ROBERTO MESQUITA DA SILVA");
		userForm.setLogin("NAGPAULO");
		userForm.setSenha(bCryptPasswordEncoder.encode("123"));
		userForm.setAtivo(true);
		userForm.setAtualizousenha(true);
		userForm.setCpf("840.185.773-20");
		userForm.setDtCriacao(new Date());
		userForm.setDtNascimento(new Date());
		userForm.setEmail("nagpaulo@gmail.com");
		userForm.setSexo("M");
		userForm.setSenhaCorrigida(true);
		
		usuarioRepository.save(userForm);
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
}
