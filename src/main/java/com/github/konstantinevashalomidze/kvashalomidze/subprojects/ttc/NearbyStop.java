package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;


import java.util.List;

public record NearbyStop(
        StopDto stop,
        double distanceMeters,
        List<ArrivalTimeDto> arrivals
) {}