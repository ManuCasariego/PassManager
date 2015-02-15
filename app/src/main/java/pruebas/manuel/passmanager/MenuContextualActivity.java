package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.markushi.ui.CircleButton;


public class MenuContextualActivity extends Activity {
    public static final int RESULT_DELETE = 300;
    public static final int RESULT_EDIT = 301;

    private LinearLayout estrucuturaUrl;

    private TextView textViewNombre;

    private CircleButton buttonOpenURL;
    private CircleButton buttonCopyUserName;
    private CircleButton buttonCopyPassword;
    private CircleButton buttonEditUser;
    private CircleButton buttonDeleteUser;

    private String service, userName, password, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contextual);
        inicializarComponentes();
        aniadirListener();
    }

    private void aniadirListener() {
        buttonOpenURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        buttonCopyUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", userName);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MenuContextualActivity.this, "Usuario copiado al portapapeles", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", password);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MenuContextualActivity.this, "Contraseña copiada al portapapeles", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_EDIT, intent);
                finish();
            }
        });

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_DELETE, intent);
                finish();
            }
        });
    }

    private void inicializarComponentes() {
        Intent intent = getIntent();

        estrucuturaUrl = (LinearLayout) findViewById(R.id.estructuraUrl);

        service = intent.getStringExtra(AddActivity.SERVICE);
        url = intent.getStringExtra(AddActivity.URL);
        userName = intent.getStringExtra(AddActivity.USERNAME);
        password = intent.getStringExtra(AddActivity.PASSWORD);

        estrucuturaUrl = (LinearLayout) findViewById(R.id.estructuraUrl);
        if (url.equals("")) {
            estrucuturaUrl.setVisibility(View.INVISIBLE);
            estrucuturaUrl.getLayoutParams().height = 0;
        }

        textViewNombre = (TextView) findViewById(R.id.textViewUserNameMenuContextual);

        textViewNombre.setText(service + " (" + userName + ")");

        buttonOpenURL = (CircleButton) findViewById(R.id.buttonOpenURLMenuContextual);
        buttonCopyUserName = (CircleButton) findViewById(R.id.buttonCopyUserMenuContextual);
        buttonCopyPassword = (CircleButton) findViewById(R.id.buttonCopyPasswordMenuContextual);
        buttonEditUser = (CircleButton) findViewById(R.id.buttonEditMenuContextual);
        buttonDeleteUser = (CircleButton) findViewById(R.id.buttonDeleteMenuContextual);
    }


}
