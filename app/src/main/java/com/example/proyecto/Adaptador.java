package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private static final String TAG = "XYZ";
    private final OnItemClickListener listener;
    private ArrayList<Bicicleta> lista;


    public Adaptador(ArrayList<Bicicleta> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void setArray(ArrayList<Bicicleta> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_productos, viewGroup, false);
        return new Adaptador.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder viewHolder, int i) {
        Bicicleta b = (Bicicleta) lista.get(i);

        viewHolder.txModelo.setText(b.getModelo().toString());
        viewHolder.txPrecio.setText(String.valueOf(b.getPrecio()).toString());

        viewHolder.bind(lista.get(i), (OnItemClickListener) listener);
    }



    public interface OnItemClickListener {
        void onItemClick(Bicicleta b);

    }
    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //  ImageView imagenLectura;
        TextView txModelo;
        TextView txPrecio;
        ImageView imagenBike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //   imagenLectura=itemView.findViewById(R.id.imagenLecturaRecycler);
            txModelo = itemView.findViewById(R.id.recycler_modelo);
            txPrecio = itemView.findViewById(R.id.recycler_precio);
            imagenBike = itemView.findViewById(R.id.recycler_image);

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
