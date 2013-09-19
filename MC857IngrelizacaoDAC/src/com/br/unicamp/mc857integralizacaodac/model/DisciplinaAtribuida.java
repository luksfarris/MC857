package com.br.unicamp.mc857integralizacaodac.model;

public class DisciplinaAtribuida extends Disciplina {

	private boolean obrigatoriaDoCurso;
	private boolean obrigatoriaDaModalidade;
	private boolean eletoriaDaModalidade;
	private boolean eletoriaDoCurso;
	private boolean eletivaDaModalidade;
	private boolean eletivaDoCurso;
	
	
	public boolean isObrigatoriaDoCurso() {
		return obrigatoriaDoCurso;
	}
	public void setObrigatoriaDoCurso(boolean obrigatoriaDoCurso) {
		this.obrigatoriaDoCurso = obrigatoriaDoCurso;
	}
	public boolean isObrigatoriaDaModalidade() {
		return obrigatoriaDaModalidade;
	}
	public void setObrigatoriaDaModalidade(boolean obrigatoriaDaModalidade) {
		this.obrigatoriaDaModalidade = obrigatoriaDaModalidade;
	}
	public boolean isEletoriaDaModalidade() {
		return eletoriaDaModalidade;
	}
	public void setEletoriaDaModalidade(boolean eletoriaDaModalidade) {
		this.eletoriaDaModalidade = eletoriaDaModalidade;
	}
	public boolean isEletoriaDoCurso() {
		return eletoriaDoCurso;
	}
	public void setEletoriaDoCurso(boolean eletoriaDoCurso) {
		this.eletoriaDoCurso = eletoriaDoCurso;
	}
	public boolean isEletivaDaModalidade() {
		return eletivaDaModalidade;
	}
	public void setEletivaDaModalidade(boolean eletivaDaModalidade) {
		this.eletivaDaModalidade = eletivaDaModalidade;
	}
	public boolean isEletivaDoCurso() {
		return eletivaDoCurso;
	}
	public void setEletivaDoCurso(boolean eletivaDoCurso) {
		this.eletivaDoCurso = eletivaDoCurso;
	}
}
