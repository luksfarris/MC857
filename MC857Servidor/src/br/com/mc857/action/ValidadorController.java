package br.com.mc857.action;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class ValidadorController extends ActionSupport{
	
	private String ra;
	
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	
	@Action(value = "buscaCatalogo", results = {
			@Result( type="stream", name=SUCCESS)}
	)
	public String valida(){
		
		return SUCCESS;
		
	}

}
