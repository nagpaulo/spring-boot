package br.com.project.webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.webservice.entity.Autores;
import br.com.project.webservice.entity.vo.AutoresVO;
import br.com.project.webservice.services.AutorService;

@RestController
public class AutorController {
	@Autowired
	private AutorService service;
	
	@RequestMapping(value = "/autores", method = RequestMethod.GET, produces = "application/json")
    public List<Autores> findAutoresAll() {
		List<Autores> autores = service.autoresAll();
        return autores;
    }
	
	@RequestMapping(value = "/autores", method = RequestMethod.POST)
	public ResponseEntity<List<Autores>> save(@RequestBody Autores post) {
		System.out.println(post.getNome());
		ResponseEntity<List<Autores>> responseEntity = null;		
		if(post.getNome() != null || post.getNome() != ""){
			service.save(post);
			List<Autores> autores = service.autoresAll();
			responseEntity = new ResponseEntity<List<Autores>>(autores, HttpStatus.OK);
		}else{
			responseEntity = new ResponseEntity<List<Autores>>(HttpStatus.BAD_REQUEST);
		}
		
	    return responseEntity;
	}
	
	@RequestMapping(value = "/autoresTest", method = RequestMethod.GET, produces = "application/json")
    public List<AutoresVO> test() {
		List<AutoresVO> autores = new ArrayList<>();

		AutoresVO autor = new AutoresVO();		
		autor.setId(1);
		autor.setNome("Paulo");
		autor.setEmail("nagpaulo@gmail.com");
		autores.add(autor);
		
		AutoresVO autor1 = new AutoresVO();
		autor1.setId(2);
		autor1.setNome("Tiago Hernandes");
		autor1.setEmail("tiago.hernandes@gmail.com");
		autores.add(autor1);
		
		AutoresVO autor2 = new AutoresVO();
		autor2.setId(3);
		autor2.setNome("Nagila Freitas");
		autor2.setEmail("nagila.pr@hotmail.com");
		autores.add(autor2);
		
        return autores;
    }
	
}

