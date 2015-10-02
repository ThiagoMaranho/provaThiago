package com.example.prova2thiago;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContatoDAO extends SQLiteOpenHelper {
	private static final String TABELA = "Contato";
	private static final String DATABASE = "Prova";
	private static final int VERSAO = 2;

	public ContatoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE "
				+ TABELA
				+ " (id INTEGER PRIMARY KEY,nome TEXT UNIQUE NOT NULL, telefone TEXT);";

		db.execSQL(ddl);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String ddl = "DROP TABLE IF EXISTS " + TABELA + ";";
		db.execSQL(ddl);

		this.onCreate(db);

	}

	public void salva(Contato contato) {
		ContentValues values = new ContentValues();
		values.put("nome", contato.getNome());
		values.put("telefone", contato.getTelefone());

		getWritableDatabase().insert(TABELA, null, values);

	}

	public List<Contato> getLista() {
		String[] colunas = { "id", "nome", "telefone" };
		Cursor cursor = getWritableDatabase().query(TABELA, colunas, null,
				null, null, null, null);

		ArrayList<Contato> contatos = new ArrayList<Contato>();

		while (cursor.moveToNext()) {
			Contato contato = new Contato();

			contato.setId(cursor.getLong(0));
			contato.setNome(cursor.getString(1));
			contato.setTelefone(cursor.getString(2));

			contatos.add(contato);
		}
		return contatos;
	}

	public void deletar(Contato contato) {
		String[] args = { contato.getId().toString() };
		getWritableDatabase().delete(TABELA, "id=?", args);

	}

	public void alterar(Contato aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());

		String[] args = { aluno.getId().toString() };
		getWritableDatabase().update(TABELA, values, "id=?", args);

	}

}
