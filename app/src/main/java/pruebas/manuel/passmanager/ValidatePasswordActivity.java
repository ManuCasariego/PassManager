package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class ValidatePasswordActivity extends Activity {

    private String password;

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
        //validar que la contraseña sea igual a la que nos pasaron, finalizar
    }

    private void inicializarComponentes() {
        //inicializar como siempre los componentes
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}
