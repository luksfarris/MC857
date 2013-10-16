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
	
	@Override
	public String toString() {
		return this.sigla;
	}
	
	@Override 
	public boolean equals(Object obj) {
		boolean isEqual = obj instanceof Disciplina; 
		Disciplina objD = (Disciplina) obj; 
		if(isEqual){ 
			boolean mesmaDisciplina = objD.sigla.equals(this.sigla); 
			boolean mesmaSigla3Tracos = (objD.sigla.contains("---") || this.sigla.contains("---")) && (this.sigla.startsWith(objD.sigla.substring(0, 2))); 
			boolean mesmaSigla4Tracos =  (objD.sigla.contains("----") || this.sigla.contains("----")) && (this.sigla.startsWith(objD.sigla.substring(0, 1))); 
			boolean qualquerDisciplina = (objD.sigla.contains("-----") || this.sigla.contains("-----"));
			return mesmaDisciplina || mesmaSigla3Tracos || mesmaSigla4Tracos || qualquerDisciplina; 
		} 
		return false; 
	}
}
