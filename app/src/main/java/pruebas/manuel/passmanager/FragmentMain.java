package pruebas.manuel.passmanager;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import pruebas.manuel.passmanager.util.DataBaseManager;

/**
 * Created by Manuel on 12/02/2015.
 */
public class FragmentMain extends Fragment implements AdapterView.OnItemClickListener{
    /*private Button button;
        private CheckBox checkBox;*/
    private View rootView;
    private DataBaseManager db;
    private Cursor cursor;
    private ListView listView;
    private SimpleCursorAdapter cursorAdapter;
    private FloatingActionButton fab;

    private static final int REQUEST_CODE = 123;

    private String[] from;
    private int[] to;

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
                Intent i = new Intent(rootView.getContext(), AddActivity.class);
                startActivityForResult(i, REQUEST_CODE);

                //PopupWindow asdf = new PopupWindow(30,30);
            }
        });
        fab.attachToListView(listView);
    }

    private void inicializarBaseDeDatos() {
        db = new DataBaseManager(rootView.getContext());
        cursor = db.cargarCursorContactos();
    }

    private void inicializarComponentes() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        from = new String[]{db.CN_NAME, db.CN_PASSWORD};
        to = new int[]{R.id.textViewNombreUsuario, R.id.textViewContra};
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
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String userName = data.getStringExtra(AddActivity.USERNAME);
                String password = data.getStringExtra(AddActivity.PASSWORD);
                db.insertar(userName, password);
                actualizarListView();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(rootView.getContext(), "Se ha cancelado", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String userName = ((TextView) view.findViewById(R.id.textViewNombreUsuario)).getText().toString();
        String password = ((TextView) view.findViewById(R.id.textViewContra)).getText().toString();

        Toast.makeText(rootView.getContext(), userName+password, Toast.LENGTH_SHORT).show();


    }
}