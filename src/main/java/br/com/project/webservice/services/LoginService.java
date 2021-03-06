package br.com.project.webservice.services;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.project.webservice.auth.model.Email;
import br.com.project.webservice.auth.model.Usuario;
import br.com.project.webservice.auth.repository.UsuarioRepository;
import br.com.project.webservice.auth.services.EmailService;
import br.com.project.webservice.auth.services.SecurityServiceImpl;
import br.com.project.webservice.entity.vo.UsuarioAutenticadoVO;
import br.com.project.webservice.entity.vo.UsuarioReenvioSenhaVO;

@Service
public class LoginService {
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@Autowired(required = true)
	private HttpServletRequest requestServlet;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<UsuarioAutenticadoVO> autentication(String username, String password){
		securityServiceImpl.autologin(username, password);
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = requestServlet.getSession(true);
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if(securityContext != null && securityContext.getAuthentication().isAuthenticated()){
			Usuario user = (Usuario) session.getAttribute("SECURITY_USER");
			String ultimoAcesso = session.getAttribute("SECURITY_LAST_ACCESS").toString();
			UserDetails userDetails = (UserDetails) session.getAttribute("SPRING_SECURITY_USERDATAIL");
			Set<GrantedAuthority> grantedAuthorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
			
			UsuarioAutenticadoVO usuarioAutenticadoVO = new UsuarioAutenticadoVO();
			usuarioAutenticadoVO.setUsuario(user);
			usuarioAutenticadoVO.setLastAcess(ultimoAcesso);
			usuarioAutenticadoVO.setGrantedAuthorities(grantedAuthorities);
			return new ResponseEntity<UsuarioAutenticadoVO>(usuarioAutenticadoVO, headers, HttpStatus.OK);
		}else if(!securityContext.getAuthentication().isAuthenticated()){
			return new ResponseEntity<UsuarioAutenticadoVO>(headers, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<UsuarioAutenticadoVO>(headers, HttpStatus.NOT_FOUND);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<UsuarioReenvioSenhaVO> reenvioSenha(String username, String cpf, String email){
		HttpHeaders headers = new HttpHeaders();
		UsuarioReenvioSenhaVO userSend = new UsuarioReenvioSenhaVO();
		Usuario user = usuarioRepository.findByLogin(username);
		if(user != null){
			if(user.getCpf().equals(cpf) && user.getEmail().equals(email)){
				Map<String, String> params = new HashedMap();
				params.put("usuario", user.getUsuario());
				params.put("senha", user.getSenha());
				Email emailTemplate = new Email();
				emailTemplate.setRemetente("Pronatec");
				emailTemplate.addDestinatario(email);
				emailTemplate.setAssunto("Reenvio de Senha");
				emailTemplate.setHtml(true);
				emailTemplate.setUsingTemplate(true);
				emailTemplate.setTemplate("nova_senha_html.vm");
				emailTemplate.setParametros(params);
		        emailService.enviar(emailTemplate);
		        
		        userSend.setUsuario(user);
		        userSend.setMessagem("Email enviado com sucesso.");
		        userSend.setSendMail(true);
		        return new ResponseEntity<UsuarioReenvioSenhaVO>(userSend, headers, HttpStatus.OK);
			}else{
				userSend.setUsuario(null);
		        userSend.setMessagem("Email não enviado. Verifique se os campos estão correto.");
		        userSend.setSendMail(false);
				return new ResponseEntity<UsuarioReenvioSenhaVO>(userSend, headers, HttpStatus.CONFLICT);
			}
		}
		
		userSend.setUsuario(null);
        userSend.setMessagem("Email não enviado. Usuario não encontrado.");
        userSend.setSendMail(false);
		return new ResponseEntity<UsuarioReenvioSenhaVO>(userSend, headers, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> registration(){
		Usuario userForm = new Usuario();
		HttpHeaders headers = new HttpHeaders();
		
		userForm.setUsuario("PAULO ROBERTO MESQUITA DA SILVA");
		userForm.setLogin("NAGPAULO");
		userForm.setSenha(bCryptPasswordEncoder.encode("123"));
		userForm.setAtivo(true);
		userForm.setAtualizousenha(true);
		userForm.setCpf("868.392.123-30");
		userForm.setDtCriacao(new Date());
		userForm.setDtNascimento(new Date());
		userForm.setEmail("nagpaulo@gmail.com");
		userForm.setSexo("M");
		userForm.setSenhaCorrigida(true);
		
		usuarioRepository.save(userForm);
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
}
