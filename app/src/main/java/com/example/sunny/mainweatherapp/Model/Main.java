package com.example.sunny.mainweatherapp.Model;

// getting the information from the API
// We get the the main information that i want for the user to see
// there are settlers and getters in the Main, so that each feature returns the correct function.

// the temp, temp_min and temp_max are doubles since the information comes in 00.00 .
// i could have used them as a float, but a double is more suitable
// the humidity is a int since its a percentage, so it can go only to 100%

public class Main {

    private double temp;
    private int humidity;
    private double temp_min;
    private double temp_max;

    public Main(){

    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
