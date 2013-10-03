package com.br.unicamp.mc857integralizacaodac.utils;

import java.util.ArrayList;
import java.util.List;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
import com.br.unicamp.mc857integralizacaodac.model.GrupoEletiva;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.model.Modalidade;

public class AppController {

	private Historico historico;
	private Catalogo catalogo;
	private String ra;
	private int curso;

	private List<Disciplina> eletivas;
	private List<GrupoEletiva> grupoDeEletivas;
	private Atribuicao atribuicao;

	public AppController(String ra, int curso){
		this.ra = ra;
		this.curso = curso;
	}

	public Boolean validarIntegralizacao(){
		/* TODO: Método que retorna se e valida a integralização, dado um RA e uma integralizacao
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
		WebServiceInterface web = new WebServiceInterface();
		this.setHistorico(web.requisitarHistorico(ra));
		this.setCatalogo(web.requisitarCatalogo(String.valueOf(curso)));

		assert (historico != null) : "historico inválido";
		assert (catalogo != null) : "catalogo inválido";
	}

	private boolean jaTemosSolucao(){
		for(GrupoEletiva grupo : grupoDeEletivas){
			if(grupo.getCreditosFeitos() < grupo.getCredito()){
				return false;
			}
		}
		return true;
	}

	private void associa(Disciplina disciplina, GrupoEletiva grupo){
		grupo.setCreditosFeitos(grupo.getCreditosFeitos()+disciplina.getCredito());

		boolean frag = false;
		//procura grupo nas eletivas do curso
		for(GrupoEletiva ge : atribuicao.getGrupos()){
			if(ge.getId() == grupo.getId()){
				if(ge.getDisciplinas() == null) {
					ge.setDisciplinas(new ArrayList<Disciplina>());
				}
				ge.getDisciplinas().add(disciplina);
				frag = true;
				break;
			}
		}
		//procura na modalidade
		if(!frag){
			for(GrupoEletiva ge : atribuicao.getModalidades().get(0).getGrupos()){
				if(ge.getId() == grupo.getId()){
					ge.getDisciplinas().add(disciplina);
					break;
				}
			}
		}
	}

	private void dissocia(Disciplina disciplina, GrupoEletiva grupo){
		grupo.setCreditosFeitos(grupo.getCreditosFeitos()-disciplina.getCredito());

		boolean frag = false;
		//procura grupo nas eletivas do curso
		for(GrupoEletiva ge : atribuicao.getGrupos()){
			if(ge.getId() == grupo.getId()){
				//TODO: testar essa bagaca.. se der pau, pode ser aqui nao estar dando certo o remove :)
				Disciplina d_aux = null;
				for(Disciplina d : ge.getDisciplinas()){
					if(d.getSigla().equalsIgnoreCase(disciplina.getSigla())){
						d_aux = d;
						break;
					}
				}
				ge.getDisciplinas().remove(d_aux);
				frag = true;
				break;
			}
		}
		//procura na modalidade
		if(!frag){
			for(GrupoEletiva ge : atribuicao.getModalidades().get(0).getGrupos()){
				if(ge.getId() == grupo.getId()){
					Disciplina d_aux = null;
					for(Disciplina d : ge.getDisciplinas()){
						if(d.getSigla().equalsIgnoreCase(disciplina.getSigla())){
							d_aux = d;
							break;
						}
					}
					ge.getDisciplinas().remove(d_aux);
					frag = true;
					break;
				}
			}
		}
	}

	private boolean tenta(Integer i){
		if(jaTemosSolucao()){
			return true;
		}
		if(i >= eletivas.size()){
			return false;
		}
		boolean achou = false;
		Disciplina disciplinaAtual = eletivas.get(i);
		for(GrupoEletiva grupo : grupoDeEletivas){
			if(grupo.getDisciplinas().contains(disciplinaAtual) && grupo.getCreditosFeitos() < grupo.getCredito()){
				if(!achou){
					associa(disciplinaAtual, grupo);
					achou = tenta(i+1);
					dissocia(disciplinaAtual, grupo);
				}
			}
		}
		if(!achou){
			achou = tenta(i+1);
		}
		return achou;
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

		atribuicao = new Atribuicao();
		atribuicao.setCodigo(catalogo.getCodigo());
		atribuicao.setNome(catalogo.getNome());

		if(catalogo.getModalidades() != null) {
			Modalidade modalidade = new Modalidade();
			modalidade.setNome(historico.getModalidade());
			atribuicao.getModalidades().add(modalidade);
		}

		int contador = 1;
		if(catalogo.getGrupos() != null) {
			for(GrupoEletiva gr : catalogo.getGrupos()){
				gr.setId(contador++);
				GrupoEletiva grupo = new GrupoEletiva();
				grupo.setCredito(gr.getCredito());
				grupo.setId(gr.getId());

				//grupo para adicionar na atribuicao
				GrupoEletiva gAtr = new GrupoEletiva();
				gAtr.setId(gr.getId());
				gAtr.setCredito(gr.getCredito());

				grupo.setDisciplinas(gr.getDisciplinas());

				if(grupoDeEletivas == null) {
					grupoDeEletivas = new ArrayList<GrupoEletiva>();
				}

				grupoDeEletivas.add(grupo);
				if(atribuicao.getGrupos() == null){
					atribuicao.setGrupos(new ArrayList<GrupoEletiva>());
				}
				atribuicao.getGrupos().add(gAtr);
			}
		}
		if(catalogo.getModalidades() != null) {
			for(Modalidade mod : catalogo.getModalidades()){
				if(mod.getNome().equals(historico.getModalidade())){
					for(GrupoEletiva gr : mod.getGrupos()){
						gr.setId(contador++);
						GrupoEletiva grupo = new GrupoEletiva();
						grupo.setCredito(gr.getCredito());


						//grupo para adicionar na atribuicao
						GrupoEletiva gAtr = new GrupoEletiva();
						gAtr.setId(gr.getId());
						gAtr.setCredito(gr.getCredito());

						grupo.setDisciplinas(gr.getDisciplinas());

						grupoDeEletivas.add(grupo);
						if(atribuicao.getModalidades().get(0).getGrupos() == null){
							atribuicao.getModalidades().get(0).setGrupos(new ArrayList<GrupoEletiva>());
						}
						atribuicao.getModalidades().get(0).getGrupos().add(gAtr);
					}
				}
			}
		}
		//agora tenho os grupos de eletivas que preciso preencher na lista "grupoDeEletivas"


		// classifica as disciplinas
		eletivas = classificar();
		//as obrigatorias estao na atribuicao

		boolean formou = tenta(0);
		// preenche atribuicao


		return atribuicao;
	}

	/**
	 * Classifica uma disciplina de acordo com seu tipo
	 * @param disciplina a disciplina a ser classificada
	 * @param atribuicao
	 */
	public ArrayList<Disciplina> classificar() {

		// cria lista de eletivas
		ArrayList<Disciplina> eletivas = new ArrayList<Disciplina>();

		// pra cada disciplina do catalogo
		for (Disciplina disciplina : historico.getDisciplinas()) {
			// cria uma atribuicao da disciplina
			Disciplina atribuida = new Disciplina();
			atribuida.setSigla(disciplina.getSigla());
			atribuida.setCredito(disciplina.getCredito());
			// checa se ela eh obrigatoria do curso
			if (catalogo.getDisciplinas().contains(disciplina)) {
				//se sim, adiciona na atribuicao
				if(atribuicao.getDisciplinas() == null) {
					atribuicao.setDisciplinas(new ArrayList<Disciplina>());
				}
				atribuicao.getDisciplinas().add(atribuida);
			}
			// checa se eh obrigatoria da modalidade
			else if (isObrigatoriaModalidade(disciplina)) {
				// se sim, adiciona na atribuicao
				if(atribuicao.getModalidades() == null) {
					atribuicao.setModalidades(new ArrayList<Modalidade>());
					atribuicao.getModalidades().add(new Modalidade());
				}
				atribuicao.getModalidades().get(0).getDisciplinas().add(atribuida);
			} else {
				// adiciona na lista de eletivas
				System.out.println(atribuida.getSigla() + " " + atribuida.getCredito().toString());
				eletivas.add(atribuida);
			}
		}
		return eletivas;
	}


	private boolean isObrigatoriaModalidade (Disciplina disciplina) {
		boolean isObrigatoria = false;
		if(catalogo.getModalidades() != null) {
			for (Modalidade modalidade : catalogo.getModalidades()) {
				if (historico.getModalidade().equalsIgnoreCase(modalidade.getNome())) {
					if (modalidade.getDisciplinas().contains(disciplina)) {
						isObrigatoria= true;
					}
				}
			}
		}
		return isObrigatoria;
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

}
