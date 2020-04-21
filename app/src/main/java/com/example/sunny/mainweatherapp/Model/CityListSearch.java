package com.example.sunny.mainweatherapp.Model;

 // a model that will allow the user to search a city
 // this will show the country code and the coords of that city
public class CityListSearch {

    private int id;
    private String name,country;
    private Coord coord;

    public CityListSearch() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
