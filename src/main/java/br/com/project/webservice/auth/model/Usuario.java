package br.com.project.webservice.auth.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Paulo Roberto
 */
@Entity
@Table(name = "tb_usuario", schema="tethys")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 5110990345399379981L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ci_usuario")
	private Long id;
	@Column(name = "nm_usuario")
	private String usuario;
	@Column(name = "nm_login")
	private String login;
	@JsonIgnore
	@Column(name = "nm_senha")
	private String senha;
	@Column(name = "ds_email")
	private String email;
	@Column(name = "nm_cpf")
	private String cpf;
	@Column(name = "dt_nascimento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNascimento;
	@Column(name = "fl_sexo")
	private String sexo;
	@Column(name = "fl_atualizousenha")
	private Boolean atualizousenha;
	@Column(name = "dt_criacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCriacao;
	@Column(name = "fl_senha_corrigida")
	private Boolean senhaCorrigida;
	@Column(name = "fl_ativo")
	private Boolean ativo;
	
	@ManyToMany
    @JoinTable(name = "tb_usuariogrupos", joinColumns = @JoinColumn(name = "cd_usuario"), inverseJoinColumns = @JoinColumn(name = "cd_grupo"), schema="tethys")
    private Set<Grupo> usuarioGrupo;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Boolean getAtualizousenha() {
		return atualizousenha;
	}
	public void setAtualizousenha(Boolean atualizousenha) {
		this.atualizousenha = atualizousenha;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public Boolean getSenhaCorrigida() {
		return senhaCorrigida;
	}
	public void setSenhaCorrigida(Boolean senhaCorrigida) {
		this.senhaCorrigida = senhaCorrigida;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Set<Grupo> getUsuarioGrupo() {
		return usuarioGrupo;
	}
	public void setUsuarioGrupo(Set<Grupo> usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

