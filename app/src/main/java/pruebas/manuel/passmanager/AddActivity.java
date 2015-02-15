package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import at.markushi.ui.CircleButton;


public class AddActivity extends Activity {

    public static final String SERVICE = "service";
    public static final String URL = "url";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    public static final int RESULT_DELETE = 234;

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

    private CircleButton buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        Intent intent = getIntent();

        String service = intent.getStringExtra(AddActivity.SERVICE);
        String url = intent.getStringExtra(AddActivity.URL);
        String userName = intent.getStringExtra(AddActivity.USERNAME);
        String password = intent.getStringExtra(AddActivity.PASSWORD);

        btnGuardar = (Button) findViewById(R.id.btnGuardarAdd);
        btnCancelar = (Button) findViewById(R.id.btnCancelarAdd);

        editTextService = (EditText) findViewById(R.id.editTextServiceAdd);
        editTextUrl = (EditText) findViewById(R.id.editTextUrlAdd);
        editTextUserName = (EditText) findViewById(R.id.editTextUserNameAdd);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordAdd);

        editTextService.setText(service);
        editTextUrl.setText(url);
        editTextUserName.setText(userName);
        editTextPassword.setText(password);

        imageViewService = (ImageView) findViewById(R.id.imageViewServicioAdd);
        imageViewUrl = (ImageView) findViewById(R.id.imageViewUrlAdd);
        imageViewUser = (ImageView) findViewById(R.id.imageViewUsuarioAdd);
        imageViewPassword = (ImageView) findViewById(R.id.imageViewContraAdd);

        returnIntent = new Intent();

        buttonDelete = (CircleButton) findViewById(R.id.buttonDeleteAdd);
        if (service == null) {
            buttonDelete.setVisibility(View.INVISIBLE);
        } else {
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_DELETE, returnIntent);
                    finish();
                }
            });
        }

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

        editTextUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageViewUrl.setImageResource(R.mipmap.ic_actino_url_orange);
                } else {
                    imageViewUrl.setImageResource(R.drawable.ic_action_url);
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
                    url = formalizarURL(url);
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

    private String formalizarURL(String url) {
        String respuesta = url.toLowerCase();
        if (respuesta != null) {
            if (!respuesta.equals("") && !respuesta.startsWith("http://") && !respuesta.startsWith("https://")) {
                respuesta = "http://" + respuesta;
            }
        } else {
            respuesta = "";
        }
        return respuesta;
    }

    private void salir() {
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }




}
