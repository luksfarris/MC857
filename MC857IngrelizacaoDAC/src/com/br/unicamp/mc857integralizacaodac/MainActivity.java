package com.br.unicamp.mc857integralizacaodac;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.unicamp.mc857ingrelizacaodac.R;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.utils.AppController;
import com.br.unicamp.mc857integralizacaodac.utils.WebServiceInterface;

public class MainActivity extends Activity {
	
	private EditText raField;
	private EditText cursoField;
	
	private Boolean integralizou = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		raField = (EditText) findViewById(R.id.raField);
		cursoField = (EditText) findViewById(R.id.cursoField);
		Button submit = (Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				integralizar(v);
			}
		});
		
	}

	    
	public void integralizar(View view){
		System.out.println("teste");
		String ra = raField.getText().toString();
		String curso = cursoField.getText().toString();
		
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
		Toast t = Toast.makeText(this, integralizou.toString(), Toast.LENGTH_LONG);
		t.show();
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
      		WebServiceInterface ws = new WebServiceInterface();
      		Catalogo catalogo = ws.requisitarCatalogo(params[0]);
      		if (catalogo == null) {
      			return null;
      		}
    		Historico hist = ws.requisitarHistorico(params[1]);
    		if (hist == null) {
    			return null;
    		}
    		
    		AppController controler = new AppController(params[1], hist.getCurso());
    		controler.gerarIntegralizacao(hist, catalogo);    		
    		integralizou = controler.validarIntegralizacao();

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
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }   
	
}
