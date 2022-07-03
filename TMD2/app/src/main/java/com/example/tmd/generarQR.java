package com.example.tmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class generarQR extends AppCompatActivity {
    String medicamentos, seguro, combinacion;
    ImageView imr;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_qr);

        medicamentos = getIntent().getStringExtra("medicamentos");
        seguro = getIntent().getStringExtra("seguro");

        boton = (Button) findViewById(R.id.bt_salir);

        combinacion = seguro + "-" + medicamentos;

        imr = findViewById(R.id.gCcode);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(combinacion, BarcodeFormat.QR_CODE, 700, 700);
            imr.setImageBitmap(bitmap);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}