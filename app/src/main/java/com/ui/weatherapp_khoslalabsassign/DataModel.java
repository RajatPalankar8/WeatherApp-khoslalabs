package com.ui.weatherapp_khoslalabsassign;

public class DataModel {
    String name;
    String version;
    int id_;
    int image;
    String weather;
    public DataModel(String name, String version, int id_, int image,String weather) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
        this.weather=weather;
    }
    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public int getImage() {
        return image;
    }
    public int getId() {
        return id_;
    }

    public String getWeather() {
        return weather;
    }
}
