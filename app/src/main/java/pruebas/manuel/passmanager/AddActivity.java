package pruebas.manuel.passmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddActivity extends ActionBarActivity {


    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    private Intent returnIntent;
    private Button btnGuardar;
    private Button btnCancelar;
    private EditText editTextUserName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        inicializarComponentes();
    }

    private void inicializarComponentes() {
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        returnIntent = new Intent();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if(userName.equals("")){
                    Toast.makeText(AddActivity.this, "El usuario no puede ser nulo", Toast.LENGTH_SHORT).show();
                    editTextUserName.requestFocus();
                } else if(password.equals("")){
                    Toast.makeText(AddActivity.this, "La constraseña no puede ser nula", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                }
                else{
                    returnIntent.putExtra(USERNAME ,userName);
                    returnIntent.putExtra(PASSWORD ,password);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

    }

    private void salir() {
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            salir();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
