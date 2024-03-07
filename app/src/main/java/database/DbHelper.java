package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydb7.sql";
    private static final int DATABASE_VERSION = 1;

    //statement sql per la creazione della tabella
    private static final String DATABASE_CREATE_TABLE =
            "create table sitiweb (" +
            "_id  integer primary key autoincrement, " +
            "nome String, "+
            "url  String)";

    // costruttore
    public DbHelper(Context ctx) {  // IL CONTET GLIELO SI PASSA SOLO PER FORNIRE IL PERSCRSO DELL'APPLIAZIONE ??
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // metodo chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
    }

    // metodo chiamato durante l'upgrade del database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sitiweb");
        onCreate(db);
    }












}
