package com.example.proyecto;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Bicicleta implements Parcelable {

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
    //Añadir numero de serie

    public Bicicleta(String tipo, int tallaRueda, String modelo, String color, int numeroPlatos, String marca, String talla, double precio, int stock) {
        this.tipo = tipo;
        this.tallaRueda = tallaRueda;
        this.modelo = modelo;
        this.color = color;
        this.numeroPlatos = numeroPlatos;
        this.marca = marca;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;

    }
    public Bicicleta(){

    }

    protected Bicicleta(Parcel in) {
        id = in.readInt();
        tipo = in.readString();
        tallaRueda = in.readInt();
        modelo = in.readString();
        color = in.readString();
        numeroPlatos = in.readInt();
        marca = in.readString();
        talla = in.readString();
        precio = in.readDouble();
        stock = in.readInt();
        imagen = in.readParcelable(Uri.class.getClassLoader());
        key = in.readString();
    }

    public static final Creator<Bicicleta> CREATOR = new Creator<Bicicleta>() {
        @Override
        public Bicicleta createFromParcel(Parcel in) {
            return new Bicicleta(in);
        }

        @Override
        public Bicicleta[] newArray(int size) {
            return new Bicicleta[size];
        }
    };

    public void setId(int id){
        this.id=id;
    }


    public void setUri(String uri){
        //this.imagen=Uri.parse(uri);
    }



    public void setKey(String key){
        this.key=key;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTallaRueda() {
        return tallaRueda;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public int getNumeroPlatos() {
        return numeroPlatos;
    }

    public String getMarca() {
        return marca;
    }

    public String getTalla() {
        return talla;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }



    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTallaRueda(int tallaRueda) {
        this.tallaRueda = tallaRueda;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNumeroPlatos(int numeroPlatos) {
        this.numeroPlatos = numeroPlatos;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return "Bicicleta{" +
                "tipo='" + tipo + '\'' +
                ", tallaRueda=" + tallaRueda +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                ", numeroPlatos=" + numeroPlatos +
                ", marca='" + marca + '\'' +
                ", talla='" + talla + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(tipo);
        dest.writeInt(tallaRueda);
        dest.writeString(modelo);
        dest.writeString(color);
        dest.writeInt(numeroPlatos);
        dest.writeString(marca);
        dest.writeString(talla);
        dest.writeDouble(precio);
        dest.writeInt(stock);
        dest.writeParcelable(imagen, flags);
        dest.writeString(key);
        Log.v("PARCELKEY", key);
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("tipo", tipo);
        result.put("tallaRueda", tallaRueda);
        result.put("modelo", modelo);
        result.put("color", color);
        result.put("numeroPlatos", numeroPlatos);
        result.put("marca", marca);
        result.put("talla", talla);
        result.put("precio", precio);
        result.put("stock", stock);
        if(imagen != null){
            result.put("imagen", imagen.toString());
        }
        result.put("key", key);

        return result;
    }

    }

