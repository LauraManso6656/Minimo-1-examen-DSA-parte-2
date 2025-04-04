package edu.upc.dsa;

import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class VueloManagerTest {
    VueloManager vueloManager;

    @Before
    public void setUp() {
        vueloManager = VueloManagerImpl.getInstance();
        vueloManager.addAvion("A1", "Boeing 747", "Ryanair");
        vueloManager.addAvion("A2", "Airbus 430", "EasyJet");

        vueloManager.addVuelo("V1", "10:00", "12:00", "A1", "Barcelona", "Madrid");
        vueloManager.addVuelo("V2", "15:00", "17:00", "A2", "Madrid", "París");
    }

    @After
    public void tearDown() {
        this.vueloManager = null;
    }

    // Funciona
    @Test
    public void testAddAvion() {
        vueloManager.addAvion("A3", "Pilatus", "Iberia");
        Avion avion = vueloManager.getAvion("A3");
        Assert.assertEquals("Pilatus", avion.getModelo());
        Assert.assertEquals("Iberia", avion.getCompania());
    }


    @Test
    public void testAddVuelo() { //me va, habia una funcion duplicada y la he eliminado
        vueloManager.addVuelo("V3", "18:00", "20:00", "A1", "Madrid", "Barcelona");
        Vuelo vuelo = vueloManager.getVuelo("V3");
        Assert.assertEquals("Madrid", vuelo.getOrigen());
        Assert.assertEquals("Barcelona", vuelo.getDestino());
    }

    @Test //ya funciona
    public void testGetAllVuelos() {
        List<Vuelo> vuelos = vueloManager.getAllVuelos();
        Assert.assertEquals(2, vuelos.size());
    }


    @Test //YA FUNCIONA 
    public void testMaletasRetrievalOrder() {
        vueloManager.facturarMaleta("V1", "User1");
        vueloManager.facturarMaleta("V1", "User2");
        List<Maleta> maletaList = vueloManager.getMaletasByVuelo("V1");
        Assert.assertEquals("User2", maletaList.get(0).getUserId()); // Última maleta facturada primero
        Assert.assertEquals("User1", maletaList.get(1).getUserId());
    }
}
