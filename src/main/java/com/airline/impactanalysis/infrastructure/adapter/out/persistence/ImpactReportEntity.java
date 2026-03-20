package com.airline.impactanalysis.infrastructure.adapter.out.persistence;

import com.airline.impactanalysis.domain.model.ImpactSeverity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "impact_reports")
public class ImpactReportEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "disruption_id", nullable = false)
    public UUID disruptionId;

    @Column(name = "flight_number", nullable = false, length = 10)
    public String flightNumber;

    @Column(name = "origin", length = 10)
    public String origin;

    @Column(name = "destination", length = 10)
    public String destination;

    @Column(name = "disruption_type", nullable = false, length = 30)
    public String disruptionType;

    @Column(name = "affected_passengers", nullable = false)
    public int affectedPassengers;

    @Column(name = "affected_flights", nullable = false)
    public int affectedFlights;

    @Column(name = "total_delay_minutes", nullable = false)
    public int totalDelayMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 20)
    public ImpactSeverity severity;

    @Column(name = "calculated_at", nullable = false)
    public LocalDateTime calculatedAt;

    @PrePersist
    public void prePersist() {
        if (calculatedAt == null) calculatedAt = LocalDateTime.now();
    }

    public static List<ImpactReportEntity> findBySeverity(ImpactSeverity severity) {
        return list("severity", severity);
    }

    public static List<ImpactReportEntity> findByDisruptionId(UUID disruptionId) {
        return list("disruptionId", disruptionId);
    }
}
