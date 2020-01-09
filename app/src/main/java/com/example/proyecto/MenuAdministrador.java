package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdministrador extends AppCompatActivity {

    private Button lista;
    private Button addBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        lista = findViewById(R.id.btLista);
        addBike = findViewById(R.id.btAddProducto);

        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuAdministrador.this, AddBicicletas.class);
                startActivity(i);

            }
        });

    }
}
