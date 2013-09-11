package com.br.unicamp.mc857integralizacaodac;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.br.unicamp.mc857ingrelizacaodac.R;

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
	
}
