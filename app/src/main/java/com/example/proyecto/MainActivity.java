package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autentificador;
    private DatabaseReference dbReference;
    private FirebaseDatabase database;
    private ArrayList<Bicicleta> listaBicis;
    private ArrayList<Bicicleta> listaBicis2;


    private Button btRegistrar;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        autentificador = FirebaseAuth.getInstance();
        dbReference = database.getReference();
        listaBicis = new ArrayList<>();
        listaBicis2 = new ArrayList<>();

        btLogin  = (Button) findViewById(R.id.btLogin);
        btRegistrar  = (Button) findViewById(R.id.btRegistro);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);

            }
        });
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
            }
        });

        //FALTARIA EL TEMA DE LAS FOTOS A VER COMO LO HACEMOS
        //Opcion 1: Crear actividad para meter las bicicletas a mano junto con la foto
        // Opcion 2: Hacerlo desde firebase

        Bicicleta b1 = new Bicicleta("carretera", 27, "Cube dos", "Negra", 2, "Cube", "M", 899, 3);
        b1.setId(2);
        //Ahora cogemos toda la lista de bicicletas


        getBicis();
    }

    public void agregarBici(Bicicleta b1){
        Map<String, Object> saveItem = new HashMap<>();
        String key = dbReference.child("bicicletas").push().getKey();
        b1.setKey(key);
        System.out.println("LA KEY EN LA INSERCION: " + key);
        saveItem.put("/bicicletas/" + key + "/", b1.toMap());
        dbReference.updateChildren(saveItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // gestor.add(lugar);
                        Log.v("GUARDADO", "GUARDADO: " + task.isSuccessful());

                    }
                });
    }

    public void getBicis(){
        final Query listaBikes =
                FirebaseDatabase.getInstance().getReference()
                        .child("/bicicletas/");  //usuario.getUid() +"-"+ usuario.getDisplayName()+"/libro/");
        //.orderByKey();
        listaBikes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                  //  System.out.println("NODO " + hijo.getValue().toString());
                    Bicicleta b = new Bicicleta();


                    String marca = (String) hijo.child("marca").getValue();
                    String modelo = (String) hijo.child("modelo").getValue();
                    String key = (String) hijo.child("key").getValue();
                    String color = (String) hijo.child("color").getValue();
                    int id = 0;
                    int nPlatos = 0;
                    double precio = 0;
                    int stock = 0;
                    String talla = (String) hijo.child("talla").getValue();
                    int tallaRueda = 0;
                    String tipo = (String) hijo.child("tipo").getValue();
                    try {
                        id = Integer.parseInt(String.valueOf(hijo.child("id").getValue()));
                        nPlatos = Integer.parseInt(String.valueOf(hijo.child("numeroPlatos").getValue()));
                        precio = Double.parseDouble(String.valueOf( hijo.child("precio").getValue()));
                        stock = Integer.parseInt(String.valueOf( hijo.child("stock").getValue()));
                        tallaRueda = Integer.parseInt(String.valueOf(hijo.child("tallaRueda").getValue()));
                    } catch (NumberFormatException ex) {
                        System.out.println("Error al convertir los datos");
                        //  preferencias.eliminarPreferencias();
                        //Intent i = new Intent(MainActivity.this, Login.class);
                        // startActivity(i);
                    }


                    b.setKey(key);
                    b.setId(id);
                    b.setMarca(marca);
                    b.setColor(color);
                    b.setModelo(modelo);
                    b.setNumeroPlatos(nPlatos);
                    b.setPrecio(precio);
                    b.setStock(stock);
                    b.setTalla(talla);
                    b.setTallaRueda(tallaRueda);
                    b.setTipo(tipo);

                    listaBicis.add(b);

                }

                listaBicis2 = listaBicis;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

}
