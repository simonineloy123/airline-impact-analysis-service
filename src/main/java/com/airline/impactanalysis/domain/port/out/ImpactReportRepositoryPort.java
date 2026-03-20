package com.airline.impactanalysis.domain.port.out;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.model.ImpactSeverity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImpactReportRepositoryPort {
    ImpactReport save(ImpactReport report);
    Optional<ImpactReport> findById(UUID id);
    List<ImpactReport> findAll();
    List<ImpactReport> findBySeverity(ImpactSeverity severity);
    List<ImpactReport> findByDisruptionId(UUID disruptionId);
}
