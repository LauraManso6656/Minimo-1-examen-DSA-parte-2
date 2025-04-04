package edu.upc.dsa;

import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Avion;

import java.util.List;

public interface VueloManager {
    // Operaciones con aviones
    void addAvion(String id, String modelo, String compania);

    void addVuelo(String id, String horaSalida, String horaLlegada, String avionId, String origen, String destino);

    void facturarMaleta(String vueloId, String userId);

    void facturarMaleta(String Id, String userId, String vueloId);

    List<Maleta> getMaletasByVuelo(String vueloId);

    Avion getAvion(String avionId);

    Vuelo getVuelo(String vueloId);

    List<Vuelo> getAllVuelos();

    void deleteVuelo(String vueloId);

    int size();




}