package br.com.mc857.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class BuscarHistoricoController  extends ActionSupport{
	
	private String ra;
	
	  private InputStream inputStream;
	  public InputStream getInputStream() {
	    return inputStream;
	   }
	
	@Action(value = "buscaHistorico", results = {
			@Result( type="stream", name=SUCCESS)}
	)
	public String buscaHistorico(){
	    inputStream = new StringBufferInputStream(
	    	      "aqui vem a treta pro ra "+ ra);
		return SUCCESS;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

}
