package br.com.mc857.model;

import java.util.List;

public class Catalogo {
	private Integer codigo;
	private String nome;
	private List<Disciplina> disciplinas;
	private List<GrupoEletiva> grupos;
	private List<Modalidade> modalidades;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public List<GrupoEletiva> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<GrupoEletiva> grupos) {
		this.grupos = grupos;
	}
	public List<Modalidade> getModalidades() {
		return modalidades;
	}
	public void setModalidades(List<Modalidade> modalidades) {
		this.modalidades = modalidades;
	}
}
