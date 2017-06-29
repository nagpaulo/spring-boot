package br.com.project.webservice.auth.services;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.project.webservice.auth.model.AnexoEmail;
import br.com.project.webservice.auth.model.Email;

@Service
public class EmailService implements Serializable{
	private static final long serialVersionUID = 8306874220102079947L;

	@Autowired
	JavaMailSender mailSender;

	/**
	 * MailException sera lancado quando ouver algum problema no envio do email
	 * MailParseException sera lancado quando ouver algum erro na montagem do
	 * MimeMessageHelper ( cabecalho do email ) MailParseException extende de
	 * MailExeption, voce pode tratar apenas um caso queira.
	 * 
	 * @param email
	 * @throws MailException
	 * @throws MailParseException
	 */
	public void enviar(Email email) throws MailException, MailParseException {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(email.getRemetente());
			helper.setSubject(email.getAssunto());
			helper.setTo(email.getDestinos().toArray(new String[email.getDestinos().size()]));
			if (email.isUsingTemplate()) {
				helper.setText(getMensagemByTemplate(email), true);
			} else {
				helper.setText(email.getMensagem(), email.isHtml());
			}
			for (AnexoEmail anexo : email.getAnexos()) {
				if (anexo.isFileSource()) {
					helper.addAttachment(anexo.getNome(), anexo.getCaminhoArquivo().toFile());
				}
				if (anexo.isByteDataSource()) {
					helper.addAttachment(anexo.getNome(), anexo.getDataSource());
				}
			}
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}

	private String getMensagemByTemplate(Email email) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		VelocityContext context = new VelocityContext();
		Map<String, String> parametros = email.getParametros();
		for (Map.Entry<String, String> parametro : parametros.entrySet()) {
			context.put(parametro.getKey(), parametro.getValue());
		}
		Template template = ve.getTemplate("email_template/" + email.getTemplate(), "UTF-8");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
}
