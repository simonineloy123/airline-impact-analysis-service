package com.airline.impactanalysis.application.usecase;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.model.ImpactSeverity;
import com.airline.impactanalysis.domain.port.in.GetImpactReportUseCase;
import com.airline.impactanalysis.domain.port.out.ImpactReportRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GetImpactReportUseCaseImpl implements GetImpactReportUseCase {

    private final ImpactReportRepositoryPort reportRepository;

    public GetImpactReportUseCaseImpl(ImpactReportRepositoryPort reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Optional<ImpactReport> findById(UUID id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<ImpactReport> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<ImpactReport> findBySeverity(ImpactSeverity severity) {
        return reportRepository.findBySeverity(severity);
    }

    @Override
    public List<ImpactReport> findByDisruptionId(UUID disruptionId) {
        return reportRepository.findByDisruptionId(disruptionId);
    }
}
