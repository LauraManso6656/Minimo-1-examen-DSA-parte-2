package edu.upc.dsa.models;
import java.util.HashMap;
import java.util.Map;

public class Maleta {
    private String id;
    private String userId;
   private String vueloId;

    public Maleta() {
    }

    public Maleta(String id, String userId, String vueloId) {
        this.id = id;
        this.userId = userId;

        this.vueloId = vueloId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getvueloId() {
        return vueloId;
    }

    public void setvueloId(String vueloId) {
        this.vueloId = vueloId;
    }
}





