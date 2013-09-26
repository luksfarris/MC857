package com.br.unicamp.mc857integralizacaodac.model;

public class Disciplina {
	private String sigla;
	private Integer credito;
	
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
	
	@Override public boolean equals(Object obj) {
		boolean isEqual = obj instanceof Disciplina; 
		Disciplina objD = (Disciplina) obj; 
		if(isEqual){ 
			boolean mesmaDisciplina = objD.sigla.equals(this.sigla); 
			boolean mesmaSigla = (objD.sigla.contains("---") || this.sigla.contains("---")) && (this.sigla.startsWith(objD.sigla.substring(0, 2))); 
			return mesmaDisciplina || mesmaSigla; 
		} 
		return false; 
	}
}
