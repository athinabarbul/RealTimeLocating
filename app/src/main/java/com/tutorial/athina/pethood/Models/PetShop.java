package com.tutorial.athina.pethood.Models;

public class PetShop {

    private String name;
    Double lat, lng;

    public PetShop() {
    }

    public PetShop(String name, Double lat, Double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
