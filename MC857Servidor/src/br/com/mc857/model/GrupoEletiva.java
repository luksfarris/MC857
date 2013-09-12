package br.com.mc857.model;

import java.util.List;

public class GrupoEletiva {
	private Integer credito;
	private List<Disciplina> disciplinas;
	
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
}
