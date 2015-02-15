package pruebas.manuel.passmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.markushi.ui.CircleButton;

public class ViewActivity extends Activity {

    private TextView textViewService;
    private TextView textViewUserName;
    private TextView textViewPassword;

    private CircleButton btnEdit;
    private CircleButton btnURL;
    private CircleButton btnUserName;
    private CircleButton btnPassword;

    private String service;
    private String url;
    private String userName;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        inicializarComponentes();
        listeners();
    }

    private void listeners() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", userName);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ViewActivity.this, R.string.userCopiedClipboard, Toast.LENGTH_SHORT).show();
            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", password);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ViewActivity.this, R.string.passwordCopiedClipboard, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarComponentes() {
        Intent intent = getIntent();

        service = intent.getStringExtra(AddActivity.SERVICE);
        url = intent.getStringExtra(AddActivity.URL);
        userName = intent.getStringExtra(AddActivity.USERNAME);
        password = intent.getStringExtra(AddActivity.PASSWORD);

        textViewService = (TextView) findViewById(R.id.textViewServiceView);
        textViewUserName = (TextView) findViewById(R.id.textViewUserNameView);
        textViewPassword = (TextView) findViewById(R.id.textViewPasswordView);

        textViewService.setText(service);
        textViewUserName.setText(userName);
        textViewPassword.setText(password);

        btnEdit = (CircleButton) findViewById(R.id.buttonEditView);
        btnURL = (CircleButton) findViewById(R.id.buttonURLView);
        btnUserName = (CircleButton) findViewById(R.id.buttonUserNameView);
        btnPassword = (CircleButton) findViewById(R.id.buttonPasswordView);

        if (url.equals("")) {
            btnURL.setVisibility(View.INVISIBLE);
        }
    }


}
