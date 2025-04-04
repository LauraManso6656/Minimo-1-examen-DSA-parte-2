package edu.upc.dsa.models;
import java.util.ArrayList;
import java.util.List;

public class Avion {
    private String id;
    private String modelo;
    private String compania;

    public Avion() {}

    public Avion(String id, String modelo, String compania) {
        this.id = id;
        this.modelo = modelo;
        this.compania = compania;
    }


    public String getId() {
        return id; }

    public void setId(String id) {
        this.id = id; }

    public String getModelo() {
        return modelo; }

    public void setModelo(String modelo) {
        this.modelo = modelo; }

    public String getCompania() {
        return compania; }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public static List<Avion> getAvions() {
        List<Avion> avions = new ArrayList<Avion>();
        return avions;
    }




     //public void addOrder(Order order) {
        //orders.add(order);
    //}
}

