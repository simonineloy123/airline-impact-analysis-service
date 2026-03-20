package com.airline.impactanalysis.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ImpactReport {

    private UUID          id;
    private UUID          disruptionId;
    private String        flightNumber;
    private String        origin;
    private String        destination;
    private String        disruptionType;
    private int           affectedPassengers;
    private int           affectedFlights;
    private int           totalDelayMinutes;
    private ImpactSeverity severity;
    private LocalDateTime calculatedAt;

    private ImpactReport() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ImpactReport r = new ImpactReport();

        public Builder id(UUID id)                          { r.id = id; return this; }
        public Builder disruptionId(UUID v)                 { r.disruptionId = v; return this; }
        public Builder flightNumber(String v)               { r.flightNumber = v; return this; }
        public Builder origin(String v)                     { r.origin = v; return this; }
        public Builder destination(String v)                { r.destination = v; return this; }
        public Builder disruptionType(String v)             { r.disruptionType = v; return this; }
        public Builder affectedPassengers(int v)            { r.affectedPassengers = v; return this; }
        public Builder affectedFlights(int v)               { r.affectedFlights = v; return this; }
        public Builder totalDelayMinutes(int v)             { r.totalDelayMinutes = v; return this; }
        public Builder severity(ImpactSeverity v)           { r.severity = v; return this; }
        public Builder calculatedAt(LocalDateTime v)        { r.calculatedAt = v; return this; }

        public ImpactReport build() {
            if (r.id == null)          r.id = UUID.randomUUID();
            if (r.calculatedAt == null) r.calculatedAt = LocalDateTime.now();
            if (r.severity == null)    r.severity = ImpactSeverity.fromPassengers(r.affectedPassengers);
            validate();
            return r;
        }

        private void validate() {
            if (r.disruptionId == null)
                throw new IllegalArgumentException("El ID de disrupción es obligatorio");
            if (r.flightNumber == null || r.flightNumber.isBlank())
                throw new IllegalArgumentException("El número de vuelo es obligatorio");
        }
    }

    public UUID getId()                    { return id; }
    public UUID getDisruptionId()          { return disruptionId; }
    public String getFlightNumber()        { return flightNumber; }
    public String getOrigin()              { return origin; }
    public String getDestination()         { return destination; }
    public String getDisruptionType()      { return disruptionType; }
    public int getAffectedPassengers()     { return affectedPassengers; }
    public int getAffectedFlights()        { return affectedFlights; }
    public int getTotalDelayMinutes()      { return totalDelayMinutes; }
    public ImpactSeverity getSeverity()    { return severity; }
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
}
