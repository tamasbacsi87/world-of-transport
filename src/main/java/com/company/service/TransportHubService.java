package com.company.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.company.input.InputData;
import com.company.output.TransportHub;
import com.company.utility.DistanceCalculator;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.PostSearchOptions;
import com.ibm.cloud.cloudant.v1.model.SearchResult;
import com.ibm.cloud.cloudant.v1.model.SearchResultRow;
import com.ibm.cloud.sdk.core.security.NoAuthAuthenticator;

public class TransportHubService {

    private static final String CLOUDANT_URL = "https://mikerhodes.cloudant.com";
    private static final String SERVICE_NAME = "cloudant";
    private static final String DATABASE_NAME = "airportdb";
    private static final String DESIGN_DOC = "view1";
    private static final String INDEX_NAME = "geo";

    /**
     * Retrieves transport hubs in abscending order that are within a specific distance of the user's location.
     *
     * @param inputData Input data containing the user's location and distance
     * @return List of transport hubs sorted by distance
     */
    public static List<TransportHub> queryTransportHubs(InputData inputData) {
        // Initialize Cloudant service
        Cloudant service = new Cloudant(SERVICE_NAME, new NoAuthAuthenticator());
        service.setServiceUrl(CLOUDANT_URL);

        // Build query string
        String query = buildQuery(inputData.getLatitude(), inputData.getLongitude(), inputData.getDistance());

        PostSearchOptions searchOptions = new PostSearchOptions.Builder()
            .db(DATABASE_NAME)
            .ddoc(DESIGN_DOC)
            .index(INDEX_NAME)
            .query(query)
            .build();

        // Execute search query
        SearchResult result = service.postSearch(searchOptions)
            .execute()
            .getResult();

        List<TransportHub> hubs = new ArrayList<>();

        for (SearchResultRow row : result.getRows()) {
            String name = row.getFields().get("name").toString();
            double hubLatitude = Double.parseDouble(row.getFields().get("lat").toString());
            double hubLongitude = Double.parseDouble(row.getFields().get("lon").toString());
            double distance = DistanceCalculator.computeDistance(inputData.getLatitude(), inputData.getLongitude(), hubLatitude, hubLongitude);
            hubs.add(new TransportHub(name, hubLatitude, hubLongitude, distance));
        }

        // Sort hubs by distance
        hubs.sort(Comparator.comparingDouble(TransportHub::getDistance));

        return hubs;
    }

    /**
    * Generates a query string for geospatial search.
    *
    * @return the query string in the format "lat:[minLat TO maxLat] AND lon:[minLon TO maxLon]"
    */
    private static String buildQuery(double latitude, double longitude, double distance) {
        double maxLat = latitude + (distance/111);
        double minLat = latitude - (distance/111);
        double maxLon = longitude + (distance/111);
        double minLon = longitude - (distance/111);

        return String.format("lat:[%f TO %f] AND lon:[%f TO %f]", minLat, maxLat, minLon, maxLon);
    }
}
