package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {

    public static final int DETALLE = 0;
    public static final int ADD_LUGAR = 1;
    public static final int ELIMINAR = 2;
    public static final int GUARDADO = 3;
    private static final String TAG = "MITAG";
    private RecyclerView recyclerBicicletas;
    private Adaptador adaptador;
    private RecyclerView.LayoutManager lymanager;
   // private Ayudante ayudante;
   // private GestorLugar gestor;
    private ArrayList<Bicicleta> listaFirebase = new ArrayList<>(); //primer array donde guardamos los libros leido
    private ArrayList<Bicicleta> listaLocal = new ArrayList<>();
    //private android.support.constraint.Constraint contenedor;
    private androidx.constraintlayout.widget.ConstraintLayout contenedor;
    //private ImageButton btAdd;
   // private Button btLogout;
    private Preferencias preferencias;
    private FirebaseAuth autentificador;
    private FirebaseUser usuario;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        firebaseDatabase= FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);
        autentificador =  FirebaseAuth.getInstance();
        usuario = autentificador.getCurrentUser();

        preferencias = new Preferencias(getApplicationContext());
      //  ayudante = new Ayudante(this);
       // gestor = new GestorLugar(this, true);
       // btAdd = findViewById(R.id.btn_add);
        getBicicletas();
        usuario = autentificador.getCurrentUser();

       this.contenedor = (ConstraintLayout) findViewById(R.id.contenedor);
       this.recyclerBicicletas = (RecyclerView) findViewById(R.id.recyclerBicicletas);

        setAdapter(listaFirebase);
        lymanager = new LinearLayoutManager(this);
        recyclerBicicletas.setLayoutManager(lymanager);


    }


    public void getBicicletas(){

        if (isOnlineNet()) { //  si hay internet cogemos los lugares de firebase
            listaFirebase = new ArrayList<>();
            final Query listaBicis =
                    FirebaseDatabase.getInstance().getReference()
                            .child("/bicicletas/"+usuario.getUid()+"/");  //usuario.getUid() +"-"+ usuario.getDisplayName()+"/libro/");
            //.orderByKey();
            listaBicis.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                        System.out.println("NODO " + hijo.getValue().toString());
                        Bicicleta bici = new Bicicleta();

                      //  bici.s






/*
                        private int id;
                        private String tipo; //Montaña, Carretera, electricas
                        private int tallaRueda;  // cambiar a double, puede haber de 27'5
                        private String modelo;
                        private String color;
                        private int numeroPlatos;
                        private String marca;
                        private String talla;
                        private double precio;
                        private int stock;
                        private Uri imagen;
                        private String key;


                     */

                        String modelo = (String) hijo.child("nombre").getValue();
                        String pais = (String) hijo.child("pais").getValue();
                        String localidad = (String) hijo.child("localidad").getValue();
                        String fecha = (String) hijo.child("fecha").getValue();
                        Double latitud = 0.0;
                        Double longitud= 0.0;
                        long puntos = 0;
                        try {
                            latitud = Double.parseDouble(String.valueOf(hijo.child("latitud").getValue()));
                            longitud = Double.parseDouble(String.valueOf(hijo.child("longitud").getValue()));
                            puntos = ((long) hijo.child("puntos").getValue());
                        }catch(NumberFormatException ex){
                            preferencias.eliminarPreferencias();
                            Intent i = new Intent(MainActivity.this, Login.class);
                            startActivity(i);
                        }
                        String comentarios = (String) hijo.child("comentario").getValue();
                        String key = (String) hijo.child("key").getValue();

                        lugar.setNombre(nombre);
                        lugar.setFecha(fecha);
                        lugar.setPais(pais);
                        lugar.setLocalidad(localidad);
                        lugar.setComentario(comentarios);
                        lugar.setLatitud(latitud);
                        lugar.setLongitud(longitud);
                        lugar.setPuntos((int) puntos);
                        lugar.setkey(key);

                        //   String fbkey = (String) hijo.getKey();
                        //    lecFB.setFbkey(fbkey);

                        lugaresFirebase.add(lugar);
                    }

                    lugaresFirebase = lugaresFirebase;
                    adaptador.setArray(lugaresFirebase);
                    adaptador.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }else{ // Si no hay internet cogemos los lugares de la bd local

            listaLugares = gestor.getLugares();

            // System.out.println("lugares bdd " +listaLugares.get(0).toString());
            System.out.println("Array tamaño " +listaLugares.size());

            adaptador.setArray(listaLugares);
            adaptador.notifyDataSetChanged();
        }


    }


    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Excepcion  "+e.getMessage());
        }
        return false;
    }


    private void setAdapter(ArrayList<Bicicleta> nuevaBicicleta){
        //Reemplaza el adaptador por una nueva instancia con un nuevo dataset.
        adaptador = new Adaptador(nuevaBicicleta, new Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(Bicicleta b) {
                Log.v(TAG, "Bici CLICK : " + b.getModelo());
                Intent i = new Intent(ListaProductos.this, Detalle.class);
                i.putExtra("bicicleta", (Parcelable) b);
                startActivityForResult(i, DETALLE);
            }
        });


        recyclerBicicletas.setAdapter(adaptador);
    }
}
