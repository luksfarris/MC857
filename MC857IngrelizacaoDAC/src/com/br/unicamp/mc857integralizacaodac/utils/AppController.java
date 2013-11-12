package com.br.unicamp.mc857integralizacaodac.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.util.Log;

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
	/** Melhor tentativa de integralizacao (testado pelo total de creditos corrente) */
	private int totalCreditosMelhor;
	/** total de creditos na tentiva corrente.*/
	private int totalCreditosCorrente;
	/** lista de disciplinas eletivas do historico. */
	private List<Disciplina> eletivas;
	private List<GrupoEletiva> grupoDeEletivas;
	public Atribuicao atribuicao;
	/** melhor atribuicao gerada pelo algoritmo*/
	public Atribuicao melhorAtribuicao;
	/** tabela que guarda as tentativas de integralizacao.*/
	private  HashMap<String, Boolean> tabelaDinamica = new HashMap<String, Boolean>();

	/**
	 * Construtor.
	 * @param ra ra do aluno.
	 * @param curso curso do aluno.
	 */
	public AppController(String ra, int curso){
		this.ra = ra;
		this.curso = curso;
	}

	/**
	 * Chama o serviço de integralização.
	 * @return {@link Boolean} se o aluno se formou.
	 */
	public Boolean validarIntegralizacao(){
		WebServiceInterface web = new WebServiceInterface();
		return web.validarIntegralizacao(atribuicao, ra);
	}

	/**
	 * Recupera o historico e o catalogo do servico.
	 */
	public void recuperarInformacoesDoServico(){
		WebServiceInterface web = new WebServiceInterface();
		this.setHistorico(web.requisitarHistorico(ra));
		this.setCatalogo(web.requisitarCatalogo(String.valueOf(curso)));
		assert (historico != null) : "historico inválido";
		assert (catalogo != null) : "catalogo inválido";
	}

	/**
	 * Checa se o algoritmo ja formou o aluno.
	 * @return {@link boolean} se o aluno se formou.
	 */
	private boolean jaTemosSolucao(){
		for(GrupoEletiva grupo : grupoDeEletivas){
			if(grupo.getCreditosFeitos() < grupo.getCredito()){
				return false;
			}
		}
		return true;
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
				if(atribuicao.getModalidades().get(0).getDisciplinas() == null){
					atribuicao.getModalidades().get(0).setDisciplinas(new ArrayList<Disciplina>());
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


	/**
	 * Checa se uma disciplina e obrigatoria da modalidade.
	 * @param disciplina a ser testada.
	 * @return {@link boolean} se a disciplina e obrigatoria.
	 */
	private boolean isObrigatoriaModalidade (Disciplina disciplina) {
		boolean isObrigatoria = false;
		if(catalogo.getModalidades() != null) {
			for (Modalidade modalidade : catalogo.getModalidades()) {
				if(modalidade.getDisciplinas() != null && modalidade.getDisciplinas().size() > 0){
					if(modalidade.getNome().toLowerCase(Locale.getDefault()).contains(historico.getModalidade().toLowerCase(Locale.getDefault()))) {
						if (modalidade.getDisciplinas().contains(disciplina)) {
							isObrigatoria= true;
						}
					}
				}
			}
		}
		return isObrigatoria;
	}
	
	
	/**
	 * Associa uma disciplina a um grupo.
	 * @param disciplina eletiva a ser associada.
	 * @param grupo grupo alvo.
	 */
	private void associa(Disciplina disciplina, GrupoEletiva grupo){	
		if(grupo.getCredito()- grupo.getCreditosFeitos() < disciplina.getCredito()) {
			disciplina.setCreditosUsados(grupo.getCreditosFeitos() - grupo.getCredito());
			totalCreditosCorrente -= grupo.getCredito() -grupo.getCreditosFeitos() ;
		} else {
			disciplina.setCreditosUsados(disciplina.getCredito());
			totalCreditosCorrente -= disciplina.getCredito();
		}
		// aumenta o numero de creditos feitos no grupo.
		grupo.setCreditosFeitos(grupo.getCreditosFeitos()+disciplina.getCredito());
		if (atribuicao == null) {
			Log.e("log", "attr nula", new NullPointerException());
		} else if (atribuicao.getGrupos() == null) {
			Log.e("log", "attr nula", new NullPointerException());
		}
		
		boolean flag = false;
		//procura grupo nas eletivas do curso
		for(GrupoEletiva ge : atribuicao.getGrupos()){
			if(ge.getId() == grupo.getId()){
				if(ge.getDisciplinas() == null) {
					ge.setDisciplinas(new ArrayList<Disciplina>());
				}
				ge.getDisciplinas().add(disciplina);
				flag = true;
				break;
			}
		}
		//procura na modalidade
		if(!flag){
			for(GrupoEletiva ge : atribuicao.getModalidades().get(0).getGrupos()){
				if(ge.getId() == grupo.getId()){
					ge.getDisciplinas().add(disciplina);
					break;
				}
			}
		}
		// guarda a tentativa se necessario.
		if(totalCreditosCorrente < totalCreditosMelhor) {
			try {
				melhorAtribuicao = (Atribuicao)atribuicao.clone();
				totalCreditosMelhor = totalCreditosCorrente;
			} catch (CloneNotSupportedException e) {
				Log.e("log", "excecao encontrada no metodo clone.", e);
			}
		}
	}

	/**
	 * Dissocia uma disciplina de um grupo.
	 * @param disciplina eletiva a ser dissociada.
	 * @param grupo grupo alvo.
	 */
	private void dissocia(Disciplina disciplina, GrupoEletiva grupo){
		totalCreditosCorrente += disciplina.getCreditosUsados();
		grupo.setCreditosFeitos(grupo.getCreditosFeitos()-disciplina.getCredito());
		boolean frag = false;
		//procura grupo nas eletivas do curso
		for(GrupoEletiva ge : atribuicao.getGrupos()){
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

	/**
	 * Gera um hash da posicao corrente dos grupos de eletivas.
	 * @return o hash gerado.
	 */
	public String currentHash () {
		String hash = "";
		for (int j=0; j<grupoDeEletivas.size(); j++){
			hash = hash+grupoDeEletivas.get(j).getCreditosFeitos().toString()+",";
		}
		return hash;
	}
	
	
	/**
	 * Tenta uma associacao para um determinado grupo de eletivas, e um determinado indice de eletiva.
	 * @param i indice da eletiva corrente.
	 * @return Se a integralizacao deu certo.
	 */
	private Boolean tenta(Integer i){
		// checa se temos solucao.
		if(jaTemosSolucao()){
			return true;
		}
		// checa se chegou no final.
		if(i >= eletivas.size()){
			return false;
		}
		Boolean achou = false;
		String hash = currentHash();
		// testa se ja vimos esse caso.
		if(tabelaDinamica.containsKey(hash)) {
			return tabelaDinamica.get(hash);
		}
		// testa se e possivel integralizar.
		if (maisEletivasQueCreditos(i)) {
			tabelaDinamica.put(hash, achou);
			return achou;
		}
		
		
		Disciplina disciplinaAtual = eletivas.get(i);
		// recursao que testa todos os grupos.
		for(GrupoEletiva grupo : grupoDeEletivas){
			if(grupo.getDisciplinas().contains(disciplinaAtual) && grupo.getCreditosFeitos() < grupo.getCredito()){
				if(!achou){
					associa(disciplinaAtual, grupo);
					achou = tenta(i+1);
					if(!achou){
						dissocia(disciplinaAtual, grupo);
					}else {
						break;
					}
				}
			}
		}		
		//guarda o resultado.
		tabelaDinamica.put(hash, achou);
		
		if(!achou){
			achou = tenta(i+1);
		}
		return achou;
	}

	/**
	 * Testa se tem mais eletivas que creditos.
	 * @param i indice corrente.
	 * @return se tem mais eletivas nao feitas que creditos faltantes.
	 */
	public boolean maisEletivasQueCreditos (int i) {
		int creditosSobrando = 0;
		for (int j = i; j< eletivas.size(); j++){
			creditosSobrando += eletivas.get(j).getCredito();
		}
		int creditosFaltando = 0;
		for (int k=0; k<grupoDeEletivas.size(); k++) {
			creditosFaltando += grupoDeEletivas.get(k).getCredito() - grupoDeEletivas.get(k).getCreditosFeitos();
		}
		return (creditosFaltando>creditosSobrando);
	}
	
	/**
	 * Gera a integralizacao do usuario, dados
	 * @param historico um historico do aluno
	 * @param catalogo o catalogo do curso dele
	 * @return uma atribuicao de disciplinas
	 */
	public Atribuicao gerarIntegralizacao(Historico historico, Catalogo catalogo) {
		atribuicao = new Atribuicao();
		atribuicao.setCodigo(catalogo.getCodigo());
		atribuicao.setNome(catalogo.getNome());

		if(catalogo.getModalidades() != null) {
			Modalidade modalidade = new Modalidade();
			modalidade.setNome(historico.getModalidade());
			if(atribuicao.getModalidades() == null){
				atribuicao.setModalidades(new ArrayList<Modalidade>());
			}
			atribuicao.getModalidades().add(modalidade);
		}

		int contador = 1;
		if(catalogo.getGrupos() != null) {
			for(GrupoEletiva gr : catalogo.getGrupos()){
				totalCreditosMelhor += gr.getCredito();
				
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
		else{
			grupoDeEletivas = new ArrayList<GrupoEletiva>();
			atribuicao.setGrupos(new ArrayList<GrupoEletiva>());
		}
		if(catalogo.getModalidades() != null) {
			for(Modalidade mod : catalogo.getModalidades()){
				if(mod.getNome().toLowerCase().contains(historico.getModalidade().toLowerCase())){
					if (mod.getGrupos() == null) continue;
					for(GrupoEletiva gr : mod.getGrupos()){
						totalCreditosMelhor += gr.getCredito();
						
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
		totalCreditosCorrente = totalCreditosMelhor;
		//agora tenho os grupos de eletivas que preciso preencher na lista "grupoDeEletivas"
		this.historico = historico;
		this.catalogo = catalogo;

		// classifica as disciplinas
		eletivas = classificar();
		//as obrigatorias estao na atribuicao
		Log.d("total", eletivas.size()+"");
		
		try {
			melhorAtribuicao = (Atribuicao) atribuicao.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean formou = tenta(0) && fezObrigatorias();

		// preenche atribuicao
		atribuicao.setIntegral(formou);


		return atribuicao;
	}
	
	/**
	 * Checa se o usuario fez as disciplinas obrigatorias.
	 * @return  {@value true} se o usuario fez as obrigatorias.
	 */
	public boolean fezObrigatorias () {
		if (catalogo.getDisciplinas().size() != atribuicao.getDisciplinas().size()){
			return false;
		}
		// TODO: verificar se fez obrigatorias da modalidade ?
		return true;
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
	
	public Atribuicao getMelhorAtribuicao(){
		return this.melhorAtribuicao;
	}

}
