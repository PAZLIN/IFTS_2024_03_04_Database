package com.example.ifts_2024_03_04_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;

import database.DBadapter;

public class Activity_3_edit extends AppCompatActivity {

    public DBadapter dbAdapter;
    private int idSito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_edit);

        dbAdapter = new DBadapter(this);
        dbAdapter.open();
        // ricavo solo la listUrl. Mi serve solo per controllare che un sito non sia
        // già presente nel db, prima di inserirlo
        ArrayList<String>listUrl = dbAdapter.getListUrl();


        EditText editText1 =(EditText) findViewById(R.id.act3_et1);
        EditText editText2 =(EditText) findViewById(R.id.act3_et2);
        Button button = (Button) findViewById(R.id.act3_btn1);

        // questo boolean mi serve per controllare se sono arrivato alla Activity3
        // dall'OptionMenu oppure dal click lungo sul sito
        boolean nuovoInsert;

        // se il Bundle getIntent().getExtras() non è null allora sono arrivato da main con longclick
        if (getIntent().getExtras()!=null) {
            Bundle datiRicevuti = getIntent().getExtras();
            String nome = datiRicevuti.getString("nome");
            String url = datiRicevuti.getString("url");
            idSito = datiRicevuti.getInt("_id");
            editText1.setText(nome);
            editText2.setText(url);
            nuovoInsert=false;
        } else {
            // sono arrivato qui passando da OptionsMenu
            editText1.setHint("Inserisci nome sito");
            editText2.setHint("Inserisci url");
            nuovoInsert = true;
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editText1.getText().toString();
                String url = editText2.getText().toString();
                if (!(url.contains("http")))
                    url = "https://" + url;

                // se ci sono arrivato da OptionsMenu allora devo chiamare il metodo crud insertOrThrow()
                if (nuovoInsert) {
                    if (!(listUrl.contains(url)))
                        dbAdapter.aggiungiSito(nome, url);
                    dbAdapter.close();
                    startActivity(new Intent(Activity_3_edit.this, MainActivity.class));
                } else {
                // in caso contrario devo chiamare il metodo crud update()
                        dbAdapter.modificaSito(idSito, nome, url);
                        dbAdapter.close();
                        startActivity(new Intent(Activity_3_edit.this, MainActivity.class));
                    }
                }
        });





    } // fine OnCreate **************************




}