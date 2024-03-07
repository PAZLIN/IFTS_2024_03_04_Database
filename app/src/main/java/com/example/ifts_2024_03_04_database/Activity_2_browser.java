
package com.example.ifts_2024_03_04_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import database.DBadapter;

public class Activity_2_browser extends AppCompatActivity {
    public DBadapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_browser);

        // definisco i riferimenti a EditText, Button1, Button2, Button3 e Webview
        EditText editText = (EditText) findViewById(R.id.act2_et1);
        Button btn1 = (Button) findViewById(R.id.act2_btn1);
        Button btn2 = (Button) findViewById(R.id.act2_btn2);
        Button btn3 = (Button) findViewById(R.id.act2_btn3);
        WebView wv = (WebView) findViewById(R.id.act2_wv1);
        //"metto a posto" la webview
        // il WebViewClient serve per evitare che si apra Chrome a parte
        wv.getSettings().setJavaScriptEnabled(true);
        WebViewClient x = new WebViewClient();
        wv.setWebViewClient(x);

        // prendo la stringa url dal Bundle che mi sono fatto passare da MainActivity
        String url = getIntent().getStringExtra("url");
        // setto il testo di editText sulla url ricevuta e la carico nella webview
        editText.setText(url);
        if (url != null)
            wv.loadUrl(url);


        // setto l'editText in modo che il testo si cancelli al click
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");

            }
        });
        // setto l'editText per caricare url e nascondere keyboard alla pressione del tasto INVIO
        // senza questo, alla pressione di invio mi va a capo
        // (metodi addHttps() e hideKeyboard() definiti piu avanti
        // quando farò la classe Sito, questi due metodi sarà opportuno definirli
        // come metodi statici in Sito
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL) {
                    loadUrl(addHttps(editText.getText().toString()), wv);
                    hideKeyboard();
                }
                return true;
            }
        });

        // setto il bottone 1 per caricare url e nascondere keyboard alla pressione
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl(addHttps(editText.getText().toString()), wv);
                hideKeyboard();
            }
        });

        // setto il bottone 2 per tornare alla pagina principale
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_2_browser.this, MainActivity.class));
            }
        });

        // setto il bottone 2 (cuore) per aggiungere il sito ai preferiti
        // il database mi serve solo in questa unica circostanza per cui lo apro e lo chiudo qui
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBadapter dBadapter = new DBadapter(Activity_2_browser.this);
                dBadapter.open();
                String url = editText.getText().toString();
                String nome = getNomeSito(url);
                dbAdapter.aggiungiSito(nome, url);
                Toast.makeText(Activity_2_browser.this, "AGGIUNTO", Toast.LENGTH_SHORT).show();
                dbAdapter.close();
            }
        });


    }
// ************************* fine OnCreate


    // *******  *****  ***  ***  **  - - - - - - - - - - - - - - - -  **  ***  ***  *****  *******
    // *******  *****  ***  ***  **  - - - - - - - - - - - - - - - -  **  ***  ***  *****  *******
    // *******  *****  ***  ***  **  - - - - - - - - - - - - - - - -  **  ***  ***  *****  *******
    // *******  *****  ***  ***  **  - - - METODI DI SUPPORTO  - - -  **  ***  ***  *****  *******

    // nascondi soft keyboard
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    // carica url
    public void loadUrl(String url, WebView wv) {
        wv.loadUrl(url);
    }

    // aggiungi "https://" a www.site.com
    public String addHttps(String sito) {
        sito = "https://" + sito;
        return sito;
    }

    public String getNomeSito(String url) {
        int start = url.indexOf('.');
        int end = url.lastIndexOf('.');
        return url.substring(start + 1, end);
    }


}