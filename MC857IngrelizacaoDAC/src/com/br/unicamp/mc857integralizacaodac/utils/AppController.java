package com.br.unicamp.mc857integralizacaodac.utils;

import java.util.ArrayList;
import java.util.List;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
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
		
		String xmlHist="<historico ra=\"031015\" curso=\"27\" modalidade=\"AF\" nome =\"Vanderson Saboia Pini Xavier\">      <disciplinas>        <disciplina sigla=\"BA110\" cred=\"4\"/>        <disciplina sigla=\"BA210\" cred=\"4\"/>        <disciplina sigla=\"BB110\" cred=\"4\"/>        <disciplina sigla=\"BF310\" cred=\"3\"/>        <disciplina sigla=\"BF410\" cred=\"3\"/>        <disciplina sigla=\"EF109\" cred=\"4\"/>        <disciplina sigla=\"EF112\" cred=\"4\"/>        <disciplina sigla=\"EF113\" cred=\"4\"/>        <disciplina sigla=\"EF114\" cred=\"4\"/>        <disciplina sigla=\"EF115\" cred=\"4\"/>        <disciplina sigla=\"EF116\" cred=\"4\"/>        <disciplina sigla=\"EF209\" cred=\"4\"/>        <disciplina sigla=\"EF212\" cred=\"2\"/>        <disciplina sigla=\"EF213\" cred=\"4\"/>        <disciplina sigla=\"EF214\" cred=\"2\"/>        <disciplina sigla=\"EF215\" cred=\"2\"/>        <disciplina sigla=\"EF309\" cred=\"4\"/>        <disciplina sigla=\"EF312\" cred=\"2\"/>        <disciplina sigla=\"EF313\" cred=\"4\"/>        <disciplina sigla=\"EF314\" cred=\"4\"/>        <disciplina sigla=\"EF315\" cred=\"4\"/>        <disciplina sigla=\"EF316\" cred=\"4\"/>        <disciplina sigla=\"EF411\" cred=\"4\"/>        <disciplina sigla=\"EF412\" cred=\"2\"/>        <disciplina sigla=\"EF413\" cred=\"4\"/>        <disciplina sigla=\"EF414\" cred=\"2\"/>        <disciplina sigla=\"EF415\" cred=\"2\"/>        <disciplina sigla=\"EF416\" cred=\"4\"/>        <disciplina sigla=\"EF511\" cred=\"2\"/>        <disciplina sigla=\"EF512\" cred=\"4\"/>        <disciplina sigla=\"EF513\" cred=\"4\"/>        <disciplina sigla=\"EF514\" cred=\"4\"/>        <disciplina sigla=\"EF611\" cred=\"4\"/>        <disciplina sigla=\"EF612\" cred=\"2\"/>        <disciplina sigla=\"EF613\" cred=\"2\"/>        <disciplina sigla=\"EF614\" cred=\"2\"/>        <disciplina sigla=\"EF711\" cred=\"2\"/>        <disciplina sigla=\"EF714\" cred=\"2\"/>        <disciplina sigla=\"EF811\" cred=\"2\"/>        <disciplina sigla=\"EF814\" cred=\"4\"/>        <disciplina sigla=\"EL683\" cred=\"6\"/>        <disciplina sigla=\"EF428\" cred=\"2\"/>        <disciplina sigla=\"EF441\" cred=\"2\"/>        <disciplina sigla=\"EF442\" cred=\"2\"/>        <disciplina sigla=\"EF443\" cred=\"2\"/>        <disciplina sigla=\"EF444\" cred=\"2\"/>        <disciplina sigla=\"EF531\" cred=\"2\"/>        <disciplina sigla=\"EF532\" cred=\"4\"/>        <disciplina sigla=\"EF631\" cred=\"4\"/>        <disciplina sigla=\"EF632\" cred=\"4\"/>        <disciplina sigla=\"EF731\" cred=\"8\"/>        <disciplina sigla=\"EF732\" cred=\"2\"/>        <disciplina sigla=\"EF831\" cred=\"8\"/>        <disciplina sigla=\"EF832\" cred=\"4\"/>        <disciplina sigla=\"EF641\" cred=\"3\"/>        <disciplina sigla=\"EF642\" cred=\"3\"/>        <disciplina sigla=\"EF643\" cred=\"3\"/>        <disciplina sigla=\"EF644\" cred=\"3\"/>        <disciplina sigla=\"EF445\" cred=\"2\"/>        <disciplina sigla=\"EF446\" cred=\"2\"/>        <disciplina sigla=\"EF447\" cred=\"2\"/>        <disciplina sigla=\"EF449\" cred=\"2\"/>        <disciplina sigla=\"EF450\" cred=\"2\"/>        <disciplina sigla=\"EF645\" cred=\"3\"/>        <disciplina sigla=\"EF646\" cred=\"3\"/>        <disciplina sigla=\"EF647\" cred=\"3\"/>        <disciplina sigla=\"EF648\" cred=\"3\"/>      </disciplinas>    </historico>";
		String xmlCat="<curso cod=\"27\" nome=\"Educacao Fisica Diurno\">     <disciplinas>       <disciplina sigla=\"BA110\"/>       <disciplina sigla=\"BA210\"/>       <disciplina sigla=\"BB110\"/>       <disciplina sigla=\"BF310\"/>       <disciplina sigla=\"BF410\"/>       <disciplina sigla=\"EF109\"/>       <disciplina sigla=\"EF112\"/>       <disciplina sigla=\"EF113\"/>       <disciplina sigla=\"EF114\"/>       <disciplina sigla=\"EF115\"/>       <disciplina sigla=\"EF116\"/>       <disciplina sigla=\"EF209\"/>       <disciplina sigla=\"EF212\"/>       <disciplina sigla=\"EF213\"/>       <disciplina sigla=\"EF214\"/>       <disciplina sigla=\"EF215\"/>       <disciplina sigla=\"EF309\"/>       <disciplina sigla=\"EF312\"/>       <disciplina sigla=\"EF313\"/>       <disciplina sigla=\"EF314\"/>       <disciplina sigla=\"EF315\"/>       <disciplina sigla=\"EF316\"/>       <disciplina sigla=\"EF411\"/>       <disciplina sigla=\"EF412\"/>       <disciplina sigla=\"EF413\"/>       <disciplina sigla=\"EF414\"/>       <disciplina sigla=\"EF415\"/>       <disciplina sigla=\"EF416\"/>       <disciplina sigla=\"EF511\"/>       <disciplina sigla=\"EF512\"/>       <disciplina sigla=\"EF513\"/>       <disciplina sigla=\"EF514\"/>       <disciplina sigla=\"EF611\"/>       <disciplina sigla=\"EF612\"/>       <disciplina sigla=\"EF613\"/>       <disciplina sigla=\"EF614\"/>       <disciplina sigla=\"EF711\"/>       <disciplina sigla=\"EF714\"/>       <disciplina sigla=\"EF811\"/>       <disciplina sigla=\"EF814\"/>       <disciplina sigla=\"EL683\"/>     </disciplinas>     <gruposEletivas>       <grupoEletivas cred=\"10\">         <disciplinas>           <disciplina sigla=\"EF428\"/>           <disciplina sigla=\"EF441\"/>           <disciplina sigla=\"EF442\"/>           <disciplina sigla=\"EF443\"/>           <disciplina sigla=\"EF444\"/>           <disciplina sigla=\"EF445\"/>           <disciplina sigla=\"EF446\"/>           <disciplina sigla=\"EF447\"/>           <disciplina sigla=\"EF449\"/>           <disciplina sigla=\"EF450\"/>           <disciplina sigla=\"EF451\"/>         </disciplinas>       </grupoEletivas>     </gruposEletivas>     <modalidades>       <modalidade nome=\"AB - Licenciatura em Educacao Fisica\">         <disciplinas>           <disciplina sigla=\"EF521\"/>           <disciplina sigla=\"EF621\"/>           <disciplina sigla=\"EF622\"/>           <disciplina sigla=\"EF722\"/>           <disciplina sigla=\"EF723\"/>           <disciplina sigla=\"EF822\"/>           <disciplina sigla=\"EL211\"/>           <disciplina sigla=\"EL511\"/>           <disciplina sigla=\"EL774\"/>           <disciplina sigla=\"EL874\"/>         </disciplinas>         <gruposEletivas>           <grupoEletivas cred=\"16\">             <disciplinas>               <disciplina sigla=\"-----\"/>             </disciplinas>           </grupoEletivas>         </gruposEletivas>       </modalidade>       <modalidade nome=\"AF - Bacharelado em Educacao Fisica\">         <disciplinas>           <disciplina sigla=\"EF531\"/>           <disciplina sigla=\"EF532\"/>           <disciplina sigla=\"EF631\"/>           <disciplina sigla=\"EF632\"/>           <disciplina sigla=\"EF731\"/>           <disciplina sigla=\"EF732\"/>           <disciplina sigla=\"EF831\"/>           <disciplina sigla=\"EF832\"/>         </disciplinas>         <gruposEletivas>           <grupoEletivas cred=\"12\">             <disciplinas>               <disciplina sigla=\"EF641\"/>               <disciplina sigla=\"EF642\"/>               <disciplina sigla=\"EF643\"/>               <disciplina sigla=\"EF644\"/>               <disciplina sigla=\"EF645\"/>               <disciplina sigla=\"EF646\"/>               <disciplina sigla=\"EF647\"/>               <disciplina sigla=\"EF648\"/>               <disciplina sigla=\"EF649\"/>               <disciplina sigla=\"EF651\"/>               <disciplina sigla=\"EF661\"/>             </disciplinas>           </grupoEletivas>           <grupoEletivas cred=\"22\">             <disciplinas>               <disciplina sigla=\"-----\"/>             </disciplinas>           </grupoEletivas>         </gruposEletivas>       </modalidade>     </modalidades>   </curso>";
		this.setHistorico(Parser.parseHistorico(xmlHist));
		this.setCatalogo(Parser.parseCatalogo(xmlCat));
		
		assert (historico != null) : "historico inválido";
		assert (catalogo != null) : "catalogo inválido";
	}
	
	private boolean jaTemosSolucao(){
		for(GrupoEletiva grupo : grupoDeEletivas){
			if(grupo.getCredito() < grupo.getCreditosFeitos()){
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
		Modalidade modalidade = new Modalidade();
		modalidade.setNome(historico.getModalidade());
		atribuicao.getModalidades().add(modalidade);
		int contador = 1;
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
			
			grupoDeEletivas.add(grupo);
			if(atribuicao.getGrupos() == null){
				atribuicao.setGrupos(new ArrayList<GrupoEletiva>());
			}
			atribuicao.getGrupos().add(gAtr);
		}
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
			if (catalogo.getDisciplinas().contains (disciplina)) {
				//se sim, adiciona na atribuicao
				atribuicao.getDisciplinas().add(atribuida);
			}
			// checa se eh obrigatoria da modalidade
			else if (isObrigatoriaModalidade(disciplina)) {
				// se sim, adiciona na atribuicao
				atribuicao.getModalidades().get(0).getDisciplinas().add(atribuida);
			} else {
				// adiciona na lista de eletivas
				eletivas.add(atribuida);
			}
		}
		return eletivas;
	}
	
	
	private boolean isObrigatoriaModalidade (Disciplina disciplina) {
		boolean isObrigatoria = false;
		for (Modalidade modalidade : catalogo.getModalidades()) {
			if (historico.getModalidade().equalsIgnoreCase(modalidade.getNome())) {
				if (modalidade.getDisciplinas().contains(disciplina)) {
					isObrigatoria= true;
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
