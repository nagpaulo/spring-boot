package br.com.project.webservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.project.webservice.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
