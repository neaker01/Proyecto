package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button btRegister;
    private String usuario;
    private String clave;
    private FirebaseAuth autentificador;
    private FirebaseUser usuarioFirebase;

    private FirebaseDatabase database;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        autentificador = FirebaseAuth.getInstance();

        user=findViewById(R.id.txEmailRegistro);
        pass=findViewById(R.id.txPassRegistro);
        btRegister = findViewById(R.id.btRegistro);

        usuario = user.getText().toString();
        clave = pass.getText().toString();

        btRegister = findViewById(R.id.btRegistro);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!user.getText().toString().isEmpty() &&
                        !pass.getText().toString().isEmpty()){
                    crearUsuario(user.getText().toString(), pass.getText().toString());
                }
            }
        });
    }



    public  void crearUsuario(String email, String password) {

        if (email.contains("admin") || email.contains("Admin")) {
            Toast.makeText(this, "El usuario no puede ser un administrador", Toast.LENGTH_SHORT).show();
        } else {
            autentificador.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                    // ((Executor) this, new OnCompleteListener<AuthResult>() { //El de carmelo estaba asi pero da error de casting
                            (new OnCompleteListener<AuthResult>() { //Sin el executor
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user1 = autentificador.getCurrentUser();
                                        //    guardarUsuario(user1);
                                         Toast.makeText(Registro.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Registro.this, Login.class);
                                        startActivity(i);
                                    } else {

                                        System.out.println("ERROR");
                                    }
                                }
                            });
        }

    }
}
