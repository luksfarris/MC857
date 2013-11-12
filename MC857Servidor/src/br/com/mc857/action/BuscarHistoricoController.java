package br.com.mc857.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.mc857.util.HttpConnector;

import com.opensymphony.xwork2.ActionSupport;

public class BuscarHistoricoController  extends ActionSupport{

	private static final long serialVersionUID = -3644436651169638063L;

	private final String URL_BASE = "http://localhost:81//";

	private String ra;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
     * Pega o xml do Historico no servidor de dados dado o RA do aluno
     * @param ra Ra do aluno
     * @return xml do Historico.
     */
	@Action(value = "buscaHistorico", results = {
			@Result( type="stream", name=SUCCESS)}
			)
	public String buscaHistorico() {
		String url = URL_BASE + "?serv=h&ra="+ra;
		StringBuffer response;

		response = HttpConnector.connect(url);

		inputStream = new ByteArrayInputStream(response.toString().getBytes());
		return SUCCESS;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

}
