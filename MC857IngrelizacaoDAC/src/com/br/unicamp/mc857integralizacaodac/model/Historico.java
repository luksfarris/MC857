package com.br.unicamp.mc857integralizacaodac.model;

import java.util.List;

public class Historico {
	private Integer ra;
	private Integer curso;
	private String nome;
	private List<Disciplina> disciplinas;
	private String modalidade;
	
	public Integer getRa() {
		return ra;
	}
	public void setRa(Integer ra) {
		this.ra = ra;
	}
	public Integer getCurso() {
		return curso;
	}
	public void setCurso(Integer curso) {
		this.curso = curso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
}
