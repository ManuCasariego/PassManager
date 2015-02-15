package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class SetPassWordActivity extends Activity {

    private EditText editTextPassword1;
    private EditText editTextPassword2;

    private ImageView imageViewPassword1;
    private ImageView imageViewPassword2;

    private Button buttonGuardar;

    private boolean firstPassOk = false;
    private boolean secondPassOk = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass_word);
        this.setFinishOnTouchOutside(false);
        inicializarComponentes();
        aniadirListener();
    }

    private void aniadirListener() {
        //Dentro del listener comprobar que contraseña no nula blabla y enviar en intent de vuelta
        //acordarse de poner el ok que si no cagada

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPassOk) {
                    if (secondPassOk) {
                        Intent intent = new Intent();
                        intent.putExtra(AddActivity.PASSWORD, editTextPassword1.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(SetPassWordActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetPassWordActivity.this, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("cuenta caracteres", Integer.toString(count + start + before));
                String texto1 = s.toString();
                String texto2 = editTextPassword2.getText().toString();
                if (texto1.length() >= 6) {
                    imageViewPassword1.setImageResource(R.mipmap.ic_action_password_orange);
                    firstPassOk = true;
                    if(texto1.equals(texto2)){
                        imageViewPassword2.setImageResource(R.mipmap.ic_action_password_orange);
                        secondPassOk = true;
                    }
                } else {
                    imageViewPassword1.setImageResource(R.drawable.ic_action_password);
                    imageViewPassword2.setImageResource(R.drawable.ic_action_password);
                    firstPassOk = false;
                    secondPassOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String texto2 = s.toString();
                String texto1 = editTextPassword1.getText().toString();
                if (texto1.equals(texto2) && firstPassOk) {
                    imageViewPassword2.setImageResource(R.mipmap.ic_action_password_orange);
                    secondPassOk = true;
                } else {
                    imageViewPassword2.setImageResource(R.drawable.ic_action_password);
                    secondPassOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void inicializarComponentes() {
        editTextPassword1 = (EditText) findViewById(R.id.editTextPasswordSet);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPasswordSet2);

        imageViewPassword1 = (ImageView) findViewById(R.id.imageViewContraSet);
        imageViewPassword2 = (ImageView) findViewById(R.id.imageViewContraSet2);

        buttonGuardar = (Button) findViewById(R.id.buttonGuardarSet);
    }


    @Override
    public void onBackPressed() {
        //nothing
    }
}
