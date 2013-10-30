package com.br.unicamp.mc857integralizacaodac;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.utils.AppController;
import com.br.unicamp.mc857integralizacaodac.utils.WebServiceInterface;

public class MainActivity extends Activity {
	
	private EditText raField;
	private EditText cursoField;
	ProgressDialog dialog;
	
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
		dialog = ProgressDialog.show(this, "Integralizando...", "Aguarde enquanto processamos a integraliza��o...");
		TextView resultado = (TextView) findViewById(R.id.resultado);
		resultado.setVisibility(View.GONE);
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
    		Historico hist = ws.requisitarHistorico(params[1]);
    		AppController controler = new AppController(params[1], hist.getCurso());
    		Atribuicao atr = controler.gerarIntegralizacao(hist, catalogo);
    		
    		if (atr.isIntegral()) {    			
    			integralizou = controler.validarIntegralizacao();
    		} else {
    			integralizou = false;
    		}
    		
    		Log.d("FORMOU", integralizou.toString());
          return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {
        	exibirResultado();
        	dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }   
	
}
