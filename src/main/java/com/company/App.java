package com.company;

import com.company.input.InputData;
import com.company.output.TransportHub;
import com.company.service.TransportHubService;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main( String[] args )
    {
        // User input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the latitude: ");
        double latitude = sc.nextDouble();
        System.out.println("Enter the longitude: ");
        double longitude = sc.nextDouble();
        System.out.println("Enter the distance (km): ");
        double distance = sc.nextDouble();
        sc.close();

        InputData inputData = new InputData(latitude, longitude, distance);

        // Retrieve transport hubs based on user location
        List<TransportHub> hubs = TransportHubService.queryTransportHubs(inputData);

        if (hubs.isEmpty()) {
            System.out.println("No transport hubs found within the given distance!");
        } else {
            System.out.println("Transport hubs within the given distance:");
            for (TransportHub hub : hubs) {
                System.out.println(hub.toString());
            }
        }
    }
}
