package pruebas.manuel.passmanager;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import pruebas.manuel.passmanager.util.DataBaseManager;
import pruebas.manuel.passmanager.util.UsersListAdapter;
import pruebas.manuel.passmanager.util.Usuario;

public class FragmentMain extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final int REQUEST_CODE_ADD = 123;
    private static final int REQUEST_CODE_VIEW = 124;
    private static final int REQUEST_CODE_EDIT = 125;
    private static final int REQUEST_CODE_ADD_MASTERPASSWORD = 126;
    private static final int REQUEST_CODE_VALIDATE_MASTERPASSWORD = 127;
    private static final int REQUEST_CODE_MENU_CONTEXTUAL = 128;

    private static final String PREF_NAME = "masterPassword";

    private View rootView;
    private DataBaseManager db;
    private Cursor cursor;
    private ListView listView;
    private FloatingActionButton fab;

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    private String masterPassword;
    private long mLastClickTime = 0;

    private ArrayAdapter<Usuario> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new UsersListAdapter(getActivity(), new ArrayList<Usuario>());
        inicializarSharedPreferences();
        return rootView;
    }

    private void cargarDatos() {
        inicializarBaseDeDatos();
        inicializarComponentes();
        aniadirFab();
    }

    private void inicializarSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        masterPassword = sharedPreferences.getString(PREF_NAME, "");
        if (masterPassword.equals("")) {
            Intent intent = new Intent(rootView.getContext(), SetPassWordActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MASTERPASSWORD);
        } else {
            Intent intent = new Intent(rootView.getContext(), ValidatePasswordActivity.class);
            intent.putExtra(AddActivity.PASSWORD, masterPassword);
            startActivityForResult(intent, REQUEST_CODE_VALIDATE_MASTERPASSWORD);
        }

    }

    private void aniadirFab() {
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
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
        adapter.clear();
        if (cursor.moveToFirst()) {
            do {
                usuarioActual = new Usuario();
                usuarioActual.setId(cursor.getString(0));
                usuarioActual.setService(cursor.getString(1));
                usuarioActual.setURL(cursor.getString(2));
                usuarioActual.setUserName(cursor.getString(3));
                usuarioActual.setPassword(cursor.getString(4));
                usuarios.add(usuarioActual);
                adapter.add(usuarioActual);
            } while (cursor.moveToNext());
        }
    }

    private void inicializarComponentes() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    private void actualizarListView() {
        inicializarBaseDeDatos();
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
        } else if (requestCode == REQUEST_CODE_VIEW) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent(rootView.getContext(), AddActivity.class);

                i.putExtra(AddActivity.SERVICE, usuarioActual.getService());
                i.putExtra(AddActivity.URL, usuarioActual.getURL());
                i.putExtra(AddActivity.USERNAME, usuarioActual.getUserName());
                i.putExtra(AddActivity.PASSWORD, usuarioActual.getPassword());

                startActivityForResult(i, REQUEST_CODE_EDIT);
            }
        } else if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                String id = usuarioActual.getId();
                String service = data.getStringExtra(AddActivity.SERVICE);
                String url = data.getStringExtra(AddActivity.URL);
                String userName = data.getStringExtra(AddActivity.USERNAME);
                String password = data.getStringExtra(AddActivity.PASSWORD);

                db.modificar(service, url, userName, password, id);
                actualizarListView();
            } else if (resultCode == AddActivity.RESULT_DELETE) {
                borrarUsuario(usuarioActual);
            }
        } else if (requestCode == REQUEST_CODE_ADD_MASTERPASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                //aca habria que seguir con el oncreate ya que como cargue las contras por debajo cagada
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putString(PREF_NAME, data.getStringExtra(AddActivity.PASSWORD));
                prefsEditor.commit();
                cargarDatos();
            }
        } else if (requestCode == REQUEST_CODE_VALIDATE_MASTERPASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                cargarDatos();
            }
        } else if (requestCode == REQUEST_CODE_MENU_CONTEXTUAL) {
            if (resultCode == MenuContextualActivity.RESULT_DELETE) {
                borrarUsuario(usuarioActual);
            } else if (resultCode == MenuContextualActivity.RESULT_EDIT) {
                Intent i = new Intent(rootView.getContext(), AddActivity.class);

                i.putExtra(AddActivity.SERVICE, usuarioActual.getService());
                i.putExtra(AddActivity.URL, usuarioActual.getURL());
                i.putExtra(AddActivity.USERNAME, usuarioActual.getUserName());
                i.putExtra(AddActivity.PASSWORD, usuarioActual.getPassword());

                startActivityForResult(i, REQUEST_CODE_EDIT);
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        usuarioActual = usuarios.get(position);
        Intent intent = new Intent(rootView.getContext(), MenuContextualActivity.class);

        intent.putExtra(AddActivity.SERVICE, usuarioActual.getService());
        intent.putExtra(AddActivity.URL, usuarioActual.getURL());
        intent.putExtra(AddActivity.USERNAME, usuarioActual.getUserName());
        intent.putExtra(AddActivity.PASSWORD, usuarioActual.getPassword());

        startActivityForResult(intent, REQUEST_CODE_MENU_CONTEXTUAL);
        return true;
    }

    public void borrarUsuario(Usuario usuario) {
        String id = usuario.getId();
        db.eliminar(id);
        actualizarListView();
    }
}
