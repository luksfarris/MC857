package com.br.unicamp.mc857integralizacaodac.model;

public class DisciplinaAtribuida extends Disciplina {

	public static final int OBRIGATORIA_CURSO = 1;
	public static final int OBRIGATORIA_MODALIDADE = 2;
	public static final int ELETIVA = 3;
	
	private int tipoAtribuicao;

	public int getTipoAtribuicao() {
		return tipoAtribuicao;
	}

	public void setTipoAtribuicao(int tipoAtribuicao) {
		this.tipoAtribuicao = tipoAtribuicao;
	}
	
}
