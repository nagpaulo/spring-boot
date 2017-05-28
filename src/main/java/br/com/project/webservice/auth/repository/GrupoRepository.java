package br.com.project.webservice.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.project.webservice.auth.model.Grupo;
import br.com.project.webservice.auth.model.Transacao;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Transacao> findByGrupoTransacao(Long id);
}
