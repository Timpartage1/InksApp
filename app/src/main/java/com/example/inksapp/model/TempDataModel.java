package com.example.inksapp.model;

public class TempDataModel {

private float temperature;
private float humidity;
private String temp_date;

    public TempDataModel() {
    }

    public TempDataModel(float temperature, float humidity, String temp_date) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.temp_date = temp_date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getTemp_date() {
        return temp_date;
    }

    public void setTemp_date(String temp_date) {
        this.temp_date = temp_date;
    }
}
