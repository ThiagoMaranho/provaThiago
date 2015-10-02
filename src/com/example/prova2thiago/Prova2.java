package com.example.prova2thiago;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Prova2 extends Activity {

	private ListView lista;
	private Contato contato;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prova2);

		lista = (ListView) findViewById(R.id.lista);

		registerForContextMenu(lista);

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Contato contatoSelecionado = (Contato) adapter
						.getItemAtPosition(position);

				Intent irPara = new Intent(Prova2.this, Formulario.class);
				irPara.putExtra("contatoSelecionado", contatoSelecionado);

				startActivity(irPara);
			}
		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				contato = (Contato) adapter.getItemAtPosition(position);

				return false;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.prova2, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {

		carregaLista();

		super.onResume();
	}

	private void carregaLista() {
		ContatoDAO dao = new ContatoDAO(this);
		List<Contato> contatos = dao.getLista();
		dao.close();

		ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this,
				android.R.layout.simple_list_item_1, contatos);

		lista.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();

		switch (itemClicado) {
		case R.id.novo:
			Intent intent = new Intent(this, Formulario.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		menuDeletar(menu);

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	private void menuDeletar(ContextMenu menu) {

		MenuItem deletar = menu.add("Deletar");

		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				ContatoDAO dao = new ContatoDAO(Prova2.this);
				dao.deletar(contato);
				dao.close();

				carregaLista();
				return false;
			}
		});

	}
}
