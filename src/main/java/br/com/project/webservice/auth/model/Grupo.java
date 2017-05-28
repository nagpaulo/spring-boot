package br.com.project.webservice.auth.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
*
* @author Paulo Roberto
*/
@Entity
@Table(name = "tb_grupo", schema="tethys")
public class Grupo implements Serializable{
	
	private static final long serialVersionUID = 1314647720244879486L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_grupo")
    private Long id;
    
	@Column(name = "nm_grupo")
    private String grupo;
	
	@OneToOne
	@JoinColumn(name = "cd_modulo", nullable = false)
    private Modulo Modulo;
    
    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCadastro;
    
    @Column(name = "fl_nivel_acesso")
    private Integer NivelAcesso;

	public Long getId() {
		return id;
	}	
	
	@ManyToMany
    @JoinTable(name = "tb_grupotransacoes", joinColumns = @JoinColumn(name = "cd_grupo"), inverseJoinColumns = @JoinColumn(name = "cd_transacao"), schema="tethys")
    private Set<Transacao> grupoTransacao;

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Modulo getModulo() {
		return Modulo;
	}

	public void setModulo(Modulo modulo) {
		Modulo = modulo;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Integer getNivelAcesso() {
		return NivelAcesso;
	}

	public void setNivelAcesso(Integer nivelAcesso) {
		NivelAcesso = nivelAcesso;
	}

	public Set<Transacao> getGrupoTransacao() {
		return grupoTransacao;
	}

	public void setGrupoTransacao(Set<Transacao> grupoTransacao) {
		this.grupoTransacao = grupoTransacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Grupo other = (Grupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
}
