package com.example.sunny.mainweatherapp.Model;

// This is the coords that the app will use
// the user's longitude and latitude
// it also sets them in a double, since its a big number

public class Coord {

    private double lon;
    private double lat;

    public Coord() {

    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    // here we override the stringbuilder so that [] appears with the coords on the app
    @Override
    public String toString() {
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
