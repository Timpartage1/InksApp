package com.example.inksapp.model;

public class WaterDataModel {

    private int  water_level;
    private String water_date;


    public WaterDataModel() {
    }

    public WaterDataModel(int water_level, String water_date) {
        this.water_level = water_level;
        this.water_date = water_date;
    }

    public int getWater_level() {
        return water_level;
    }

    public void setWater_level(int water_level) {
        this.water_level = water_level;
    }

    public String getWater_date() {
        return water_date;
    }

    public void setWater_date(String water_date) {
        this.water_date = water_date;
    }
}
