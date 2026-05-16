package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NearbyStopsFormatter {

    public String format(List<NearbyStop> nearbyStops) {
        if (nearbyStops.isEmpty()) {
            return "😕 No stops found within 600 m of your location.";
        }

        StringBuilder sb = new StringBuilder("🗺 <b>Nearby stops</b>\n\n");

        for (NearbyStop ns : nearbyStops) {
            sb.append(String.format("🚏 <b>%s</b>  <i>(%.0f m away)</i>\n",
                    ns.stop().name(), ns.distanceMeters()));

            List<ArrivalTimeDto> arrivals = ns.arrivals();
            if (arrivals == null || arrivals.isEmpty()) {
                sb.append("  — no upcoming arrivals\n");
            } else {
                for (ArrivalTimeDto a : arrivals) {
                    sb.append(formatArrival(a));
                }
            }
            sb.append("\n");
        }

        return sb.toString().trim();
    }

    private String formatArrival(ArrivalTimeDto a) {
        String modeIcon = switch (a.vehicleMode() == null ? "" : a.vehicleMode()) {
            case "SUBWAY"  -> "🚇";
            case "GONDOLA" -> "🚡";
            default        -> "🚌";
        };

        Integer minutes = a.realtime() ? a.realtimeArrivalMinutes() : a.scheduledArrivalMinutes();
        String timeStr;
        if (minutes == null) {
            timeStr = "—";
        } else if (minutes == 0) {
            timeStr = "arriving now";
        } else {
            timeStr = minutes + " min";
        }

        String realtimeBadge = a.realtime() ? " 🟢" : " 🕐";

        return String.format("  %s <b>%s</b> → %s — %s%s\n",
                modeIcon,
                a.shortName(),
                a.headsign() != null ? a.headsign() : "?",
                timeStr,
                realtimeBadge);
    }
}