package edu.upc.dsa.services;

import edu.upc.dsa.VueloManager;
import edu.upc.dsa.VueloManagerImpl;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Avion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/vuelos", description = "Endpoint to Vuelo Service")
@Path("/vuelos")
public class VueloService {

    private VueloManager vueloManager;

    public VueloService() {
        this.vueloManager = VueloManagerImpl.getInstance();
        if (vueloManager.size() == 0) {
            this.vueloManager.addAvion("A1", "Boeing 747", "Ryanair");
            this.vueloManager.addAvion("A2", "Airbus 430", "Iberia");

            this.vueloManager.addVuelo("V1", "10:00", "12:00", "A1", "Barcelona", "Madrid");
            this.vueloManager.addVuelo("V2", "15:00", "17:00", "A2", "Madrid", "París");
        }
    }

    // Obtener todos los vuelos
    @GET
    @ApiOperation(value = "Get all vuelos", notes = "Returns a list of all vuelos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Vuelo.class, responseContainer = "List")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVuelos() {
        List<Vuelo> vuelos = this.vueloManager.getAllVuelos();
        GenericEntity<List<Vuelo>> entity = new GenericEntity<List<Vuelo>>(vuelos) {};
        return Response.status(200).entity(entity).build();
    }

    // Obtener un vuelo por ID
    @GET
    @ApiOperation(value = "Get a vuelo by ID", notes = "Returns a specific vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Vuelo.class),
            @ApiResponse(code = 404, message = "Vuelo not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVuelo(@PathParam("id") String id) {
        Vuelo vuelo = this.vueloManager.getVuelo(id);
        if (vuelo == null) return Response.status(404).build();
        return Response.status(200).entity(vuelo).build();
    }

    // Eliminar un vuelo
    @DELETE
    @ApiOperation(value = "Delete a vuelo", notes = "Deletes a vuelo by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Vuelo not found")
    })
    @Path("/{id}")
    public Response deleteVuelo(@PathParam("id") String id) {
        Vuelo vuelo = this.vueloManager.getVuelo(id);
        if (vuelo == null) return Response.status(404).build();
        this.vueloManager.deleteVuelo(id);
        return Response.status(200).build();
    }

    // Crear un nuevo vuelo
    @POST
    @ApiOperation(value = "Create a new vuelo", notes = "Creates a new vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vuelo.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newVuelo(Vuelo vuelo) {
        if (vuelo.getHoraSalida() == null || vuelo.getHoraLlegada() == null || vuelo.getOrigen() == null || vuelo.getDestino() == null) {
            return Response.status(500).entity(vuelo).build();
        }
        this.vueloManager.addVuelo(vuelo.getId(), vuelo.getHoraSalida(), vuelo.getHoraLlegada(), vuelo.getAvionAsignado(), vuelo.getOrigen(), vuelo.getDestino());
        return Response.status(201).entity(vuelo).build();
    }

    // Facturar una maleta para un vuelo
    @POST
    @ApiOperation(value = "Check-in baggage", notes = "Adds a new baggage to a vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Maleta.class),
            @ApiResponse(code = 404, message = "Vuelo not found")
    })
    @Path("/{id}/maletas")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response facturarMaleta(@PathParam("id") String vueloId, Maleta maleta) {
        try {
            this.vueloManager.facturarMaleta(vueloId, maleta.getUserId());
            return Response.status(201).entity(maleta).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    // Obtener maletas por vuelo
    @GET
    @ApiOperation(value = "Get all maletas by vuelo", notes = "Returns all baggage checked in for a vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Maleta.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Vuelo not found")
    })
    @Path("/{id}/maletas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaletasByVuelo(@PathParam("id") String vueloId) {
        try {
            List<Maleta> maletas = this.vueloManager.getMaletasByVuelo(vueloId);
            GenericEntity<List<Maleta>> entity = new GenericEntity<List<Maleta>>(maletas) {};
            return Response.status(200).entity(entity).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
}
