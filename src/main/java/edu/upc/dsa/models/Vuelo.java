package edu.upc.dsa.models;

import java.util.Date;

public class Vuelo {

   String id;
   String horaSalida;
   String horaLlegada;
   String avionAsignado;
   String origen;
   String destino;





    public Vuelo() {
    }

    public Vuelo(String id, String horaSalida, String horaLlegada,
                 String avionAsignado, String origen, String destino) {
        this.id = id;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.avionAsignado = avionAsignado;
        this.origen = origen;
        this.destino = destino;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getAvionAsignado() {
        return avionAsignado;
    }

    public void setAvionAsignado(String avionAsignado) {
        this.avionAsignado = avionAsignado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}





