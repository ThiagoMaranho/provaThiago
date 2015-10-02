package com.example.prova2thiago;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Formulario extends Activity {

	private FormularioHelper helper;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		Intent intent = getIntent();
		Contato contatoSelecionado = (Contato) intent
				.getSerializableExtra("contatoSelecionado");

		helper = new FormularioHelper(this);

		btn = (Button) findViewById(R.id.btnSalvar);

		if (contatoSelecionado != null) {
			btn.setText("Alterar");
			helper.colocaAlunoNaTela(contatoSelecionado);
		}

		btnOnClick(contatoSelecionado);

	}

	private void btnOnClick(final Contato contatoSelecionado) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Contato contato = helper.pegaAlunoDoFormulario();
				ContatoDAO dao = new ContatoDAO(Formulario.this);
				if (contatoSelecionado == null) {
					dao.salva(contato);
				} else {
					contato.setId(contatoSelecionado.getId());
					dao.alterar(contato);
				}
				dao.close();

				finish();
			}
		});

	}
}
