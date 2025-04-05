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

    @Test
    public void testGetVueloExistente() {
        Vuelo vuelo = vueloManager.getVuelo("V1");
        Assert.assertNotNull(vuelo);
        Assert.assertEquals("V1", vuelo.getId());
        Assert.assertEquals("10:00", vuelo.getHoraSalida());
        Assert.assertEquals("12:00", vuelo.getHoraLlegada());
        Assert.assertEquals("Barcelona", vuelo.getOrigen());
        Assert.assertEquals("Madrid", vuelo.getDestino());
        Assert.assertEquals("A1", vuelo.getAvionAsignado());
    }

    @Test //ya funciona
    public void testGetAllVuelos() {
        List<Vuelo> vuelos = vueloManager.getAllVuelos();
        Assert.assertEquals(2, vuelos.size());
    }

    @Test
    public void testAddMaletaExistente() {
        vueloManager.facturarMaleta("V1", "User1");
        vueloManager.facturarMaleta("V1", "User2");

        // Recuperar maletas asociadas al vuelo "V1"
        List<Maleta> maletas = vueloManager.getMaletasByVuelo("V1");

        Assert.assertNotNull(maletas); // Verificar que la lista no sea nula
        Assert.assertEquals(2, maletas.size()); // Verificar que hay 2 maletas
        Assert.assertEquals("User2", maletas.get(0).getUserId()); // Última maleta añadida primero
        Assert.assertEquals("User1", maletas.get(1).getUserId());
    }



    @Test //YA FUNCIONA 
    public void testMaletasRetrievalOrder() {
        vueloManager.facturarMaleta("V1", "User1");
        vueloManager.facturarMaleta("V1", "User2");
        List<Maleta> maletaList = vueloManager.getMaletasByVuelo("V1");
        Assert.assertEquals("User2", maletaList.get(0).getUserId()); // Última maleta facturada primero
        Assert.assertEquals("User1", maletaList.get(1).getUserId());
    }

    @Test
    public void testDeleteVueloExistente() {
        // Eliminar el vuelo con ID "V1"
        vueloManager.deleteVuelo("V1");

        // Verificar que el vuelo ya no existe
        Vuelo vuelo = null;
        try {
            vuelo = vueloManager.getVuelo("V1");
        } catch (IllegalArgumentException e) {
            // El vuelo no debe existir
            Assert.assertNull(vuelo);
        }

        // Verificar que solo queda un vuelo en el sistema
        Assert.assertEquals(1, vueloManager.getAllVuelos().size());
    }
}
