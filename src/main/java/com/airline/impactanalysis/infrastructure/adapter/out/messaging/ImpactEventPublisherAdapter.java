package com.airline.impactanalysis.infrastructure.adapter.out.messaging;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.port.out.ImpactEventPublisherPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ImpactEventPublisherAdapter implements ImpactEventPublisherPort {

    private static final Logger LOG = Logger.getLogger(ImpactEventPublisherAdapter.class);

    private final Emitter<String> impactEventsEmitter;
    private final ObjectMapper    objectMapper;

    public ImpactEventPublisherAdapter(
        @Channel("impact-events") Emitter<String> impactEventsEmitter
    ) {
        this.impactEventsEmitter = impactEventsEmitter;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Override
    public void publish(ImpactReport report) {
        try {
            String payload = buildPayload(report);
            impactEventsEmitter.send(payload);
            LOG.infof("Impacto publicado → topic: impact_events | vuelo: %s | pasajeros: %d | severidad: %s",
                report.getFlightNumber(),
                report.getAffectedPassengers(),
                report.getSeverity());
        } catch (JsonProcessingException e) {
            LOG.errorf("Error serializando impacto: %s", e.getMessage());
            throw new RuntimeException("Error publicando impacto a Kafka", e);
        }
    }

    private String buildPayload(ImpactReport report) throws JsonProcessingException {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id",                 report.getId().toString());
        node.put("disruptionId",       report.getDisruptionId() != null ? report.getDisruptionId().toString() : null);
        node.put("flightNumber",       report.getFlightNumber());
        node.put("origin",             report.getOrigin());
        node.put("destination",        report.getDestination());
        node.put("disruptionType",     report.getDisruptionType());
        node.put("affectedPassengers", report.getAffectedPassengers());
        node.put("affectedFlights",    report.getAffectedFlights());
        node.put("totalDelayMinutes",  report.getTotalDelayMinutes());
        node.put("severity",           report.getSeverity().name());
        node.put("calculatedAt",       report.getCalculatedAt().toString());
        return objectMapper.writeValueAsString(node);
    }
}
