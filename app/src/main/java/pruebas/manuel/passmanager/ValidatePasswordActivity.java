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


public class ValidatePasswordActivity extends Activity {

    private String password;

    private EditText editTextPassword;

    private ImageView imageViewPassword;

    private Button buttonAcceder;

    private boolean passwordOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_password);
        this.setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        password = intent.getStringExtra(AddActivity.PASSWORD);
        inicializarComponentes();
        aniadirListener();
    }

    private void aniadirListener() {
        buttonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordOk) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(ValidatePasswordActivity.this, R.string.wrongPassword, Toast.LENGTH_SHORT).show();
                    editTextPassword.getText().clear();
                }
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(password)) {
                    imageViewPassword.setImageResource(R.mipmap.ic_action_password_orange);
                    passwordOk = true;
                } else {
                    imageViewPassword.setImageResource(R.drawable.ic_action_password);
                    passwordOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void inicializarComponentes() {
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordValidate);

        imageViewPassword = (ImageView) findViewById(R.id.imageViewContraValidate);

        buttonAcceder = (Button) findViewById(R.id.buttonAccederValidate);
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}
