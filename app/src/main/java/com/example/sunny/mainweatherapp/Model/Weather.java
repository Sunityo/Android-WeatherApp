package com.example.sunny.mainweatherapp.Model;

// this is the main feature for the app.
// this gets the description and icon from the API, so the user will see an image of the weather
// They will also get a description, saying what the weather is in their location

public class Weather {

    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
