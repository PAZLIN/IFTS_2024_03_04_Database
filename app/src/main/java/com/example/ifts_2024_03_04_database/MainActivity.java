package com.example.ifts_2024_03_04_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import database.*;

public class MainActivity extends AppCompatActivity {

    private DBadapter dbAdapter;
    private String LOG_TAG = "MainActivity STATO";

    protected void inizializza(){
    setContentView(R.layout.activity_main);

        Log.i(LOG_TAG,"mi trovo nella onCreate");

    // istanzio il mio adapter e gli passo il context
    // il Context dell'Activity viene passato al dbadapter, il quale lo passa all'oggetto helper, il quale lo utilizza nel
    // metodo getWritableDatabase() per ricavare il percorso dell'applicazione e creare il file nel percorso corretto
    dbAdapter =new DBadapter(this);

    // chiamo il metodo open(), il quale non crea un database ma istanzia un oggetto di tipo SQLiteOpenHelper,
    // con questo oggetto viene chiamato il metodo di istanza getWritableDatabase(), il quale ritorna un oggetto
    // di tipo SQLiteDatabaseper che viene assegnato alla variabile di istanza di DBadapter "database".
        dbAdapter.open();
        dbAdapter.creaListeDati();

    // questo di seguito non va bene
    // era più conveniente fare una classe Sito con campi id, nome e url e ottenere un'ArrayList di Sito
    // meglio ancora utilizzando un CursorAdapter
    ArrayList<Integer> listId = dbAdapter.getListId();
    ArrayList<String> listNomi = dbAdapter.getListNomi();
    ArrayList<String> listUrl = dbAdapter.getListUrl();

    // prendo il riferimento alla ListView, defiisco un adapter (semplice) a cui passo la lista di nomi sito e li aggancio con setAdapter()
    ListView lv = (ListView) findViewById(R.id.act1_list1);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNomi);
        lv.setAdapter(adapter);

    // al click chiamo l'activity 2 (browser) e gli passo l'url
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
        Intent activity2 = new Intent(MainActivity.this, Activity_2_browser.class);
        activity2.putExtra("url", listUrl.get(position));
        startActivity(activity2);

    }
    });

    // al click lungo chiamo l'activity 3 (edit) e gli passo _id, nome e url.
    // nome e url vengono poi inseriti come hint negli editText di activity3
    // _id serve, sempre in activity_3, per chiamare il metodo modificaSito (CRUD-->update())
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

    {
        @Override
        public boolean onItemLongClick (AdapterView < ? > parent, View view,int position, long id){
        Intent intActivityEdit = new Intent(MainActivity.this, Activity_3_edit.class);
        intActivityEdit.putExtra("_id", listId.get(position));
        intActivityEdit.putExtra("nome", listNomi.get(position));
        intActivityEdit.putExtra("url", listUrl.get(position));
        startActivity(intActivityEdit);
        return true;
    }
    });
        dbAdapter.close();
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inizializza();
    } // ***** FINE ON CREATE


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "mi trovo nella onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "mi trovo nella onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "mi trovo nella onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "mi trovo nella onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "mi trovo nella onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inizializza();
        Log.i(LOG_TAG, "mi trovo nella ONRESTART");

    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_item_1)
            startActivity(new Intent(MainActivity.this, Activity_3_edit.class));
        return true;
    }


}