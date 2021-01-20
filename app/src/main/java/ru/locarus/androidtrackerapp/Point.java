package ru.locarus.androidtrackerapp;

import java.io.Serializable;

public class Point implements Serializable {
    private double latitude;
    private double longitude;
    private float speed;
    private double time;
    private double altitude;

    public Point(double latitude, double longitude, float speed, double time, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.time = time;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }

    public double getAltitude() {
        return altitude;
    }
}
