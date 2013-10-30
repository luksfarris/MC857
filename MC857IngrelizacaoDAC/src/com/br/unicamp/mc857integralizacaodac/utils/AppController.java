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
	private int totalCreditosMelhor;
	private int totalCreditosCorrente;

	private List<Disciplina> eletivas;
	private List<GrupoEletiva> grupoDeEletivas;
	private Atribuicao atribuicao;
	public Atribuicao melhorAtribuicao;
	
	private Integer[] testes;
	private  HashMap<String, Boolean> tabelaDinamica = new HashMap<String, Boolean>();

	public AppController(String ra, int curso){
		this.ra = ra;
		this.curso = curso;
	}

	public Boolean validarIntegralizacao(){
		WebServiceInterface web = new WebServiceInterface();
		return web.validarIntegralizacao(atribuicao, ra);
	}

	public void recuperarInformacoesDoServico(){
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


	private boolean isObrigatoriaModalidade (Disciplina disciplina) {
		boolean isObrigatoria = false;
		if(catalogo.getModalidades() != null) {//32 17
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
	
	private void associa(Disciplina disciplina, GrupoEletiva grupo){
		
		if(grupo.getCredito()- grupo.getCreditosFeitos() < disciplina.getCredito()) {
			disciplina.setCreditosUsados(grupo.getCreditosFeitos() - grupo.getCredito());
			totalCreditosCorrente -= grupo.getCredito() -grupo.getCreditosFeitos() ;
		} else {
			disciplina.setCreditosUsados(disciplina.getCredito());
			totalCreditosCorrente -= disciplina.getCredito();
		}
		
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
		
		if(totalCreditosCorrente < totalCreditosMelhor) {
			try {
				melhorAtribuicao = (Atribuicao)atribuicao.clone();
				totalCreditosMelhor = totalCreditosCorrente;
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void dissocia(Disciplina disciplina, GrupoEletiva grupo){
		totalCreditosCorrente += disciplina.getCreditosUsados();
		
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

	public String currentHash (Integer i) {
		String hash = i.toString()+",";
		for (int j=0; j<grupoDeEletivas.size(); j++){
			hash = hash+grupoDeEletivas.get(j).getCreditosFeitos().toString()+",";
		}
		return hash;
	}
	
	
	private Boolean tenta(Integer i){
		if(jaTemosSolucao()){
			return true;
		}
		if(i >= eletivas.size()){
			return false;
		}
		Boolean achou = false;
		String hash = currentHash(i);
		if(tabelaDinamica.containsKey(hash)) {
			//Log.d("LUCAS", "cortou !! " + hash);
			return tabelaDinamica.get(hash);
		}
		if (maisEletivasQueCreditos(i)) {
			//Log.d("LUCAS", "corte logico");
			tabelaDinamica.put(hash, achou);
			return achou;
		}
		
		Disciplina disciplinaAtual = eletivas.get(i);
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
		
		
		//Log.d("LUCAS", "salvou " + hash + " como " + achou.toString());
		tabelaDinamica.put(hash, achou);
		
//		testes[i] += 1;
//		String log = "";
//		for (int j=0;j<testes.length;j++){
//			log = log + testes[j] +",";
//		}
//		Log.d("log", log);
//		
		
		if(!achou){
			achou = tenta(i+1);
		}
		return achou;
	}

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
		
		testes = new Integer[eletivas.size()];
		for (int i=0;i<eletivas.size();i++) {
			testes[i]=0;
		}
		
		boolean formou = tenta(0);
		
		// preenche atribuicao
		atribuicao.setIntegral(formou);


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

}
