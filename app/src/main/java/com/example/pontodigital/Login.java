package com.example.pontodigital;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(clicknext);
    }

    private View.OnClickListener clicknext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent homeIntent = new Intent(Login.this, Home.class);
            EditText nome = findViewById(R.id.inNome);
            EditText mat = findViewById(R.id.inMat);
            String txtNome = nome.getText().toString();
            String txtMat = mat.getText().toString();
            if (!txtNome.matches("") && !txtMat.matches("")) {
                Bundle bundle = new Bundle();
                bundle.putString("nome", txtNome.toUpperCase());
                bundle.putString("mat", txtMat);
                homeIntent.putExtras(bundle);
                startActivity(homeIntent);
                finish();
            }
        }
    };

}
