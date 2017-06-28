package br.com.project.webservice.auth.model;

import java.io.Serializable;
import java.nio.file.Path;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

public class AnexoEmail implements Serializable {
	
	private static final long serialVersionUID = 3179086523664482666L;
	
	private String nome;
	private Path caminhoArquivo;
	private DataSource dataSource;
	
	public AnexoEmail() {}
	
	public AnexoEmail(String nome, Path caminhoArquivo) {
		this.nome = nome;
		this.caminhoArquivo = caminhoArquivo;
	}
	
	/*public AnexoEmail(String nome,  byte[] bytes , MimeType mime ) {
		this( nome , bytes , mime.getValue() );
	}*/
	
	public AnexoEmail(String nome, byte[] bytes , String mimeType) {
		this.nome = nome;
		this.dataSource = new ByteArrayDataSource( bytes , mimeType);
	}
	
	public boolean isFileSource(){
		return caminhoArquivo != null;
	}
	
	public boolean isByteDataSource(){
		return dataSource != null;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Path getCaminhoArquivo() {
		return caminhoArquivo;
	}
	public void setCaminhoArquivo(Path caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	
}

