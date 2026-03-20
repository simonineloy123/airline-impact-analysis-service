package com.airline.impactanalysis.infrastructure.adapter.out.persistence;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.model.ImpactSeverity;
import com.airline.impactanalysis.domain.port.out.ImpactReportRepositoryPort;
import com.airline.impactanalysis.shared.mapper.ImpactReportMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ImpactReportRepositoryAdapter implements ImpactReportRepositoryPort {

    private final ImpactReportMapper mapper;

    public ImpactReportRepositoryAdapter(ImpactReportMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ImpactReport save(ImpactReport report) {
        ImpactReportEntity entity = mapper.toEntity(report);
        ImpactReportEntity.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<ImpactReport> findById(UUID id) {
        return ImpactReportEntity.<ImpactReportEntity>findByIdOptional(id)
            .map(mapper::toDomain);
    }

    @Override
    public List<ImpactReport> findAll() {
        return ImpactReportEntity.<ImpactReportEntity>listAll()
            .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ImpactReport> findBySeverity(ImpactSeverity severity) {
        return ImpactReportEntity.findBySeverity(severity)
            .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ImpactReport> findByDisruptionId(UUID disruptionId) {
        return ImpactReportEntity.findByDisruptionId(disruptionId)
            .stream().map(mapper::toDomain).toList();
    }
}
