package com.example.cdsofttag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private  Context context;
    public static final String  DATABASE_NAME = "eaton.db";
    public static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


//    CREATE TABLE clientes ( cli_id INTEGER NOT NULL AUTOINCREMENT, cli_nombre TEXT, PRIMARY KEY(cli_id));
//
//    CREATE TABLE embarque (	emb_id	INTEGER NOT NULL AUTOINCREMENT,emb_fecha TEXT,emb_empleado TEXT,emb_cliente	INTEGER, PRIMARY KEY(emb_id), FOREIGN KEY(emb_cliente) REFERENCES clientes(cli_id));
//
//    CREATE TABLE pieza (pie_id INTEGER NOT NULL AUTOINCREMENT, pie_emb_id INTEGER, pie_num_pte TEXT, pie_cant INTEGER, FOREIGN KEY(pie_emb_id) REFERENCES embarque(emb_id), PRIMARY KEY(pie_id));
       @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 ="CREATE TABLE clientes (cli_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT , cli_nombre TEXT);";
        String query2 = "CREATE TABLE embarque (emb_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,emb_fecha TEXT,emb_hora TEXT,emb_empleado TEXT,emb_cliente INTEGER, FOREIGN KEY(emb_cliente) REFERENCES clientes(cli_id));";
        String query3 =  "CREATE TABLE pieza (pie_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "pie_serial_eaton TEXT , pie_serial_prov TEXT ,pie_emb_id INTEGER, pie_num_pte TEXT, pie_gonogo TEXT, FOREIGN KEY(pie_emb_id) REFERENCES embarque(emb_id));";
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS embarque");
        db.execSQL("DROP TABLE IF EXISTS pieza");

        onCreate(db);
    }

    void addCliente (String nombre)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("cli_nombre",nombre);
        long result = db.insert("clientes",null,cv);
        if (result == -1)
        {//
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else
        {
            //Toast.makeText(context, "Succed", Toast.LENGTH_SHORT).show();
        }//

    }
    long   addPieza ( String pie_serial_eaton  , String pie_serial_prov  ,String pie_emb_id , String pie_num_pte , String pie_gonogo )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put("pie_capa",pie_capa);
//        cv.put("pie_numero",pie_numero);
        cv.put("pie_serial_eaton",pie_serial_eaton);
        cv.put("pie_serial_prov",pie_serial_prov);
        cv.put("pie_emb_id",pie_emb_id);
        cv.put("pie_num_pte",pie_num_pte);
        cv.put("pie_gonogo",pie_gonogo);
        long result = db.insert("pieza",null,cv);

        return  result;

    }

    long  addEmbarque (String fecha,String hora, String empleado, int cliente )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("emb_fecha",fecha);
        cv.put("emb_hora",hora);
        cv.put("emb_empleado",empleado);
        cv.put("emb_cliente",cliente);
        long result = db.insert("embarque" ,null,cv);
        return result;
    }

    void addPieza (int embarque,String numpte,int cantidad ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pie_emb_id",embarque);
        cv.put("pie_num_pte",numpte);
        cv.put("pie_cant",cantidad);
        long result = db.insert("pieza",null,cv);

        if (result == -1)
        {
           // Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else
        {
          //  Toast.makeText(context, "Succed", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readCliente (){
        String query = "SELECT cli_nombre from clientes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!= null)
        {
            cursor = db.rawQuery(query,null);
        }
        else {

            Log.e("DBERROR","Error en la base de datos");
        }

            return cursor;


    }

    Cursor readPiezas(String lowerDate,String upperDate){
//        String query =
//                "SELECT emb_fecha,pie_num_pte,pie_serial_prov,pie_serial_eaton," +
//                        "pie_gonogo FROM embarque INNER JOIN pieza ON embarque.emb_id = pieza.pie_emb_id WHERE embarque.emb_id='" + emb_id +  "';";

        String query = "SELECT emb_fecha,emb_hora,pie_num_pte,pie_serial_prov,pie_serial_eaton,pie_gonogo FROM embarque INNER JOIN pieza ON embarque.emb_id = pieza.pie_emb_id WHERE embarque.emb_fecha BETWEEN '"+lowerDate+"' AND '"+upperDate+"';";
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = null;
        if (myDB != null)
        {
            cursor = myDB.rawQuery(query,null);

        }
        else {

            Log.e("DBERROR","Error en la base de datos");
        }
        return cursor;
    }

}

















