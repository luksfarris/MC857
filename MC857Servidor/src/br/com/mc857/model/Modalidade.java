package br.com.mc857.model;

import java.util.List;

public class Modalidade {
	private String nome;
	private List<Disciplina> disciplinas;
	private List<GrupoEletiva> grupos;
	
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
	public List<GrupoEletiva> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<GrupoEletiva> grupos) {
		this.grupos = grupos;
	}
}
