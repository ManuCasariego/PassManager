package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AddActivity extends Activity {

    public static final String SERVICE = "service";
    public static final String URL = "url";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";

    private Intent returnIntent;
    private Button btnGuardar;
    private Button btnCancelar;

    private EditText editTextService;
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;

    private ImageView imageViewService;
    private ImageView imageViewUrl;
    private ImageView imageViewUser;
    private ImageView imageViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        btnGuardar = (Button) findViewById(R.id.btnGuardarAdd);
        btnCancelar = (Button) findViewById(R.id.btnCancelarAdd);

        editTextService = (EditText) findViewById(R.id.editTextServiceAdd);
        editTextUrl = (EditText) findViewById(R.id.editTextUrlAdd);
        editTextUserName = (EditText) findViewById(R.id.editTextUserNameAdd);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordAdd);

        imageViewService = (ImageView) findViewById(R.id.imageViewServicioAdd);
        imageViewUrl = (ImageView) findViewById(R.id.imageViewUrlAdd);
        imageViewUser = (ImageView) findViewById(R.id.imageViewUsuarioAdd);
        imageViewPassword = (ImageView) findViewById(R.id.imageViewContraAdd);

        returnIntent = new Intent();

        editTextService.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageViewService.setImageResource(R.mipmap.ic_action_service_orange);
                } else {
                    imageViewService.setImageResource(R.drawable.ic_action_service);
                }
            }
        });

        editTextUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageViewUser.setImageResource(R.mipmap.ic_action_user_orange);
                } else {
                    imageViewUser.setImageResource(R.drawable.ic_action_user);
                }
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageViewPassword.setImageResource(R.mipmap.ic_action_password_orange);
                } else {
                    imageViewPassword.setImageResource(R.drawable.ic_action_password);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String service = editTextService.getText().toString();
                String url = editTextUrl.getText().toString();
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if (service.equals("")) {
                    Toast.makeText(AddActivity.this, "El servicio no puede ser nulo", Toast.LENGTH_SHORT).show();
                    editTextService.requestFocus();
                } else if (userName.equals("")) {
                    Toast.makeText(AddActivity.this, "El usuario no puede ser nulo", Toast.LENGTH_SHORT).show();
                    editTextUserName.requestFocus();
                } else if (password.equals("")) {
                    Toast.makeText(AddActivity.this, "La constraseña no puede ser nula", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                } else {

                    returnIntent.putExtra(SERVICE, service);
                    returnIntent.putExtra(URL, url);
                    returnIntent.putExtra(USERNAME, userName);
                    returnIntent.putExtra(PASSWORD, password);

                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

    }

    private void salir() {
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


}
