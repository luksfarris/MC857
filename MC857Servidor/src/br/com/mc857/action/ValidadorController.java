package br.com.mc857.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.mc857.model.Atribuicao;
import br.com.mc857.model.Catalogo;
import br.com.mc857.model.Disciplina;
import br.com.mc857.model.GrupoEletiva;
import br.com.mc857.model.Historico;
import br.com.mc857.model.Modalidade;
import br.com.mc857.util.HttpConnector;
import br.com.mc857.util.Parser;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class ValidadorController extends ActionSupport{

	private static final long serialVersionUID = -8251392448200439157L;

	private final String URL_BASE = "http://localhost:81//";

	private String ra;
	private String atribuicaoString ;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
     * Valida a atribuicao
     * @param atribuicaoString Json da atribuicao realizada
     * @param ra RA do aluno que sera validado
     * @return booleano dizendo se o aluno se formou ou nao.
     */
	@Action(value = "valida", results = {
			@Result( type="stream", name=SUCCESS)}
			)
	public String valida(){
		Atribuicao attr = new Gson().fromJson(atribuicaoString, Atribuicao.class);

		Boolean formou = validaIntegralizacao(attr, ra);

		inputStream = new ByteArrayInputStream(formou.toString().getBytes());

		return SUCCESS;
	}
	
	/**
     * Valida a atribuicao
     * @param atribuicao Atribuicao realizada pela aplicacao
     * @param ra RA do aluno
     * @return booleano dizendo se o aluno se formou ou nao.
     */
	private Boolean validaIntegralizacao(Atribuicao atribuicao, String ra){
		StringBuffer histBuffer = HttpConnector.connect(URL_BASE + "?serv=h&ra="+ra);
		Historico historico = Parser.parseHistorico(histBuffer.toString());
		StringBuffer catBuffer = HttpConnector.connect(URL_BASE +"?serv=c&cod="+historico.getCurso());
		Catalogo catalogo = Parser.parseCatalogo(catBuffer.toString());

		//vamos ver as disciplinas obrigatorias
		boolean fezAsObrigatorias = cumpriuObrigatorias(atribuicao.getDisciplinas(), catalogo.getDisciplinas());
		if(!fezAsObrigatorias){
			return false;
		}
		
		for(GrupoEletiva grupoFeito : atribuicao.getGrupos()){
			boolean fezAsEletivas = false;
			for(GrupoEletiva grupoNecessario : catalogo.getGrupos()) {
				if(cumpriuEletivas(grupoFeito.getDisciplinas(), grupoNecessario.getDisciplinas(), grupoNecessario.getCredito())){
					fezAsEletivas = true;
					break;
				}
			}
			
			if(!fezAsEletivas){
				return false;
			}
		}
		
		//vamos ver as diciplinas obrigatorias da modalidade
		boolean fezAsObrigatoriasDaModalidade = true;

		if(historico.getModalidade() != null && !historico.getModalidade().equals("") ){
			for(Modalidade modalidade : catalogo.getModalidades()){
				if(modalidade.getNome().equals(historico.getModalidade())){
					fezAsObrigatoriasDaModalidade = 
							cumpriuObrigatorias(atribuicao.getModalidades().get(0).getDisciplinas(), modalidade.getDisciplinas());

					if(!fezAsObrigatoriasDaModalidade){
						return false;
					}

					for(GrupoEletiva grupoFeito : atribuicao.getModalidades().get(0).getGrupos()){
						boolean fezAsEletivasDaModalidade = false;
						for(GrupoEletiva grupoNecessario : modalidade.getGrupos()) {
							if(cumpriuEletivas(grupoFeito.getDisciplinas(), grupoNecessario.getDisciplinas(), grupoNecessario.getCredito())){
								fezAsEletivasDaModalidade = true;
								break;
							}
						}
						
						if(!fezAsEletivasDaModalidade){
							return false;
						}
					}
					break;
				}
			}
		}

		return true;
	}

	/**
     * Verifica se a atribuicao contem todas as obrigatorias da lista passada
     * @param feitas disciplinas cursadas
     * @param necessarias as disciplinas obrigatorias necessarias
     * @return booleano dizendo se o aluno cumpriu ou nao as obrigatorias.
     */
	private Boolean cumpriuObrigatorias(List<Disciplina> feitas, List<Disciplina> necessarias){
		Boolean cumpriu = true;
		
		if(feitas.size() != necessarias.size()) {
			return false;
		}
		
		for(Disciplina feita : feitas){
			cumpriu = necessarias.contains(feita);
			if(!cumpriu){
				break;
			}
		}
		return cumpriu;
	}
	
	/**
     * Verifica se a atribuicao contem todas as eletivas da lista passada
     * @param feitas disciplinas eletivas cursadas
     * @param necessarias as disciplinas eletivas necessarias
     * @return booleano dizendo se o aluno cumpriu ou nao as eletivas.
     */
	private Boolean cumpriuEletivas(List<Disciplina> feitas, List<Disciplina> necessarias, int credito){
		int creditoFeito = 0;
		
		for(Disciplina feita : feitas){
			if(necessarias.contains(feita)){
				creditoFeito += feita.getCredito();
			}
		}
		
		return creditoFeito >= credito;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getAtribuicaoString() {
		return atribuicaoString;
	}

	public void setAtribuicaoString(String atribuicaoString) {
		this.atribuicaoString = atribuicaoString;
	}

}
