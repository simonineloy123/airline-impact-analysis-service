package com.airline.impactanalysis.domain.model;

public enum ImpactSeverity {
    LOW,       // < 50 pasajeros afectados
    MEDIUM,    // 50-200 pasajeros
    HIGH,      // 200-500 pasajeros
    CRITICAL;  // > 500 pasajeros o cancelación

    public static ImpactSeverity fromPassengers(int passengers) {
        if (passengers < 50)  return LOW;
        if (passengers < 200) return MEDIUM;
        if (passengers < 500) return HIGH;
        return CRITICAL;
    }
}
