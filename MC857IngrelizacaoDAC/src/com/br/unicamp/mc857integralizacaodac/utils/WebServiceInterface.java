package com.br.unicamp.mc857integralizacaodac.utils;

import java.util.List;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;

public class WebServiceInterface {
	
	private Boolean validarIntegralizacao(List<Atribuicao> atribuicoes, String ra) {
		// TODO: implementar
		return true;
	}
	
	private Catalogo requisitarCatalogo(String ra) {
		Catalogo cat = new Catalogo();
		// TODO: implementar
		return cat;
	}
	
	private Historico requisitarHistorico(String ra) {
		Historico hist = new Historico();
		// TODO: implementar
		return hist;
	}
	
}
