package com.br.unicamp.mc857integralizacaodac;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.unicamp.mc857ingrelizacaodac.R;
import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
import com.br.unicamp.mc857integralizacaodac.model.GrupoEletiva;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.model.Modalidade;
import com.br.unicamp.mc857integralizacaodac.utils.AppController;
import com.br.unicamp.mc857integralizacaodac.utils.WebServiceInterface;

public class MainActivity extends Activity {
	
	private EditText raField;
	private EditText cursoField;
	ProgressDialog dialog;
	AppController controller;
	
	private Boolean integralizou = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		raField = (EditText) findViewById(R.id.raField);
		cursoField = (EditText) findViewById(R.id.cursoField);
	}

	    
	public void integralizar(View view){
		String ra = raField.getText().toString();
		String curso = cursoField.getText().toString();
		dialog = ProgressDialog.show(this, "Integralizando...", "Aguarde enquanto processamos a integralização...");
		TextView resultado = (TextView) findViewById(R.id.resultado);
		resultado.setVisibility(View.GONE);
		
		if (ra.length() < 3 || curso.length() < 1 || !ra.matches("[0-9]+") || !curso.matches("[0-9]+")) {
			Toast t = Toast.makeText(this, "Preencha corretamente os campos.", Toast.LENGTH_LONG);
			t.show();
		} else {
			new LongOperation().execute(curso, ra);
		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void exibirResultado() {
		TextView resultado = (TextView) findViewById(R.id.resultado);
		if (integralizou == null) {
			Toast t = Toast.makeText(this,"Ocorreu um erro na validação" , Toast.LENGTH_LONG);
			t.show();
		} else if(integralizou) {
			resultado.setText("Integralizado!");
			resultado.setTextColor(Color.BLUE);
		}else{
			resultado.setText("Não Integralizado!");
			resultado.setTextColor(Color.RED);
		}
		resultado.setVisibility(View.VISIBLE);
			
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
      		WebServiceInterface ws = new WebServiceInterface();
      		Catalogo catalogo =ws.requisitarCatalogo(params[0]);
      		if (catalogo == null) {
      			return null;
      		}
    		Historico hist = ws.requisitarHistorico(params[1]);

    		if (hist == null) {
    			return null;
    		}
    	
    		controller = new AppController(params[1], hist.getCurso());
    		Atribuicao atr = controller.gerarIntegralizacao(hist, catalogo);

    		
    		if (atr.isIntegral()) {    			
    			integralizou = controller.validarIntegralizacao();
    		} else {
    			integralizou = false;
    			String resposta = trataResposta(catalogo);
    			Intent i = new Intent(MainActivity.this, ResultActivity.class);
    			i.putExtra("resultado", resposta);
    			startActivity(i);
    		}
    		
    		if (integralizou == null) {
    			// TODO tratar erro
    		} else {    			
    			Log.d("FORMOU", integralizou.toString());
    		}

    		
          return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {
        	if (result != null) {
        		exibirResultado();
        	} else {
        		Toast t = Toast.makeText(MainActivity.this, "Ocorreu um erro de conexão.", Toast.LENGTH_LONG);
        		t.show();
        	}
        	dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }   
	
	/* Metodos de tratamento da resposta */
    private String trataResposta(Catalogo catalogo){
    	System.out.println("Entrou trataResposta");
    	Atribuicao melhorAtribuicao = controller.getMelhorAtribuicao();
    	
    	//TODO: APAGAR - DEBUG
    	if (controller == null)
    		System.out.println ("Controller null");
    	if (melhorAtribuicao == null) {
    		System.out.println ("Nao pegou melhor atribuicao : controller zuado.");
    		melhorAtribuicao = controller.atribuicao;
    	}
    	
    	Modalidade modalidade = null;
    	if (catalogo.getModalidades() != null && catalogo.getModalidades().size() > 0){
	    	for (Modalidade mod: catalogo.getModalidades()){
	    		if (mod.getNome().toLowerCase().contains((melhorAtribuicao.getModalidades().get(0).getNome().toLowerCase()))){
	    			modalidade = mod;
	    			break;
	    		}
	    	}
    	}
    	
    	//obrigatorios do curso
    	List<Disciplina> obrigatorias = listObrigatoriasRestantes(melhorAtribuicao.getDisciplinas(), catalogo.getDisciplinas());
    	String resposta = obrigatorias.size() + " disciplinas obrigatórias do curso faltantes \n" ;
    	for (Disciplina obrg : obrigatorias) {
    		resposta += obrg.getSigla() + " ";
    	}
    	
    	//eletivas do curso
    	for(GrupoEletiva grupoFeito : melhorAtribuicao.getGrupos()){
			for(GrupoEletiva grupoNecessario : catalogo.getGrupos()) {
				if (grupoFeito.getId() == grupoNecessario.getId()) {
					String eletivas = eletivasRestantes(grupoFeito.getDisciplinas(), grupoNecessario.getDisciplinas(), grupoNecessario.getCredito());
					resposta += eletivas;
				    if(eletivas.equals("")){
						break;
					}
				}
			}
			
    	}

    	//obrigatorios da modalidade
    	if (modalidade != null){
    		List<Disciplina> obrigatoriasMod = listObrigatoriasRestantes(melhorAtribuicao.getModalidades().get(0).getDisciplinas(), modalidade.getDisciplinas());
    		resposta += "\n" + obrigatoriasMod.size() + " disciplinas obrigatórias da modalidade faltantes \n" ;
    		for (Disciplina obrg : obrigatoriasMod) {
    			resposta += obrg.getSigla() + " ";
    		}
    		
    		//eletivas da modalidade
    		if (melhorAtribuicao.getModalidades().get(0) != null) {
	    		if (melhorAtribuicao.getModalidades().get(0).getGrupos() != null) {
			    	for(GrupoEletiva grupoFeito : melhorAtribuicao.getModalidades().get(0).getGrupos()){
						for(GrupoEletiva grupoNecessario : modalidade.getGrupos()) {
							if (grupoFeito.getId() == grupoNecessario.getId()) {
								String eletivasMod = eletivasRestantes(grupoFeito.getDisciplinas(), grupoNecessario.getDisciplinas(), grupoNecessario.getCredito());
								resposta += eletivasMod;
							    if(eletivasMod.equals("")){
									break;
								}
							}
						}
					}
	    		}
    		}
    	}
    	System.out.println(resposta);
    	return resposta;
    }
    
    private List<Disciplina> listObrigatoriasRestantes(List<Disciplina> feitas, List<Disciplina> necessarias){
    	if (feitas==null && necessarias == null) {
    		return new ArrayList<Disciplina>();
    	}
		if(feitas.size() == necessarias.size()) {
			return new ArrayList<Disciplina>();
		}
		
		int disciplinasFaltantes = necessarias.size() - feitas.size();
		for(Disciplina feita : feitas){
			boolean cumpriu = necessarias.contains(feita);
			if(cumpriu){
				necessarias.remove(feita);
				if (necessarias.size() == disciplinasFaltantes){
					break;
				}
			}
		}
		return necessarias;
	}
    
    private String eletivasRestantes(List<Disciplina> feitas, List<Disciplina> necessarias, int credito){
		String eletivas = "";
		if(feitas == null) {
			feitas = new ArrayList<Disciplina>();
		}
		if(necessarias ==null){
			necessarias = new ArrayList<Disciplina>();
		}
    	int creditoFeito = 0;
		for(Disciplina feita : feitas){
			if(necessarias.contains(feita)){
				creditoFeito += feita.getCredito();
			}
		}
		
		int creditoFaltante = credito - creditoFeito;
		if (creditoFaltante > 0){
			eletivas += "\n" + creditoFaltante + " créditos faltantes dentre as disciplinas: \n";
			for (Disciplina n : necessarias){
				eletivas += n.getSigla() + " ";
			}
		}
		
		return eletivas;
	}
    /* Fim Metodos de tratamento da resposta */
}
