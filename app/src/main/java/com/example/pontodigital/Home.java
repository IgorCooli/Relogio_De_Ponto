package com.example.pontodigital;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {

    private static final int REQUEST = 1;
    private LocationManager locationManager;
    private Location location;
    private String nome, matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(clickBack);

        Button btnEntrada = findViewById(R.id.btnEntrada);
        Button btnSaida = findViewById(R.id.btnSaida);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome = bundle.getString("nome");
        matricula = bundle.getString("mat");
        TextView id = findViewById(R.id.identificacao);
        id.setText("Nome: " + nome + " - Matrícula: " + matricula);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoDirectory = new File(Environment.getExternalStorageDirectory(), "Fotos");
                if (!photoDirectory.exists()) {
                    photoDirectory.mkdir();
                }
                String photoName = getPhotName();  /*Nome da foto*/
                File imgFile = new File(photoDirectory, photoName);
                //Uri uriPhoto = Uri.fromFile(imgFile);
                Uri uriPhoto = FileProvider.getUriForFile(Home.this, BuildConfig.APPLICATION_ID + ".provider",imgFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(cameraIntent, 0);
            }
        });

        btnSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoDirectory = new File(Environment.getExternalStorageDirectory(), "Fotos");
                if (!photoDirectory.exists()) {
                    photoDirectory.mkdir();
                }
                String photoName = getPhotName();  /*Nome da foto*/
                File imgFile = new File(photoDirectory, photoName);
                //Uri uriPhoto = Uri.fromFile(imgFile);
                Uri uriPhoto = FileProvider.getUriForFile(Home.this, BuildConfig.APPLICATION_ID + ".provider",imgFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(cameraIntent, 0);
            }
        });
    }

    private String getPhotName() {
        String location = getLocation();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return ("Entrada:" + nome + "-" + matricula + "-"+ timestamp + "-" + "coordenadas: " + location);
    }

    private String getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return "ErroNoGPS";

            } else {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                return latitude + "_" + longitude;
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST:
                getLocation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getPhotName();
        }
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
