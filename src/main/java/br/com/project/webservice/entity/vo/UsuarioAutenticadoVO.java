package br.com.project.webservice.entity.vo;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import br.com.project.webservice.auth.model.Usuario;

public class UsuarioAutenticadoVO {
	private Usuario usuario;
	private Set<GrantedAuthority> grantedAuthorities;
	private String lastAcess;
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Set<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}
	public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}
	public String getLastAcess() {
		return lastAcess;
	}
	public void setLastAcess(String lastAcess) {
		this.lastAcess = lastAcess;
	}
}
