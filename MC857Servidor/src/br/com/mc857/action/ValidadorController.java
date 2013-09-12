package br.com.mc857.action;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.mc857.model.Atribuicao;

import com.opensymphony.xwork2.ActionSupport;

public class ValidadorController extends ActionSupport{
	
	private String ra;
	private String atribuicaoString ;
	
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	
	@Action(value = "buscaCatalogo", results = {
			@Result( type="stream", name=SUCCESS)}
	)
	public String valida(){
		
		Atribuicao attr = new Atribuicao();
		//TODO: Tem que gerar a atribuicao a partir do dado vindo do request
		validaIntegralizacao(attr, ra);
		
		return SUCCESS;
		
	}
	
	private Boolean validaIntegralizacao(Atribuicao atribuicao, String ra){
		return false;
	}

}
