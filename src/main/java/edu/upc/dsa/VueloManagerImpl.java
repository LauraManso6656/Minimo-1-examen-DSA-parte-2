package edu.upc.dsa;

import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import org.apache.log4j.Logger;

import java.util.*;

public class VueloManagerImpl implements VueloManager {
    private static final Logger logger = Logger.getLogger(VueloManagerImpl.class);
    private static VueloManagerImpl instance;

    private final HashMap<String, Avion> aviones;
    private final HashMap<String, Vuelo> vuelos;
    private final HashMap<String, Queue<Maleta>> maletasPorVuelo;

    // Constructor para Singleton
    private VueloManagerImpl() {
        aviones = new HashMap<>();
        vuelos = new HashMap<>();
        maletasPorVuelo = new HashMap<>();
        logger.info("VueloManagerImpl inicializado");
    }

    // Obtener la instancia única
    public static synchronized VueloManagerImpl getInstance() {
        if (instance == null) {
            instance = new VueloManagerImpl();
        }
        return instance;
    }

    @Override
    public void addAvion(String id, String modelo, String compania) {
        logger.info("Inicio de addAvion: id=" + id + ", modelo=" + modelo + ", compania=" + compania);
        Avion avion = aviones.get(id);
        if (avion != null) {
            avion.setModelo(modelo);
            avion.setCompania(compania);
            logger.info("Avión actualizado: " + avion);
        } else {
            avion = new Avion(id, modelo, compania);
            aviones.put(id, avion);
            logger.info("Avión agregado: " + avion);
        }
    }

    @Override
    public void addVuelo(String id, String horaSalida, String horaLlegada, String avionId, String origen, String destino) {
        logger.info("Inicio de addVuelo: id=" + id + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada +
                ", avionId=" + avionId + ", origen=" + origen + ", destino=" + destino);

        Avion avion = aviones.get(avionId);
        if (avion == null) {
            logger.error("Error: el avión con ID " + avionId + " no existe.");
            throw new IllegalArgumentException("El avión con ID " + avionId + " no existe.");
        }

        Vuelo vuelo = vuelos.get(id);
        if (vuelo != null) {
            vuelo.setHoraSalida(horaSalida);
            vuelo.setHoraLlegada(horaLlegada);
            vuelo.setOrigen(origen);
            vuelo.setDestino(destino);
            vuelo.setAvionAsignado(avionId);
            logger.info("Vuelo actualizado: " + vuelo);
        } else {
            vuelo = new Vuelo(id, horaSalida, horaLlegada, avionId, origen, destino);
            vuelos.put(id, vuelo);
            logger.info("Vuelo agregado: " + vuelo);
        }
    }



    @Override
    public void facturarMaleta(String vueloId, String userId) {
        logger.info("Inicio de facturarMaleta: vueloId=" + vueloId + ", userId=" + userId);

        Vuelo vuelo = vuelos.get(vueloId);
        if (vuelo == null) {
            logger.error("Error: el vuelo con ID " + vueloId + " no existe.");
            throw new IllegalArgumentException("El vuelo con ID " + vueloId + " no existe.");
        }

        String idMaleta = UUID.randomUUID().toString();
        Maleta maleta = new Maleta(idMaleta, userId, vueloId);

        maletasPorVuelo.computeIfAbsent(vueloId, k -> new LinkedList<>()).offer(maleta);
        logger.info("Maleta facturada: " + maleta);
    }

    @Override
    public void facturarMaleta(String Id, String userId, String vueloId) {

    }


    @Override
    public List<Maleta> getMaletasByVuelo(String vueloId) {
        logger.info("Inicio de getMaletasByVuelo: vueloId=" + vueloId);

        Queue<Maleta> maletasQueue = maletasPorVuelo.get(vueloId);
        if (maletasQueue == null || maletasQueue.isEmpty()) {
            logger.error("Error: el vuelo con ID " + vueloId + " no tiene maletas facturadas.");
            throw new IllegalArgumentException("El vuelo con ID " + vueloId + " no tiene maletas facturadas.");
        }

        List<Maleta> maletasList = new ArrayList<>(maletasQueue); // Copiar a lista ordenada
        Collections.reverse(maletasList); // Invertir para simular el orden de descarga
        logger.info("Maletas encontradas para el vuelo " + vueloId + ": " + maletasList);
        return maletasList;
    }

    @Override
    public Avion getAvion(String id) {
        logger.info("Inicio de getAvion: id=" + id);
        Avion avion = aviones.get(id);
        if (avion == null) {
            logger.error("Error: el avión con ID " + id + " no existe.");
            throw new IllegalArgumentException("El avión con ID " + id + " no existe.");
        }
        logger.info("Avión encontrado: " + avion);
        return avion;
    }





    @Override
    public Vuelo getVuelo(String id) {
        logger.info("Inicio de getVuelo: id=" + id);
        Vuelo vuelo = vuelos.get(id);
        if (vuelo == null) {
            logger.error("Error: el vuelo con ID " + id + " no existe.");
            throw new IllegalArgumentException("El vuelo con ID " + id + " no existe.");
        }
        logger.info("Vuelo encontrado: " + vuelo);
        return vuelo;
    }

    @Override
    public Vuelo getVuelo(String id, String horaSalida, String horaLlegada, String avionId, String origen, String destino) {
        return null;
    }

    @Override
    public List<Vuelo> getAllVuelos() {

        logger.info("Inicio de getAllVuelos");
        List<Vuelo> vuelosList = new ArrayList<>(vuelos.values());
        logger.info("Vuelos encontrados: " + vuelosList.size());
        return vuelosList;
    }

    @Override
    public void deleteVuelo(String vueloId) {
        logger.info("Inicio de deleteVuelo: vueloId=" + vueloId);

        // Verificar si el vuelo existe
        Vuelo vuelo = vuelos.get(vueloId);
        if (vuelo == null) {
            logger.error("Error: el vuelo con ID " + vueloId + " no existe.");
            throw new IllegalArgumentException("El vuelo con ID " + vueloId + " no existe.");
        }

        // Eliminar el vuelo del mapa
        vuelos.remove(vueloId);
        logger.info("Vuelo con ID " + vueloId + " eliminado exitosamente.");

    }

    @Override
    public int size() {
        return 0;
    }
} 










