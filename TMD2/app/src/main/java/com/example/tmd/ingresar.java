package com.example.tmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class ingresar extends AppCompatActivity {
    TextView nombre, medicamento;
    String seguro;
    String url;
    RequestQueue requestQueue;
    String medi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        seguro = getIntent().getStringExtra("dato");
        nombre = findViewById(R.id.paciente);
        medicamento = findViewById(R.id.medicamentos);

        try {
            url = "https://fjhgj.000webhostapp.com/datos/mostrar.php?Seguro=" + seguro + "";

            JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(url, (response) -> {
                JSONObject jsonObject = null;

                for(int i=0; i< response.length(); i++) {
                    try{
                        jsonObject = response.getJSONObject(i);
                        nombre.setText(jsonObject.getString("Nombre"));
                    }
                    catch (JSONException e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }, (error) -> {
                Toast.makeText(this, "Error de conexion", Toast.LENGTH_SHORT).show();
            });


            requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error de Inicio", Toast.LENGTH_SHORT).show();
        }

        Button checar = (Button) findViewById(R.id.buttonEnviar);
        checar.setOnClickListener(view -> openRegistrar());
    }


    public void openRegistrar() {
        medi = medicamento.getText().toString().trim();

        if(medi.length() <= 5) {
            Toast.makeText(this, "Ingrese su medicamento", Toast.LENGTH_SHORT).show();
        }
        else {
            new ingresar.DescargarImagen(ingresar.this).execute(seguro, medi);
        }
    }

    public class DescargarImagen extends AsyncTask<String, Void, String> {

        private WeakReference<Context> context;

        public DescargarImagen(Context context) {
            this.context = new WeakReference<>(context);
        }

        protected String doInBackground(String... params) {
            String registrar_url =  "https://fjhgj.000webhostapp.com/datos/medicamentos.php";
            String resultado = null;

            try {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String seguro = params[0];
                String medicamento = params[1];

                String data = URLEncoder.encode("Seguro", "UTF-8") + "=" + URLEncoder.encode(seguro, "UTF-8")
                        + "&" + URLEncoder.encode("Medicamento", "UTF-8") + "=" + URLEncoder.encode(medicamento, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                resultado = stringBuilder.toString();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Intent i = new Intent(ingresar.this, generarQR.class);
                i.putExtra("seguro", seguro);
                i.putExtra("medicamentos", medi);
                startActivity(i);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultado;
        }

        protected void onPostExecute(String resultado) {
            Toast.makeText(context.get(), resultado, Toast.LENGTH_SHORT).show();

            if (resultado == "Solicitud de medicamentos enviada con éxito") {
                Intent i = new Intent(ingresar.this, generarQR.class);
                i.putExtra("seguro", seguro);
                i.putExtra("medicamentos", medi);
                startActivity(i);
            }
            else if(resultado == "Error vuelva a intentar"){
                Toast.makeText(ingresar.this, "Error vuelva a intentar", Toast.LENGTH_SHORT).show();
            }
        }

    }

}