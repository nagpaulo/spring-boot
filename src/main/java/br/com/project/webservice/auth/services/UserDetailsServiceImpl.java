package br.com.project.webservice.auth.services;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.webservice.auth.model.Grupo;
import br.com.project.webservice.auth.model.Modulo;
import br.com.project.webservice.auth.model.Transacao;
import br.com.project.webservice.auth.model.Usuario;
import br.com.project.webservice.auth.model.UsuarioAcesso;
import br.com.project.webservice.auth.repository.ModuloRepository;
import br.com.project.webservice.auth.repository.TransacaoRepository;
import br.com.project.webservice.auth.repository.UsuarioAcessoRepository;
import br.com.project.webservice.auth.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
    private UsuarioRepository userRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private UsuarioAcessoRepository usuarioAcessoRepository;
	
	@Autowired
	private ModuloRepository moduloRepository;

	@Autowired(required = true)
	private HttpServletRequest requestServlet;
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();    	
    	Usuario user = userRepository.findByLogin(username);
    	List<Transacao> listTransacao = new ArrayList<>();
    	List<Grupo> listGrupo = new ArrayList<>();
    	
    	ResourceBundle resource = ResourceBundle.getBundle("project");
    	Long moduloId = Long.parseLong(resource.getString("modulo.id"));
    	Modulo modulo = moduloRepository.findOne(moduloId);
    	
    	if(!user.getUsuarioGrupo().isEmpty()){
    		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER_AUTH"));
	    	for (Grupo grupo : user.getUsuarioGrupo()) {
	    		if(grupo.getModulo().getId() == moduloId){
	    			listGrupo.add(grupo);
	    			if(grupo.getGrupo().equals(new String("MASTER"))){
						listTransacao = transacaoRepository.findAll();
						listTransacao.forEach(transacao->{
							grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+transacao.getLabel().toUpperCase()));
						});
												
				    	UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), grantedAuthorities);				    	
				    	GravarAcesso(user, modulo, userDetails);
				    	
				    	return userDetails;
					}else{
						Set<Transacao> setTransacao = grupo.getGrupoTransacao();
						setTransacao.forEach(transacao->{
							grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+transacao.getLabel().toUpperCase()));
						});
					}
	    		}
			}
	    	
	    	//Gravar Acesso do Usuario.	    		
	    	UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), grantedAuthorities);
	    	GravarAcesso(user, modulo, userDetails);
	    	
	    	return userDetails;
    	}else{
    		 return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), false, false, false, false, grantedAuthorities);
    	}

    }
    
    public void GravarAcesso(Usuario user, Modulo modulo, UserDetails userDetails){
    	UsuarioAcesso usuarioAcesso = usuarioAcessoRepository.findByUsuarioAndModulo(user, modulo);
    	String ip = requestServlet.getRemoteAddr();
    	
		if(usuarioAcesso == null){
			//usuarioAcesso = listAcessUser.get(0);
			usuarioAcesso = new UsuarioAcesso();
			usuarioAcesso.setModulo(modulo);
			usuarioAcesso.setUsuario(user);
		}
    	
		usuarioAcesso.setDataInicio(Calendar.getInstance().getTime());
		usuarioAcesso.setDataFim(null);
		usuarioAcesso.setIp(ip);		
		usuarioAcessoRepository.save(usuarioAcesso);
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, user.getSenha(), userDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.getContext();

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			securityContext.setAuthentication(usernamePasswordAuthenticationToken);
			
			DateFormat dtHora = DateFormat.getDateTimeInstance();
			String ultimoAcesso = dtHora.format(usuarioAcesso.getDataInicio());
			
			// Create a new session and add the security context.
		    HttpSession session = requestServlet.getSession(true);
		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		    session.setAttribute("SECURITY_USER", user);
		    session.setAttribute("SECURITY_USER_ACCESS", usuarioAcesso);
		    session.setAttribute("SECURITY_LAST_ACCESS", ultimoAcesso);
		}
    	
    }
}
 