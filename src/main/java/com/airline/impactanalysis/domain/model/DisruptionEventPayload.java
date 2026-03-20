package com.airline.impactanalysis.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisruptionEventPayload {

    private UUID          id;
    private UUID          flightId;
    private String        flightNumber;
    private String        origin;
    private String        destination;
    private String        type;
    private String        severity;
    private String        description;
    private int           delayMinutes;
    private int           passengers;
    private LocalDateTime detectedAt;
    private boolean       resolved;

    public DisruptionEventPayload() {}

    public UUID getId()                      { return id; }
    public void setId(UUID v)                { this.id = v; }
    public UUID getFlightId()                { return flightId; }
    public void setFlightId(UUID v)          { this.flightId = v; }
    public String getFlightNumber()          { return flightNumber; }
    public void setFlightNumber(String v)    { this.flightNumber = v; }
    public String getOrigin()                { return origin; }
    public void setOrigin(String v)          { this.origin = v; }
    public String getDestination()           { return destination; }
    public void setDestination(String v)     { this.destination = v; }
    public String getType()                  { return type; }
    public void setType(String v)            { this.type = v; }
    public String getSeverity()              { return severity; }
    public void setSeverity(String v)        { this.severity = v; }
    public String getDescription()           { return description; }
    public void setDescription(String v)     { this.description = v; }
    public int getDelayMinutes()             { return delayMinutes; }
    public void setDelayMinutes(int v)       { this.delayMinutes = v; }
    public int getPassengers()               { return passengers; }
    public void setPassengers(int v)         { this.passengers = v; }
    public LocalDateTime getDetectedAt()     { return detectedAt; }
    public void setDetectedAt(LocalDateTime v) { this.detectedAt = v; }
    public boolean isResolved()              { return resolved; }
    public void setResolved(boolean v)       { this.resolved = v; }
}
