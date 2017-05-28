package br.com.project.webservice.auth.services;

import br.com.project.webservice.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
