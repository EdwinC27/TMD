package com.example.tmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class registra extends AppCompatActivity {
    CheckBox tyc;
    Button boton;
    EditText nuSeguro, nuClinica, Nombre, fechaNacimiento, Apellido, Dirrecion, CP, numeroTelefono, Contrasena, Corrreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        boton = (Button) findViewById(R.id.registrar);
        nuSeguro = (EditText) findViewById(R.id.nuSeguroRegistro);
        nuClinica = (EditText) findViewById(R.id.nuClinicaRegistro);
        Nombre = (EditText) findViewById(R.id.nombreRegistro);
        fechaNacimiento = (EditText) findViewById(R.id.fechaNacimientoRegistro);
        Apellido = (EditText) findViewById(R.id.apellidoRegistro);
        Dirrecion = (EditText) findViewById(R.id.dirrecionRegistro);
        CP = (EditText) findViewById(R.id.cpRegistro);
        numeroTelefono = (EditText) findViewById(R.id.nuTelefonoRegistro);
        tyc = (CheckBox) findViewById(R.id.TYC);
        Contrasena = (EditText) findViewById(R.id.contrasena);
        Corrreo = (EditText) findViewById(R.id.correo);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = Nombre.getText().toString();
                String apellido = Apellido.getText().toString();
                String clinica = nuClinica.getText().toString();
                String seguro = nuSeguro.getText().toString();
                String nacimiento = fechaNacimiento.getText().toString();
                String dirrecion = Dirrecion.getText().toString();
                String cp = CP.getText().toString();
                String telefono = numeroTelefono.getText().toString();
                String contrasena = Contrasena.getText().toString();
                String correo = Corrreo.getText().toString();

                if(nombre.length() == 0 || apellido.length() == 0 || clinica.length() == 0 || seguro.length() == 0 || nacimiento.length() == 0 || dirrecion.length() == 0 || cp.length() == 0 || telefono.length() == 0 || contrasena.length() == 0 || correo.length() == 0) {
                    Toast.makeText(registra.this, "Llene todo los campos, para poder tener un registro exitoso", Toast.LENGTH_SHORT).show();
                }
                else if(nombre.length() > 50 || nombre.length() < 4) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Nombre' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(apellido.length() > 50 || apellido.length() < 4) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Apellido' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(clinica.length() > 10 || clinica.length() < 0) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Clinica' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(seguro.length() > 11 || seguro.length() < 11) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Seguro' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(nacimiento.length() > 50 || nacimiento.length() < 4) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Fecha de Nacimiento' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(cp.length() > 5 || cp.length() < 5) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'CP' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(dirrecion.length() > 100 || dirrecion.length() < 5) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Dirrecion' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(telefono.length() > 10 || telefono.length() < 10) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Numero de Telefono' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else if(contrasena.length() > 50) {
                    Toast.makeText(registra.this, "Contraseña muy larga", Toast.LENGTH_SHORT).show();
                }
                else if(contrasena.length() < 5) {
                    Toast.makeText(registra.this, "Contraseña muy corta", Toast.LENGTH_SHORT).show();
                }
                else if(correo.length() > 50 || telefono.length() < 9) {
                    Toast.makeText(registra.this, "Texo ingresado en el campo 'Correo' incorrecto", Toast.LENGTH_SHORT).show();
                }
                else {
                        if (tyc.isChecked()) {
                            new DescargarImagen(registra.this).execute(nombre, apellido, clinica, seguro, nacimiento, dirrecion, cp, telefono, contrasena, correo);
                        } else {
                            Toast.makeText(registra.this, "Porfavor acepte nuestros terminos y condiciones", Toast.LENGTH_SHORT).show();
                        }
                }
                }
            });
    }


    public class DescargarImagen extends AsyncTask<String, Void, String> {

        private WeakReference<Context> context;

        public DescargarImagen(Context context) {
            this.context = new WeakReference<>(context);

        }

        protected String doInBackground(String... params) {
            String registrar_url =  "https://fjhgj.000webhostapp.com/datos/resivir.php";
            String resultado = "";

            try {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String nombre = params[0];
                String apellido = params[1];
                String clinica = params[2];
                String seguro = params[3];
                String nacimiento = params[4];
                String dirrecion = params[5];
                String cp = params[6];
                String telefono = params[7];
                String contrasena = params[8];
                String correo = params[9];


                String data = URLEncoder.encode("Nombre", "UTF-8") + "=" + URLEncoder.encode(nombre, "UTF-8")
                        + "&" + URLEncoder.encode("Apellido", "UTF-8") + "=" + URLEncoder.encode(apellido, "UTF-8")
                        + "&" + URLEncoder.encode("Clinica", "UTF-8") + "=" + URLEncoder.encode(clinica, "UTF-8")
                        + "&" + URLEncoder.encode("Seguro", "UTF-8") + "=" + URLEncoder.encode(seguro, "UTF-8")
                        + "&" + URLEncoder.encode("Nacimiento", "UTF-8") + "=" + URLEncoder.encode(nacimiento, "UTF-8")
                        + "&" + URLEncoder.encode("Dirrecion", "UTF-8") + "=" + URLEncoder.encode(dirrecion, "UTF-8")
                        + "&" + URLEncoder.encode("CP", "UTF-8") + "=" + URLEncoder.encode(cp, "UTF-8")
                        + "&" + URLEncoder.encode("Telefono", "UTF-8") + "=" + URLEncoder.encode(telefono, "UTF-8")
                        + "&" + URLEncoder.encode("Contrasena", "UTF-8") + "=" + URLEncoder.encode(contrasena, "UTF-8")
                        + "&" + URLEncoder.encode("Correo", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8");

                
                
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return resultado;
        }
        
        protected void onPostExecute(String resultado) {
            if(resultado == ""){
                Toast.makeText(registra.this, "Error, verifique la conexion a internet", Toast.LENGTH_SHORT).show();
            }
            else if(resultado.equalsIgnoreCase("Usuario agregado con exito, ya puede iniciar sesion")) {
                Toast.makeText(registra.this, resultado, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(registra.this, iniciar.class);
                startActivity(i);
            }
            else if(resultado.equalsIgnoreCase("Este numero de seguro social ya esta registrado")){
                Toast.makeText(registra.this, resultado, Toast.LENGTH_SHORT).show();
            }

        }
        
    }
}