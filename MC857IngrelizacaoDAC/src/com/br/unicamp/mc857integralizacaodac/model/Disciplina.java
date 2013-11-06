package com.br.unicamp.mc857integralizacaodac.model;

public class Disciplina {
	private String sigla;
	private Integer credito;

	private Integer creditosUsados;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getCredito() {
		return credito;
	}

	public void setCredito(Integer credito) {
		this.credito = credito;
	}

	public Integer getCreditosUsados() {
		return creditosUsados;
	}

	public void setCreditosUsados(Integer creditosUsados) {
		this.creditosUsados = creditosUsados;
	}

	@Override
	public String toString() {
		return this.sigla;
	}

	@Override
	public boolean equals(Object obj) {
		Disciplina objD = (Disciplina) obj;
		String sigla1 = objD.getSigla();
		String sigla2 = getSigla();
		for (int i = 0; i < sigla1.length(); i++) {
			char char1 = sigla1.charAt(i);
			char char2 = sigla2.charAt(i);
			if (!(char1==char2 || char1=='-' || char2=='-')){
				return false;
			}
		}
		return true;
	}
}
