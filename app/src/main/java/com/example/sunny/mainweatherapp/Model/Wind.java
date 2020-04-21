package com.example.sunny.mainweatherapp.Model;


// this is the wind feature of the API
// the data is displayed as an double instead of an int, due to an number exception

public class Wind {

    private double speed;

    public Wind(){

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
