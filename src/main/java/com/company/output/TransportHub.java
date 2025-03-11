package com.company.output;

/**
* Represents a transport hub with its name,location and distance from the user.
*/
public class TransportHub {

    private final String name;
    private final double latitude;
    private final double longitude;
    private final double distance;
 
    public TransportHub(String name, double latitude, double longitude, double distance) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
 
    public double getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }
 
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Latitude: %f, Longitude: %f, Distance: %f km", name, latitude, longitude, distance);
    }
}
