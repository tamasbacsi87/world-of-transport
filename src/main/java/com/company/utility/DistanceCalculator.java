package com.company.utility;

/**
* Utility for calculating distances between geographic coordinates.
*/
public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * Computes the circle distance between two points.
     * 
     * @param minlat Latitude of first point
     * @param minLon Longitude of first point
     * @param maxLat Latitude of second point
     * @param maxLon Longitude of second point
     * @return Distance in kilometers
     */
    public static double computeDistance(double minlat, double minLon, double maxLat, double maxLon) {
        double diffLat = Math.toRadians(maxLat - minlat);
        double diffLon = Math.toRadians(maxLon - minLon);
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
                + Math.cos(Math.toRadians(minlat)) * Math.cos(Math.toRadians(maxLat))
                * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
