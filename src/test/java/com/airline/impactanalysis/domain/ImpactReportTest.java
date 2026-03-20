package com.airline.impactanalysis.domain;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.model.ImpactSeverity;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ImpactReportTest {

    @Test
    void shouldCreateImpactReportWithMediumSeverity() {
        ImpactReport report = ImpactReport.builder()
            .disruptionId(UUID.randomUUID())
            .flightNumber("AR1234")
            .origin("EZE")
            .destination("MAD")
            .disruptionType("DELAY")
            .affectedPassengers(150)
            .affectedFlights(1)
            .totalDelayMinutes(90)
            .build();

        assertNotNull(report.getId());
        assertNotNull(report.getCalculatedAt());
        assertEquals(ImpactSeverity.MEDIUM, report.getSeverity());
    }

    @Test
    void shouldCalculateLowSeverity() {
        ImpactReport report = ImpactReport.builder()
            .disruptionId(UUID.randomUUID())
            .flightNumber("X001")
            .origin("EZE").destination("SCL")
            .disruptionType("DELAY")
            .affectedPassengers(30)
            .affectedFlights(1)
            .totalDelayMinutes(30)
            .build();

        assertEquals(ImpactSeverity.LOW, report.getSeverity());
    }

    @Test
    void shouldCalculateHighSeverity() {
        ImpactReport report = ImpactReport.builder()
            .disruptionId(UUID.randomUUID())
            .flightNumber("X002")
            .origin("EZE").destination("MAD")
            .disruptionType("DELAY")
            .affectedPassengers(300)
            .affectedFlights(2)
            .totalDelayMinutes(120)
            .build();

        assertEquals(ImpactSeverity.HIGH, report.getSeverity());
    }

    @Test
    void shouldCalculateCriticalSeverity() {
        ImpactReport report = ImpactReport.builder()
            .disruptionId(UUID.randomUUID())
            .flightNumber("X003")
            .origin("EZE").destination("MAD")
            .disruptionType("CANCELLATION")
            .affectedPassengers(600)
            .affectedFlights(3)
            .totalDelayMinutes(480)
            .build();

        assertEquals(ImpactSeverity.CRITICAL, report.getSeverity());
    }

    @Test
    void shouldMapSeverityCorrectly() {
        assertEquals(ImpactSeverity.LOW,      ImpactSeverity.fromPassengers(10));
        assertEquals(ImpactSeverity.LOW,      ImpactSeverity.fromPassengers(49));
        assertEquals(ImpactSeverity.MEDIUM,   ImpactSeverity.fromPassengers(50));
        assertEquals(ImpactSeverity.MEDIUM,   ImpactSeverity.fromPassengers(199));
        assertEquals(ImpactSeverity.HIGH,     ImpactSeverity.fromPassengers(200));
        assertEquals(ImpactSeverity.HIGH,     ImpactSeverity.fromPassengers(499));
        assertEquals(ImpactSeverity.CRITICAL, ImpactSeverity.fromPassengers(500));
    }

    @Test
    void shouldFailWithoutDisruptionId() {
        assertThrows(IllegalArgumentException.class, () ->
            ImpactReport.builder()
                .flightNumber("AR0000")
                .disruptionType("DELAY")
                .build()
        );
    }
}