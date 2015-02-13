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
    public static final String CN_NAME = "nombreusuario";
    public static final String CN_PASSWORD = "contrasenia";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_PASSWORD + " text not null);";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insertar(String nombre, String contra) {
        db.insert(TABLE_NAME, null, contenedor(nombre, contra));
    }

    public ContentValues contenedor(String nombre, String contra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_NAME, nombre);
        contentValues.put(CN_PASSWORD, contra);
        return contentValues;
    }

    public void eliminar(String nombre, String contra) {
        db.delete(TABLE_NAME, CN_NAME + "=? AND " + CN_PASSWORD + "=?", new String[]{nombre, contra});
    }

    public void modificarNombre(String nombre, String contra, String nuevoNombre) {
        db.update(TABLE_NAME, contenedor(nuevoNombre, contra), CN_NAME + "=? AND " + CN_PASSWORD + "=?", new String[]{nombre, contra});
    }

    public void modificarContrasenia(String nombre, String contra, String nuevaContra) {
        db.update(TABLE_NAME, contenedor(nombre, nuevaContra), CN_NAME + "=? AND " + CN_PASSWORD + "=?", new String[]{nombre, contra});

    }

    public Cursor cargarCursorContactos() {
        String[] columnas = new String[]{CN_ID, CN_NAME, CN_PASSWORD};
        return db.query(TABLE_NAME, columnas, null, null, null, null, null);
    }
}
