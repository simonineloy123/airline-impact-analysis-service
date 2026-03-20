package com.airline.impactanalysis.domain.exception;

import java.util.UUID;

public class ImpactReportNotFoundException extends RuntimeException {
    public ImpactReportNotFoundException(UUID id) {
        super("Reporte de impacto no encontrado con id: " + id);
    }
}
