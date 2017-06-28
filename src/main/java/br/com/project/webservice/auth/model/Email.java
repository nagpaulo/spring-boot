package br.com.project.webservice.auth.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Email implements Serializable {

	private static final long serialVersionUID = 5366713503811391765L;

	private String remetente;
	private List<String> destinos = new ArrayList<>();
	private List<AnexoEmail> anexos = new ArrayList<>();
	private Map<String, String> parametros = new HashMap<>();
	private String template;

	private String assunto;
	private String mensagem;
	private boolean isHtml = true;
	private boolean isUsingTemplate = false;

	public Email() {
	}

	public void addDestinatario(String email) {
		destinos.add(email);
	}

	public void addDestinatarios(List<String> emails) {
		for (String email : emails) {
			destinos.add(email);
		}
	}

	public void addAnexo(AnexoEmail anexoEmail) {
		anexos.add(anexoEmail);
	}

	public void addAnexo(String nome, Path caminhoArquivo) {
		anexos.add(new AnexoEmail(nome, caminhoArquivo));
	}

	public boolean possuiAnexo() {
		return !anexos.isEmpty();
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public List<String> getDestinos() {
		return destinos;
	}

	public void setDestinos(List<String> destinos) {
		this.destinos = destinos;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public boolean isUsingTemplate() {
		return isUsingTemplate;
	}

	public void setUsingTemplate(boolean isUsingTemplate) {
		this.isUsingTemplate = isUsingTemplate;
	}

	public List<AnexoEmail> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoEmail> anexos) {
		this.anexos = anexos;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
