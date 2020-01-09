package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private TextView user;
    private TextView pass;
    private Button btLogin;
    private FirebaseAuth autentificador;
    private FirebaseUser fbUser;
    FirebaseDatabase firebaseDatabase;
    String usuario;
    String clave;
    Preferencias preferencias;

    String prefEmail;
    String prefPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);
        autentificador = FirebaseAuth.getInstance();

        preferencias = new Preferencias(getApplicationContext());
        user = (TextView) findViewById(R.id.txUserLogin);
        pass = (TextView) findViewById(R.id.txPassLogin);
        btLogin = (Button) findViewById(R.id.btLogin);
        usuario = user.getText().toString();
        clave = pass.getText().toString();


        if (preferencias.getSesion() != null) {
            String sesion = preferencias.getSesion();
            String[] arraySesion = sesion.split("-");
            try {
                prefEmail = arraySesion[0];
                prefPassword = arraySesion[1];
                iniciarSesion(prefEmail, prefPassword);
            } catch (ArrayIndexOutOfBoundsException ex) {
                // Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getText().toString().isEmpty() &&
                        !pass.getText().toString().isEmpty()) {
                    // When a user signs in to your app, pass the user's email address and password to signInWithEmailAndPassword
                    autentificador.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        fbUser = autentificador.getCurrentUser();
                                        preferencias.guardarPreferencias(user.getText().toString(), pass.getText().toString());
                                        Toast.makeText(Login.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                        if (user.toString().contains("admin") || user.toString().contains("Admin")) {
                                            Intent i = new Intent(Login.this, MenuAdministrador.class);
                                            startActivity(i);
                                        }
                                        else {
                                            Intent i = new Intent(Login.this, ListaProductos.class);
                                            startActivity(i); }
                                    } else {
                                        Toast.makeText(Login.this, "Fallo autentificacion", Toast.LENGTH_SHORT).show();

                                        try {
                                            preferencias.eliminarPreferencias();
                                        } catch (NullPointerException ex) {
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    public void iniciarSesion(String email, String password) {
            final String finalEmail = email;
            final String finalPass = password;
            autentificador.signInWithEmailAndPassword(email, password).addOnCompleteListener
                    (new OnCompleteListener<AuthResult>() {
                        private static final String TAG = "MITAG";

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fbUser = autentificador.getCurrentUser();


                                if (finalEmail.toString().contains("admin") || finalEmail.toString().contains("Admin")) {
                                    Intent i = new Intent(Login.this, MenuAdministrador.class);
                                    startActivity(i);
                                }
                                else {
                                    Intent i = new Intent(Login.this, ListaProductos.class);
                                    startActivity(i); }
                            } else {
                                preferencias.eliminarPreferencias();
                                Log.v(TAG, "ERROOOOOO " + task.getException().toString());
                            }
                        }
                    });
        }

}

