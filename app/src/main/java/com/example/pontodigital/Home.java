package com.example.pontodigital;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(clickBack);
    }

    private View.OnClickListener clickBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent voltarIntent = new Intent(Home.this, Login.class);
                startActivity(voltarIntent);
                finish();
        }
    };
}
