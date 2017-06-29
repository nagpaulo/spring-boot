package br.com.project.webservice.entity.vo;

import br.com.project.webservice.auth.model.Usuario;

public class UsuarioReenvioSenhaVO {
	private Usuario usuario;
	private Boolean sendMail = false;
	private String messagem;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Boolean getSendMail() {
		return sendMail;
	}
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}
	public String getMessagem() {
		return messagem;
	}
	public void setMessagem(String messagem) {
		this.messagem = messagem;
	}
}
