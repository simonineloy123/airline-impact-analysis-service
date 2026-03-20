package com.airline.impactanalysis.application.usecase;

import com.airline.impactanalysis.domain.model.*;
import com.airline.impactanalysis.domain.port.in.AnalyzeImpactUseCase;
import com.airline.impactanalysis.domain.port.out.ImpactEventPublisherPort;
import com.airline.impactanalysis.domain.port.out.ImpactReportRepositoryPort;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AnalyzeImpactUseCaseImpl implements AnalyzeImpactUseCase {

    private static final Logger LOG = Logger.getLogger(AnalyzeImpactUseCaseImpl.class);

    private final ImpactReportRepositoryPort reportRepository;
    private final ImpactEventPublisherPort   eventPublisher;
    private final Event<ImpactReport>        impactReportEvent;
    private final Vertx                      vertx;

    public AnalyzeImpactUseCaseImpl(
        ImpactReportRepositoryPort reportRepository,
        ImpactEventPublisherPort eventPublisher,
        Event<ImpactReport> impactReportEvent,
        Vertx vertx
    ) {
        this.reportRepository  = reportRepository;
        this.eventPublisher    = eventPublisher;
        this.impactReportEvent = impactReportEvent;
        this.vertx             = vertx;
    }

    @Override
    public ImpactReport execute(DisruptionEventPayload disruption) {
        LOG.debugf("Analizando impacto de disrupción: %s | vuelo: %s",
            disruption.getType(), disruption.getFlightNumber());

        ImpactReport report = calculateImpact(disruption);

        vertx.executeBlocking(() -> {
            saveAndPublish(report);
            return null;
        });

        return report;
    }

    private ImpactReport calculateImpact(DisruptionEventPayload disruption) {
        int affectedPassengers = disruption.getPassengers();
        int affectedFlights    = calculateAffectedFlights(disruption);
        int totalDelayMinutes  = calculateTotalDelay(disruption);

        return ImpactReport.builder()
            .disruptionId(disruption.getId())
            .flightNumber(disruption.getFlightNumber())
            .origin(disruption.getOrigin())
            .destination(disruption.getDestination())
            .disruptionType(disruption.getType())
            .affectedPassengers(affectedPassengers)
            .affectedFlights(affectedFlights)
            .totalDelayMinutes(totalDelayMinutes)
            .severity(ImpactSeverity.fromPassengers(affectedPassengers))
            .build();
    }

    private int calculateAffectedFlights(DisruptionEventPayload disruption) {
        return switch (disruption.getType()) {
            case "CANCELLATION" -> 3; 
            case "DELAY"        -> disruption.getDelayMinutes() > 120 ? 2 : 1;
            case "DIVERSION"    -> 2;
            default             -> 1;
        };
    }

    private int calculateTotalDelay(DisruptionEventPayload disruption) {
        return switch (disruption.getType()) {
            case "CANCELLATION" -> 480; // 8 horas promedio para reubicación
            case "DELAY"        -> disruption.getDelayMinutes();
            case "DIVERSION"    -> disruption.getDelayMinutes() + 120;
            default             -> disruption.getDelayMinutes();
        };
    }

    @Transactional
    public void saveAndPublish(ImpactReport report) {
        ImpactReport saved = reportRepository.save(report);
        LOG.infof("Impacto calculado → vuelo: %s | pasajeros: %d | vuelos: %d | severidad: %s",
            saved.getFlightNumber(),
            saved.getAffectedPassengers(),
            saved.getAffectedFlights(),
            saved.getSeverity());
        impactReportEvent.fire(saved);
    }

    public void onImpactReportSaved(
        @Observes(during = TransactionPhase.AFTER_SUCCESS) ImpactReport report
    ) {
        eventPublisher.publish(report);
    }
}
