package com.br.unicamp.mc857integralizacaodac.utils;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;

public class AppController {
	
	private Historico historico;
	private Catalogo catalogo;
	private String ra;
	private int curso;
	
	public AppController(String ra, int curso){
		this.ra = ra;
		this.curso = curso;
	}
		
	public Boolean validarIntegralizacao(){
		/* TODO: �� M�todo que retorna se � v�lida a integraliza��o, dado um RA e uma integraliza��o
		require
		o RA e a integraliza��o devem ser v�lidos
		ensure
		a view ser� notificada com a resposta booleana do WebServiceM�todo que retorna se � v�lida a integraliza��o, dado um RA e uma integraliza��o*/
		return true;
	}
	
	public void recuperarInformacoesDoServico(){
		
		/* TODO: �� M�todo que preenche os atributos Catalogo e Historico vindo do WebService
		require
		o RA e o curso devem ser v�lidos
		ensure
		os atributos Catalogo e Historico foram preenchidos (n�o s�o nulos)*/
		assert (!ra.isEmpty()) : "ra n�o pode ser nulo";
		assert (curso > 0) : "curso inv�lido";
		
		String xmlHist=""; //TODO request trazendo  historico
		String xmlCat=""; //TODO request trazendo catalogo
		this.setHistorico(Parser.parseHistorico(xmlHist));
		this.setCatalogo(Parser.parseCatalogo(xmlCat));
		
		assert (historico != null) : "historico inv�lido";
		assert (catalogo != null) : "catalogo inv�lido";
	}
	
	public Atribuicao gerarIntegraliza��o(Historico historico, Catalogo catalogo) {
		/* TODO: �� M�todo respons�vel por gerar a integraliza��o a partir dos dados obtidos nos webservices
		require
		o hist�rico e o cat�logo devem ser v�lidos
		ensure
		Todas as disciplinas do historico foram alocadas*/
		Atribuicao atribuicao = new Atribuicao();
		return atribuicao;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}
	
	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
	
}
