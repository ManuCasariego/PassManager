package pruebas.manuel.passmanager.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Manuel on 12/02/2015.
 */
public class DataBaseManager {

    public static final String TABLE_NAME = "datos";
    public static final String CN_ID = "_id";
    public static final String CN_SERVICE = "servicio";
    public static final String CN_URL = "url";
    public static final String CN_NAME = "nombreusuario";
    public static final String CN_PASSWORD = "contrasenia";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_SERVICE + " text not null,"
            + CN_URL + " text,"
            + CN_NAME + " text not null,"
            + CN_PASSWORD + " text not null);";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insertar(String servicio, String url, String nombre, String contra) {
        db.insert(TABLE_NAME, null, contenedor(servicio, url, nombre, contra));
    }

    public ContentValues contenedor(String servicio, String url, String nombre, String contra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_SERVICE, servicio);
        contentValues.put(CN_URL, url);
        contentValues.put(CN_NAME, nombre);
        contentValues.put(CN_PASSWORD, contra);
        return contentValues;
    }

    public void eliminar(String id) {
        db.delete(TABLE_NAME, CN_ID + "=?", new String[]{id});
    }

    public void modificar(String servicio, String url, String nombre, String contra, String id) {
        db.update(TABLE_NAME, contenedor(servicio, url, nombre, contra), CN_ID + "=?", new String[]{id});
    }
/*
    public void modificarContrasenia(String nombre, String contra, String nuevaContra) {
        db.update(TABLE_NAME, contenedor(nombre, nuevaContra), CN_NAME + "=? AND " + CN_PASSWORD + "=?", new String[]{nombre, contra});

    }*/

    public Cursor cargarCursorContactos() {
        String[] columnas = new String[]{CN_ID, CN_SERVICE, CN_URL, CN_NAME, CN_PASSWORD};
        return db.query(TABLE_NAME, columnas, null, null, null, null, null);
    }
}
