package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBadapter {


    private Context ctx;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    // Database field
    private static final String DATABASE_TABLE = "sitiweb";
    public static final String KEY_ID = "_id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_URL = "url";

    // campi per ottenere liste
    // meglio fare una classe "Sito" ?
    ArrayList<Integer> listId = new ArrayList<>();
    ArrayList<String>listNomi = new ArrayList<>();
    ArrayList<String>listUrl = new ArrayList<>();

    Cursor cursor;

    // costruttore (serve solo per istanziare
    public DBadapter(Context ctx){
        this.ctx=ctx;
    }

    //questa era public DBadapter open() con return this;
    // ma funziona anche cin return void
    public void open(){
        dbHelper = new DbHelper(ctx);
        database = dbHelper.getWritableDatabase();
        return ;
    }



    public void close(){
        dbHelper.close();
    }

    // creaContenuti (metodo privato) - ci si appoggiano i metodi successivi
    private ContentValues creaRecord(String nome, String url){
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, nome);
        values.put(KEY_URL, url);
        return values;
    }

    // crea un nuovo sitoweb
    public long aggiungiSito (String nome, String url){
        ContentValues initialValues = creaRecord(nome, url);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    // modifica un sito web esistente
    public boolean modificaSito(long sitoID, String nome, String url){
        ContentValues updateValues = creaRecord(nome, url);
        return database.update(DATABASE_TABLE, updateValues, KEY_ID + "=" + sitoID, null) >0;
    }

    // cancella sito
    public boolean cancellaSito (long sitoID){
        return database.delete(DATABASE_TABLE, KEY_ID + "=" + sitoID, null)>0;
    }

    // recupera tutti i dati
    public Cursor ottieniSiti(){
        return database.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NOME, KEY_URL}, null, null, null, null, null);
    }


    // questi metodi li avevo messi originariamente in una classe a parte
    // che però prevedeva l'apertura di un db, per cui forse è meglio
    // lasciarle qui

    // usa il cursore per ciclare il db e popolare le liste ID, NOME, URL
    public void creaListeDati() {
        cursor = this.ottieniSiti();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String url = cursor.getString(2);
            listId.add(id);
            listNomi.add(nome);
            listUrl.add(url);
        }
    }

    // metodi per resituire le liste
    public ArrayList<Integer> getListId (){
        return listId;
    }

    public ArrayList<String> getListNomi (){
        return listNomi;
    }

    public ArrayList<String> getListUrl (){
        return listUrl;
    }








}
