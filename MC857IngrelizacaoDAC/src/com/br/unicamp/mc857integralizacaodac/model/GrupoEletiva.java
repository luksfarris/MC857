package com.br.unicamp.mc857integralizacaodac.model;

import java.util.List;

public class GrupoEletiva{
	private Integer credito;
	private Integer creditosFeitos = 0;
	private List<Disciplina> disciplinas;
	private Integer id;
	
	public Integer getCredito() {
		return credito;
	}
	public void setCredito(Integer credito) {
		this.credito = credito;
	}
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public Integer getCreditosFeitos() {
		return creditosFeitos;
	}
	public void setCreditosFeitos(Integer creditosFeitos) {
		this.creditosFeitos = creditosFeitos;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
