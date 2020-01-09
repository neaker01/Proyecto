package com.example.proyecto;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private static final String TAG = "XYZ";
    private final OnItemClickListener listener;
    private ArrayList<Bicicleta> listaBicicletas;

    //---Constructor que recibe el array de lecturas----

    public Adaptador(ArrayList<Bicicleta> arrayBicicletas, OnItemClickListener listener) {
        this.listaBicicletas = arrayBicicletas;
        this.listener = listener;
    }

    public void setArray(ArrayList<Bicicleta> arrayBicicletas) {
        this.listaBicicletas = arrayBicicletas;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_productos, viewGroup, false);
        return new Adaptador.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder viewHolder, int i) {
        Bicicleta bicicleta = (Bicicleta) listaBicicletas.get(i);

        viewHolder.txModelo.setText(bicicleta.getModelo());
        viewHolder.txPrecio.setText(String.valueOf(bicicleta.getPrecio()));



      /*  if(bicicleta.getImagen() != null){

            Log.v(TAG, "En adaptador: uri no es null");
            viewHolder.imagenLectura.setImageURI(lectura.getImagen());
        }
        */

       // viewHolder.imagenLectura.setImageURI(lectura.getImagen());

        viewHolder.bind(listaBicicletas.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return listaBicicletas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Bicicleta b);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenBicicleta;
        TextView txModelo;
        TextView txPrecio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenBicicleta=itemView.findViewById(R.id.recycler_image);
            txModelo=itemView.findViewById(R.id.recycler_modelo);
            txPrecio=itemView.findViewById(R.id.recycler_precio);

            txPrecio.setGravity(Gravity.RIGHT);

        }

        public void bind(final Bicicleta b, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(b);
                }
            });
        }
    }
}
