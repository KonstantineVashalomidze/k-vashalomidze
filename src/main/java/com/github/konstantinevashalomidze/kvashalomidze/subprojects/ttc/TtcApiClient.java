package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class TtcApiClient {

    private static final String BASE_URL = "https://transit.ttc.com.ge/pis-gateway";
    private static final String API_KEY = "c0a2f304-551a-4d08-b8df-2c53ecd57f9f";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Cacheable("ttc-stops")
    public List<StopDto> getAllStops() {
        return get("/api/v2/stops?locale=EN", new TypeReference<>() {});
    }

    public List<ArrivalTimeDto> getArrivalTimes(String stopId, int maxArrivals, boolean ignoreScheduled) {
        return get("/api/v2/stops/" + stopId + "/arrival-times"
                        + "?locale=EN"
                        + "&maxNumberOfArrivalTimes=" + maxArrivals
                        + "&ignoreScheduledArrivalTimes=" + ignoreScheduled,
                new TypeReference<>() {});
    }

    private <T> T get(String path, TypeReference<T> type) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + path))
                    .header("X-Api-Key", API_KEY)
                    .GET()
                    .build();

            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), type);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("TTC API call interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("TTC API call failed: " + e.getMessage(), e);
        }
    }
}