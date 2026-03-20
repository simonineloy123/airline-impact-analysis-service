package com.airline.impactanalysis.infrastructure.adapter.in.messaging;

import com.airline.impactanalysis.domain.model.DisruptionEventPayload;
import com.airline.impactanalysis.domain.port.in.AnalyzeImpactUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DisruptionEventConsumer {

    private static final Logger LOG = Logger.getLogger(DisruptionEventConsumer.class);

    private final AnalyzeImpactUseCase analyzeImpactUseCase;
    private final ObjectMapper         objectMapper;

    public DisruptionEventConsumer(AnalyzeImpactUseCase analyzeImpactUseCase) {
        this.analyzeImpactUseCase = analyzeImpactUseCase;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Incoming("disruption-events-in")
    public void consume(String message) {
        try {
            LOG.debugf("Evento de disrupción recibido: %s", message);
            DisruptionEventPayload payload = objectMapper.readValue(message, DisruptionEventPayload.class);
            analyzeImpactUseCase.execute(payload);
        } catch (Exception e) {
            LOG.errorf("Error procesando evento de disrupción: %s | error: %s",
                message, e.getMessage());
        }
    }
}
