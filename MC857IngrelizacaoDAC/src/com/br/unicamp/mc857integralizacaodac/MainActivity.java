package com.br.unicamp.mc857integralizacaodac;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.br.unicamp.mc857ingrelizacaodac.R;
import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.utils.AppController;
import com.br.unicamp.mc857integralizacaodac.utils.WebServiceInterface;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	
	private EditText raField;
	private EditText cursoField;

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
		new LongOperation().execute(curso, ra);
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

	private void calcularIntegralizacao() {
		// TODO: metodo que recebe do controller o resultado do c�lculo da integraliza��o e a exibe
	}
	
	private void exibirResultado() {
		// TODO: metodo que recebe do controller o resultado calculado e mostra ao usu�rio
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
      		WebServiceInterface ws = new WebServiceInterface();
    		Catalogo lixo =ws.requisitarCatalogo(params[0]);
    		Historico hist = ws.requisitarHistorico(params[1]);
    		AppController controler = new AppController(hist.getRa().toString(), hist.getCurso());
    		Atribuicao attr = controler.gerarIntegralizacao(hist, lixo);
    		String nois =new Gson().toJson(attr);
    		

              return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {
              //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }   
	
}
