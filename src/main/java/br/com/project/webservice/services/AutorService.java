package br.com.project.webservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.project.webservice.entity.Autores;
import br.com.project.webservice.repository.AutorRepository;

@Service
public class AutorService {
	@Autowired
	private AutorRepository repository;
	
	public List<Autores> autoresAll(){
		return repository.findAll();
	}
	
	public Autores save(Autores autores){
		return repository.save(autores);
	}
}
