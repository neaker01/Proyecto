package com.example.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddBicicletas extends AppCompatActivity {

    private Button addImage;

    private ImageView imagenBike;

    private static final int SELECCIONAR_IMAGEN = 2;
    final private int REQUEST_CODE_PERMISSIONS = 1;
    private Uri uriImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bicicletas);

        addImage = findViewById(R.id.btSelectImage);
    imagenBike = findViewById(R.id.imaheAddBike);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });



    }


    public void cargarImagen(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, SELECCIONAR_IMAGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECCIONAR_IMAGEN){
            if(resultCode == RESULT_OK){
                uriImagen = data.getData();
                imagenBike.setImageURI(uriImagen);
                AddBicicletas.this.grantUriPermission(AddBicicletas.this.getPackageName(), uriImagen, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
    }
}
