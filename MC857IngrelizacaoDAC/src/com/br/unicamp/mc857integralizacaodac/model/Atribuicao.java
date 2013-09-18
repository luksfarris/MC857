package com.br.unicamp.mc857integralizacaodac.model;

public class Atribuicao extends Catalogo {
	/** diz se a atribuicao corresponde a uma integralizacao completa */
	private boolean integral;

	public boolean isIntegral() {
		return integral;
	}

	public void setIntegral(boolean integral) {
		this.integral = integral;
	}
}
