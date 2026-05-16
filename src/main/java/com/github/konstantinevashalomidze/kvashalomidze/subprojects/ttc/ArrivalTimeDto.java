package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;


public record ArrivalTimeDto(
        String shortName,
        String color,
        String headsign,
        String patternSuffix,
        String vehicleMode,
        boolean realtime,
        Integer realtimeArrivalMinutes,
        Integer scheduledArrivalMinutes
) {}