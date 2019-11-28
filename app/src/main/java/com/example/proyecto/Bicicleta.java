package com.example.proyecto;

public class Bicicleta {

    private String tipo; //Montaña, Carretera, electricas
    private int tallaRueda;
    private String modelo;
    private String color;
    private int numeroPlatos;
    private String marca;
    private String talla;
    private double precio;
    private int stock;
    private String neumaticos;

    public Bicicleta(String tipo, int tallaRueda, String modelo, String color, int numeroPlatos, String marca, String talla, double precio, int stock, String neumaticos) {
        this.tipo = tipo;
        this.tallaRueda = tallaRueda;
        this.modelo = modelo;
        this.color = color;
        this.numeroPlatos = numeroPlatos;
        this.marca = marca;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;
        this.neumaticos = neumaticos;
    }
    public Bicicleta(){

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

    public String getNeumaticos() {
        return neumaticos;
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

    public void setNeumaticos(String neumaticos) {
        this.neumaticos = neumaticos;
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
                ", neumaticos='" + neumaticos + '\'' +
                '}';
    }
}
