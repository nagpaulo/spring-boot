package br.com.project.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.webservice.entity.vo.UsuarioAutenticadoVO;
import br.com.project.webservice.entity.vo.UsuarioReenvioSenhaVO;
import br.com.project.webservice.services.LoginService;

@RestController
@RequestMapping("/authentication")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/loginin", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<UsuarioAutenticadoVO> acesso(@RequestParam("username") String username, @RequestParam("password") String password){
		return loginService.autentication(username, password);
	}
	
	@RequestMapping(value = "/esqueceusenha", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity<UsuarioReenvioSenhaVO> esqueceusenha(@RequestParam("username") String username, 
    								@RequestParam("cpf") String cpf,
    								@RequestParam("email") String email) 
	{
		return loginService.reenvioSenha(username, cpf, email);
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registration(){
		return loginService.registration();
	}
}
