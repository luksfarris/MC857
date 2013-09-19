package com.br.unicamp.mc857integralizacaodac.utils;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.model.Modalidade;

public class AppController {
	
	private Historico historico;
	private Catalogo catalogo;
	private String ra;
	private String modalidade;
	private int curso;
	
	public AppController(String ra, int curso, String modalidade){
		this.ra = ra;
		this.curso = curso;
		this.modalidade = modalidade;
	}
		
	public Boolean validarIntegralizacao(){
		/* TODO: ­­ Método que retorna se é válida a integralização, dado um RA e uma integralização
		require
		o RA e a integralizacao devem ser válidos
		ensure
		a view será notificada com a resposta booleana do WebServiceMétodo que retorna se é válida a integralização, dado um RA e uma integralização*/
		return true;
	}
	
	public void recuperarInformacoesDoServico(){
		
		/* TODO: ­­ Método que preenche os atributos Catalogo e Historico vindo do WebService
		require
		o RA e o curso devem ser válidos
		ensure
		os atributos Catalogo e Historico foram preenchidos (não são nulos)*/
		assert (ra.length()>0) : "ra não pode ser nulo";
		assert (curso > 0) : "curso inválido";
		
		String xmlHist=""; //TODO request trazendo  historico
		String xmlCat=""; //TODO request trazendo catalogo
		this.setHistorico(Parser.parseHistorico(xmlHist));
		this.setCatalogo(Parser.parseCatalogo(xmlCat));
		
		assert (historico != null) : "historico inválido";
		assert (catalogo != null) : "catalogo inválido";
	}
	
	/**
	 * Gera a integralizacao do usuario, dados
	 * @param historico um historico do aluno
	 * @param catalogo o catalogo do curso dele
	 * @return uma atribuicao de disciplinas
	 */
	public Atribuicao gerarIntegralizacao(Historico historico, Catalogo catalogo) {
		/* TODO: Metodo responsavel por gerar a integralizacao a partir dos dados obtidos nos webservices
		require
		o historico e o catalogo devem ser validos
		ensure
		Todas as disciplinas do historico foram alocadas*/
		assert (null != catalogo) : "historico não pode ser nulo";
		assert (null != historico) : "catalogo não pode ser nulo";
		Atribuicao atribuicao = new Atribuicao();
		// primeiramente checa se ele fez todas as obrigatórias
		for (Disciplina disciplina : historico.getDisciplinas()) {
			// se ela for obrigatoria, adiciona na atribuicao
			if (isDisciplinaObrigatoria(disciplina, catalogo)) {
				atribuicao.getDisciplinas().add(disciplina);
			}
		}
		
		
		// checa se ele fez todas as obrigatorias
		if (catalogo.getDisciplinas().size() != atribuicao.getDisciplinas().size()) {
			// nao fez todas as obrigatorias
			atribuicao.setIntegral(false);
		}
		
		
		return atribuicao;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}
	
	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
	
	
	
	/**
	 * Checa se uma disciplina e obrigatoria
	 * @param disciplina disciplina a ser checada
	 * @param catalogo catologo para comparar
	 * @return se a disciplina e obrigatoria no catalogo
	 */
	private boolean isDisciplinaObrigatoria(Disciplina disciplina, Catalogo catalogo) {
		boolean obrigatoria = false;
		for (Disciplina disciplinaCorrente : catalogo.getDisciplinas()) {
			if (disciplina.getSigla().equalsIgnoreCase(disciplinaCorrente.getSigla())) {
				obrigatoria = true;
				break;
			}
		}
		
		for (Modalidade modalidade : catalogo.getModalidades()) {
			if (this.modalidade.equalsIgnoreCase(modalidade.getNome())) {
				for (Disciplina disciplinaCorrente : modalidade.getDisciplinas()) {
					if (disciplina.getSigla().equalsIgnoreCase(disciplinaCorrente.getSigla())) {
						obrigatoria = true;
						break;
					}
				}
				if (obrigatoria = true) {
					break;
				}
			}
		}
		
		return obrigatoria;
	}
}
