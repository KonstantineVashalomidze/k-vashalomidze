package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NearbyStopsService {

    /** Only show stops within this radius (metres). */
    private static final double MAX_RADIUS_METERS = 600.0;

    /** Number of closest stops to query arrivals for. */
    private static final int MAX_STOPS = 3;

    /** Arrivals to fetch per stop. */
    private static final int MAX_ARRIVALS_PER_STOP = 5;

    private final TtcApiClient ttcApiClient;

    public NearbyStopsService(TtcApiClient ttcApiClient) {
        this.ttcApiClient = ttcApiClient;
    }

    /**
     * Returns the closest stops to the given coordinates, each enriched
     * with upcoming arrival times.
     */
    public List<NearbyStop> findNearbyWithArrivals(double userLat, double userLon) {
        List<StopDto> allStops = ttcApiClient.getAllStops();

        return allStops.stream()
                .map(stop -> new NearbyStop(
                        stop,
                        haversineMeters(userLat, userLon, stop.lat(), stop.lon()),
                        null  // arrivals filled in below
                ))
                .filter(ns -> ns.distanceMeters() <= MAX_RADIUS_METERS)
                .sorted(Comparator.comparingDouble(NearbyStop::distanceMeters))
                .limit(MAX_STOPS)
                .map(ns -> {
                    List<ArrivalTimeDto> arrivals = ttcApiClient.getArrivalTimes(
                            ns.stop().id(),
                            MAX_ARRIVALS_PER_STOP,
                            false   // include scheduled when no real-time available
                    );
                    return new NearbyStop(ns.stop(), ns.distanceMeters(), arrivals);
                })
                .toList();
    }

    /**
     * Haversine formula — returns distance in metres between two lat/lon points.
     */
    private static double haversineMeters(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6_371_000.0; // Earth radius in metres
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
