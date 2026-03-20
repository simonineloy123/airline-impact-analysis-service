package com.airline.impactanalysis.shared.mapper;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.infrastructure.adapter.out.persistence.ImpactReportEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImpactReportMapper {

    public ImpactReport toDomain(ImpactReportEntity entity) {
        return ImpactReport.builder()
            .id(entity.id)
            .disruptionId(entity.disruptionId)
            .flightNumber(entity.flightNumber)
            .origin(entity.origin)
            .destination(entity.destination)
            .disruptionType(entity.disruptionType)
            .affectedPassengers(entity.affectedPassengers)
            .affectedFlights(entity.affectedFlights)
            .totalDelayMinutes(entity.totalDelayMinutes)
            .severity(entity.severity)
            .calculatedAt(entity.calculatedAt)
            .build();
    }

    public ImpactReportEntity toEntity(ImpactReport domain) {
        ImpactReportEntity entity  = new ImpactReportEntity();
        entity.disruptionId        = domain.getDisruptionId();
        entity.flightNumber        = domain.getFlightNumber();
        entity.origin              = domain.getOrigin();
        entity.destination         = domain.getDestination();
        entity.disruptionType      = domain.getDisruptionType();
        entity.affectedPassengers  = domain.getAffectedPassengers();
        entity.affectedFlights     = domain.getAffectedFlights();
        entity.totalDelayMinutes   = domain.getTotalDelayMinutes();
        entity.severity            = domain.getSeverity();
        entity.calculatedAt        = domain.getCalculatedAt();
        return entity;
    }
}
