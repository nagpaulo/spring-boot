package br.com.project.webservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.project.webservice.auth.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
}
