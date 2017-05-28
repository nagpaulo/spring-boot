package br.com.project.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.project.webservice.entity.Autores;

public interface AutorRepository extends JpaRepository<Autores, Long>{
	
}
