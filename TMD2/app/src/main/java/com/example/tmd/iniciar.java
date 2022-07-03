package com.example.tmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class iniciar extends AppCompatActivity {
    EditText seguro, contrasena;
    String seguro2, contrasena2;
    String url="https://fjhgj.000webhostapp.com/datos/iniciar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);

        seguro = findViewById(R.id.numeroSeguro);
        contrasena = findViewById(R.id.contrasena);
    }

    public void Login(View view) {

        if(seguro.getText().toString().equals("")){
            Toast.makeText(this, "Enter Su Seguro", Toast.LENGTH_SHORT).show();
        }
        else if(seguro.length() > 11 || seguro.length() < 11) {
            Toast.makeText(this, "Texo ingresado en el campo 'Seguro' incorrecto", Toast.LENGTH_SHORT).show();
        }
        else if(contrasena.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Su contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espere.....");

            progressDialog.show();

            seguro2 = seguro.getText().toString().trim();
            contrasena2 = contrasena.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Toast.makeText(iniciar.this, response, Toast.LENGTH_SHORT).show();

                    if(response.equalsIgnoreCase("Ingresaste Correctamente")){
                        seguro.setText("");
                        contrasena.setText("");

                        Intent i = new Intent(iniciar.this, ingresar.class);
                        i.putExtra("dato", seguro2);
                        startActivity(i);
                        Toast.makeText(iniciar.this, response, Toast.LENGTH_SHORT).show();
                    }


                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(iniciar.this, "Error vuelva a intentar", Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Seguro",seguro2);
                    params.put("Contrasena",contrasena2);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(iniciar.this);
            requestQueue.add(request);
        }
    }

}

