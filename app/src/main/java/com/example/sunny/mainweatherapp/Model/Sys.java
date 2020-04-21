package com.example.sunny.mainweatherapp.Model;

// this is the sys information from the API
// this controls the information such as the message, so the user will be displayed a message of their location
// the country is also there, since I want the user to see their county code, so people in england will see "UK", or "GB"
// the sunrise and sunset are int, as they will displayed as 11:00
// there are getters and settlers that come from the private variables

public class Sys {

    private int type;
    private int id;
    private double message;
    private String country;
    private int sunrise;
    private int sunset;

    public Sys() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
