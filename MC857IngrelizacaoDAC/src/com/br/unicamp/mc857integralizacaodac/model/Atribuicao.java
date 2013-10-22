package com.br.unicamp.mc857integralizacaodac.model;

public class Atribuicao extends Catalogo implements Cloneable {
	/** diz se a atribuicao corresponde a uma integralizacao completa */
	private boolean integral;

	public boolean isIntegral() {
		return integral;
	}

	public void setIntegral(boolean integral) {
		this.integral = integral;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
