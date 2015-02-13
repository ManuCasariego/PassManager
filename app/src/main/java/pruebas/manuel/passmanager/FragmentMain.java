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

import com.melnykov.fab.FloatingActionButton;

import pruebas.manuel.passmanager.util.DataBaseManager;

/**
 * Created by Manuel on 12/02/2015.
 */
public class FragmentMain extends Fragment implements AdapterView.OnItemClickListener {

    private static final int REQUEST_CODE = 123;

    private View rootView;
    private DataBaseManager db;
    private Cursor cursor;
    private ListView listView;
    private SimpleCursorAdapter cursorAdapter;
    private FloatingActionButton fab;
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
        from = new String[]{DataBaseManager.CN_SERVICE, DataBaseManager.CN_NAME, DataBaseManager.CN_PASSWORD};
        to = new int[]{R.id.textViewServicio, R.id.textViewNombreUsuario, R.id.textViewContra};
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
                String service = data.getStringExtra(AddActivity.SERVICE);
                String userName = data.getStringExtra(AddActivity.USERNAME);
                String password = data.getStringExtra(AddActivity.PASSWORD);

                db.insertar(service, userName, password);
                actualizarListView();
            } else if (resultCode == Activity.RESULT_CANCELED) {}
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String service = ((TextView) view.findViewById(R.id.textViewServicio)).getText().toString();
        String userName = ((TextView) view.findViewById(R.id.textViewNombreUsuario)).getText().toString();
        String password = ((TextView) view.findViewById(R.id.textViewContra)).getText().toString();

        Intent intent = new Intent(rootView.getContext(), ViewActivity.class);
        intent.putExtra(AddActivity.SERVICE, service);
        intent.putExtra(AddActivity.USERNAME, userName);
        intent.putExtra(AddActivity.PASSWORD, password);
        startActivity(intent);
    }
}
