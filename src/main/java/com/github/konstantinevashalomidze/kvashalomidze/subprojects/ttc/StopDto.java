package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;


public record StopDto(
        String id,
        String code,
        String name,
        double lat,
        double lon,
        String vehicleMode
) {}
