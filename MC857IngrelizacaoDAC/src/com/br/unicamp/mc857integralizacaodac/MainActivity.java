package com.br.unicamp.mc857integralizacaodac;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.unicamp.mc857ingrelizacaodac.R;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.utils.WebServiceInterface;

public class MainActivity extends Activity {
	
	private Integer ra;
	private Integer curso;
	private EditText raField;
	private EditText cursoField;
	private Button integralizarButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		new LongOperation().execute("42");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void calcularIntegralizacao() {
		// TODO: metodo que recebe do controller o resultado do cálculo da integralização e a exibe
	}
	
	private void exibirResultado() {
		// TODO: metodo que recebe do controller o resultado calculado e mostra ao usuário
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
      		WebServiceInterface ws = new WebServiceInterface();
    		Catalogo lixo =ws.requisitarCatalogo(params[0]);
    		System.out.println(lixo);


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
