package br.com.mc857.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.mc857.util.HttpConnector;

import com.opensymphony.xwork2.ActionSupport;

public class BuscarCatalogoController extends ActionSupport{
	
	private static final long serialVersionUID = 829854663838076499L;

	private final String URL_BASE = "http://localhost:80//";
	
	private String cod;
	
	  private InputStream inputStream;
	  public InputStream getInputStream() {
	    return inputStream;
	   }
	
	@Action(value = "buscaCatalogo", results = {
			@Result( type="stream", name=SUCCESS)}
	)
	public String buscaCatalogo(){
		String url = URL_BASE + "?serv=c&cod="+cod;

		StringBuffer response;

		response = HttpConnector.connect(url);

		inputStream = new ByteArrayInputStream(response.toString().getBytes());
		return SUCCESS;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
