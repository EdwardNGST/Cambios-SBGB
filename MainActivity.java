package com.example.alan_.sbgb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alan_.sbgb.models.database.DatabaseHelper;
import com.example.alan_.sbgb.models.extra.AndroidStudioLIB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    AndroidStudioLIB extra;

    Button btnAgree;
    EditText txtUser;
    EditText txtPassword;
    Button btnNewUser;
    Button btnPanelExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        myDb = new DatabaseHelper(this);
        extra = new AndroidStudioLIB();

        btnAgree = (Button)findViewById(R.id.btnAgree);
        txtUser = (EditText)findViewById(R.id.txtUser);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnNewUser = (Button)findViewById(R.id.btnNewUser);
        btnPanelExtra = (Button)findViewById(R.id.btnPanelExtra);
        login();
        user();
        panel();
    }
    private static final String TAG = "MyActivity";
    private void login() {
        btnAgree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validateUser(txtUser.getText().toString(), txtPassword.getText().toString())){
                            if(loginUser()){
                                saveOnPreferences();
                                goToPanel();
                            }else{
                                Toast.makeText(MainActivity.this,"Usuario no encontrado o contraseña incorrecta",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        /*Cursor res = myDb.validateUser(txtUser.getText().toString(),txtPassword.getText().toString());
                        if(res.getCount() == 0) {
                            Toast.makeText(MainActivity.this,"Usuario no encontrado",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            Toast.makeText(MainActivity.this,"Usuario encontrado",Toast.LENGTH_LONG).show();
                            return;
                        }*/
                    }
                }
        );
    }

    private void saveOnPreferences(String user, String password){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user", user);
        editor.putString("pass", password);
        editor.apply();
    }

    private boolean validateUser(String user, String password){
        if(TextUtils.isEmpty(user)||TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Ingrese todos los datos correctamente",Toast.LENGTH_LONG).show();
            return false;
        }else{
            String user = txtUser.getText().toString();
            String password = txtPassword.getText().toString();
            Toast.makeText(MainActivity.this,"Datos correctos",Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private void user() {
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), com.example.alan_.sbgb.activities.users.new_user.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void panel() {
        btnPanelExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), com.example.alan_.sbgb.models.extra.PanelExtra.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void goToPanel() {
        Intent intent = new Intent (this, .com.example.alan_.sbgb.models.users.main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
