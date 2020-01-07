package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

public class ListaProductos extends AppCompatActivity {

    public static final int OK = 1;
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
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        firebaseDatabase= FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference();

        FirebaseApp.initializeApp(this);
        autentificador =  FirebaseAuth.getInstance();
        usuario = autentificador.getCurrentUser();



        Bicicleta b1 = new Bicicleta("carretera", 27, "Cube dos", "Negra", 2, "Cube", "M", 899, 3);
        b1.setId(2);
        //Ahora cogemos toda la lista de bicicletas
        preferencias = new Preferencias(getApplicationContext());
      //  ayudante = new Ayudante(this);
       // gestor = new GestorLugar(this, true);
       // btAdd = findViewById(R.id.btn_add);
        getBicicletas();

       this.contenedor = (ConstraintLayout) findViewById(R.id.contenedor);
       this.recyclerBicicletas = (RecyclerView) findViewById(R.id.recyclerBicicletas);

        setAdapter(listaFirebase);
        lymanager = new LinearLayoutManager(this);
         recyclerBicicletas.setLayoutManager(lymanager);

        if (listaFirebase.size() > 0){
            for (Bicicleta b: listaFirebase
                 )
                System.out.println("Bici 1" + b.toString());

            }else{
                System.out.println("array vacio");
        }

        System.out.println("ONCREATE");
    }

    public void getBicicletas(){

        listaFirebase = new ArrayList();

        //if (isOnlineNet()) { //  si hay internet cogemos los lugares de firebase
            final Query listaBicis =
                    FirebaseDatabase.getInstance().getReference()
                            .child("/bicicletas/");
        //usuario.getUid() +"-"+ usuario.getDisplayName()+"/libro/");
            //.orderByKey();
            listaBicis.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                        System.out.println("NODO " + hijo.getValue().toString());
                        Bicicleta bici = new Bicicleta();
                        String modelo = (String) hijo.child("modelo").getValue();
                        int id =  0;
                        String tipo = (String) hijo.child("tipo").getValue();
                        int tallaRueda =  0;
                        String color = (String) hijo.child("color").getValue();
                        int numeroPlatos =  0;
                        String marca = (String) hijo.child("marca").getValue();
                        String talla = (String) hijo.child("talla").getValue();
                        Double precio = 0.0;
                        String uri = (String) hijo.child("uri").getValue();
                        String key = (String) hijo.child("key").getValue();
                        bici.setKey(key);

                        int stock =  0;
                        try {
                           // String idStrig = ((String) hijo.child("id").getValue());
                             id = Integer.parseInt((String.valueOf( hijo.child("id").getValue())));
                            tallaRueda=  Integer.parseInt((String.valueOf( hijo.child("tallaRueda").getValue())));
                            numeroPlatos=  Integer.parseInt((String.valueOf( hijo.child("numeroPlatos").getValue())));
                            precio=  Double.parseDouble((String.valueOf(hijo.child("precio").getValue())));
                            stock=  Integer.parseInt((String.valueOf( hijo.child("stock").getValue())));



                            // tallaRueda =  Integer.parseInt((String) hijo.child("tallaRueda").getValue());
                           //  numeroPlatos =  Integer.parseInt((String) hijo.child("numeroPlatos").getValue());
                           //  precio =  Double.parseDouble((String) hijo.child("precio").getValue());
                            // stock =  Integer.parseInt((String) hijo.child("stock").getValue());
                            bici.setStock(stock);
                        }catch(NumberFormatException ex){
                            preferencias.eliminarPreferencias();
                           // Intent i = new Intent(ListaProductos.this, Login.class);
                            // startActivity(i);
                           // Toast.makeText(ListaProductos.this, "HA CRASHEADO AL CONVERTIR NUMEROS", Toast.LENGTH_SHORT).show();

                            System.out.println("HA CRASHEADO AL CONVERTIR NUMERO");
                          }

                        bici.setModelo(modelo);
                        bici.setId(id);
                        bici.setTipo(tipo);
                        bici.setTallaRueda(tallaRueda);
                        bici.setColor(color);
                        bici.setNumeroPlatos(numeroPlatos);
                        bici.setMarca(marca);
                        bici.setTalla(talla);
                        bici.setPrecio(precio);
                        // bici.setUri(uri);
                        System.out.println("Bici segundo sout " +bici.toString());

                        listaFirebase.add(bici);

                        System.out.println("Se añade al array? " +listaFirebase.size());
                        System.out.println("La bici es null? " +bici.toString());
                    }


                    System.out.println("tam array fuera del bucle" +listaFirebase.size());
                   // listaFirebase = listaFirebase;
                    adaptador.setArray(listaFirebase);
                    adaptador.notifyDataSetChanged();

                 //   setAdapter(listaFirebase);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            //--------RELLENAR PARA CUANDO SE VAYA INTERNET
       /* }else{ // Si no hay internet cogemos los lugares de la bd local

          //  listaFirebase = gestor.getLugares();

            // System.out.println("lugares bdd " +listaLugares.get(0).toString());
          //  System.out.println("Array tamaño " +listaLugares.size());

           // adaptador.setArray(listaLugares);

            adaptador.notifyDataSetChanged();
        //}
        */

        System.out.println("tam array fuera del data change" +listaFirebase.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // listaLugares =  gestor.getLugares();
        if(resultCode == OK){
            getBicicletas();
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
