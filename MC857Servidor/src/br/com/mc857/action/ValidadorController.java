package br.com.mc857.action;

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

	private final String URL_BASE = "http://localhost:80//";

	private String ra;
	private String atribuicaoString ;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}

	@Action(value = "buscaCatalogo", results = {
			@Result( type="stream", name=SUCCESS)}
			)
	public String valida(){
		Atribuicao attr = new Gson().fromJson(atribuicaoString, Atribuicao.class);
		//TODO: Tem que gerar a atribuicao a partir do dado vindo do request
		Boolean formou = validaIntegralizacao(attr, ra);


		return SUCCESS;

	}

	private Boolean validaIntegralizacao(Atribuicao atribuicao, String ra){
		StringBuffer histBuffer = HttpConnector.connect(URL_BASE + "?serv=h&ra="+ra);
		Historico historico = Parser.parseHistorico(histBuffer.toString());
		StringBuffer catBuffer = HttpConnector.connect(URL_BASE +"?serv=c&cod="+historico.getCurso());
		Catalogo catalogo = Parser.parseCatalogo(catBuffer.toString());

		//vamos ver as disciplinas obrigatorias
		boolean fezAsObrigatorias = cumpriuGrupo(atribuicao.getDisciplinas(), catalogo.getDisciplinas());
		if(!fezAsObrigatorias){
			return false;
		}

		//vamos ver as diciplinas obrigatorias da modalidade
		boolean fezAsObrigatoriasDaModalidade = true;

		if(historico.getModalidade() != null){
			for(Modalidade modalidade : catalogo.getModalidades()){
				if(modalidade.getNome().equals(historico.getModalidade())){
					fezAsObrigatoriasDaModalidade = 
							cumpriuGrupo(atribuicao.getModalidades().get(0).getDisciplinas(), modalidade.getDisciplinas());

					if(!fezAsObrigatoriasDaModalidade){
						return false;
					}

					for(GrupoEletiva grupoFeito : atribuicao.getModalidades().get(0).getGrupos()){
						boolean fezAsEletivasDaModalidade = false;
						for(GrupoEletiva grupoNecessario : modalidade.getGrupos()) {
							if(cumpriuGrupo(grupoFeito.getDisciplinas(), grupoNecessario.getDisciplinas())){
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

	private Boolean cumpriuGrupo(List<Disciplina> feitas, List<Disciplina> necessarias){
		Boolean cumpriu = true;
		for(Disciplina feita : feitas){
			cumpriu = feitas.contains(feita);
			if(!cumpriu){
				break;
			}
		}
		return cumpriu;
	}





}
