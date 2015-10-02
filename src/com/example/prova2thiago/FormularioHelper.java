package com.example.prova2thiago;

import android.widget.EditText;

public class FormularioHelper {
	private EditText nome;
	private EditText telefone;

	private Contato contato;

	public FormularioHelper(Formulario formulario) {
		nome = (EditText) formulario.findViewById(R.id.edtNome);
		telefone = (EditText) formulario.findViewById(R.id.edtTelefone);

		contato = new Contato();
	}

	public Contato pegaAlunoDoFormulario() {

		contato.setNome(nome.getText().toString());
		contato.setTelefone(telefone.getText().toString());

		return contato;

	}

	public void colocaAlunoNaTela(Contato contato) {
		this.contato = contato;
		nome.setText(contato.getNome());
		telefone.setText(contato.getTelefone());

	}
}
