package br.com.project.webservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.project.webservice.auth.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByLogin(String login);
}
