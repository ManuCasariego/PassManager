package pruebas.manuel.passmanager;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import pruebas.manuel.passmanager.util.DataBaseManager;
import pruebas.manuel.passmanager.util.Usuario;

/**
 * Created by Manuel on 12/02/2015.
 */
public class FragmentMain extends Fragment implements AdapterView.OnItemClickListener {

    private static final int REQUEST_CODE_ADD = 123;
    private static final int REQUEST_CODE_VIEW = 124;
    private static final int REQUEST_CODE_EDIT = 125;

    private View rootView;
    private DataBaseManager db;
    private Cursor cursor;
    private ListView listView;
    private SimpleCursorAdapter cursorAdapter;
    private FloatingActionButton fab;
    private String[] from;
    private int[] to;

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    private long mLastClickTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        inicializarBaseDeDatos();
        inicializarComponentes();
        aniadirFab();
        return rootView;
    }

    private void aniadirFab() {
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent i = new Intent(rootView.getContext(), AddActivity.class);
                startActivityForResult(i, REQUEST_CODE_ADD);
            }
        });
        fab.attachToListView(listView);
    }

    private void inicializarBaseDeDatos() {
        db = new DataBaseManager(rootView.getContext());
        cursor = db.cargarCursorContactos();
        actualizarArrayList();
    }

    private void actualizarArrayList() {
        usuarios.clear();
        if(cursor.moveToFirst()){
            do{
                usuarioActual = new Usuario();
                usuarioActual.setId(cursor.getString(0));
                usuarioActual.setService(cursor.getString(1));
                usuarioActual.setURL(cursor.getString(2));
                usuarioActual.setUserName(cursor.getString(3));
                usuarioActual.setPassword(cursor.getString(4));
                usuarios.add(usuarioActual);
            } while(cursor.moveToNext());
        }
    }

    private void inicializarComponentes() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        from = new String[]{DataBaseManager.CN_SERVICE, DataBaseManager.CN_NAME};
        to = new int[]{R.id.textViewServicio, R.id.textViewNombreUsuario};
        cursorAdapter = new SimpleCursorAdapter(rootView.getContext(), R.layout.list_item_personalizado, cursor, from, to, 0);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(this);

    }

    private void actualizarListView() {
        inicializarBaseDeDatos();
        cursorAdapter = new SimpleCursorAdapter(rootView.getContext(), R.layout.list_item_personalizado, cursor, from, to, 0);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                String service = data.getStringExtra(AddActivity.SERVICE);
                String url = data.getStringExtra(AddActivity.URL);
                String userName = data.getStringExtra(AddActivity.USERNAME);
                String password = data.getStringExtra(AddActivity.PASSWORD);

                db.insertar(service, url, userName, password);
                actualizarListView();
            }
        }
        else if(requestCode == REQUEST_CODE_VIEW){
            if(resultCode == Activity.RESULT_OK){
                Intent i = new Intent(rootView.getContext(), AddActivity.class);

                i.putExtra(AddActivity.SERVICE, usuarioActual.getService());
                i.putExtra(AddActivity.URL, usuarioActual.getURL());
                i.putExtra(AddActivity.USERNAME, usuarioActual.getUserName());
                i.putExtra(AddActivity.PASSWORD, usuarioActual.getPassword());

                startActivityForResult(i, REQUEST_CODE_EDIT);
            }
        }
        else if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode == Activity.RESULT_OK){
                String id = usuarioActual.getId();
                String service = data.getStringExtra(AddActivity.SERVICE);
                String url = data.getStringExtra(AddActivity.URL);
                String userName = data.getStringExtra(AddActivity.USERNAME);
                String password = data.getStringExtra(AddActivity.PASSWORD);

                db.modificar(service, url, userName, password, id);
                actualizarListView();
            }
            else if(resultCode == AddActivity.RESULT_DELETE){
                String id = usuarioActual.getId();
                db.eliminar(id);
                actualizarListView();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        usuarioActual = usuarios.get(position);

        String service = ((TextView) view.findViewById(R.id.textViewServicio)).getText().toString();
        String userName = ((TextView) view.findViewById(R.id.textViewNombreUsuario)).getText().toString();

        Intent intent = new Intent(rootView.getContext(), ViewActivity.class);

        intent.putExtra(AddActivity.SERVICE, service);
        intent.putExtra(AddActivity.URL, usuarioActual.getURL());
        intent.putExtra(AddActivity.USERNAME, userName);
        intent.putExtra(AddActivity.PASSWORD, usuarioActual.getPassword());

        startActivityForResult(intent, REQUEST_CODE_VIEW);
    }
}
