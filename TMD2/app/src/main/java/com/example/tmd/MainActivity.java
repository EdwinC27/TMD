package com.example.tmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registrar = (Button) findViewById(R.id.btRegistrarse);
        registrar.setOnClickListener(view -> openRegistrar());

        Button iniciar = (Button) findViewById(R.id.btIniciar);
        iniciar.setOnClickListener(view -> openIniciar());;

        Button ambulancia = (Button)  findViewById(R.id.btAmbulancia);
        ambulancia.setOnClickListener(view -> openAmbulancia());

        Button tyc = (Button) findViewById(R.id.tyc);
        tyc.setOnClickListener(view -> openTYC());

    }

    public void openRegistrar() {
        Intent i = new Intent(this, registra.class);
        startActivity(i);
    }

    public void openIniciar() {
        Intent i = new Intent(this, iniciar.class);
        startActivity(i);
    }

    public void openAmbulancia() {
        Intent i = new Intent(this, mapa.class);
        startActivity(i);
    }

    public void openTYC() {
        Intent i = new Intent(this, TYC.class);
        startActivity(i);
    }
}