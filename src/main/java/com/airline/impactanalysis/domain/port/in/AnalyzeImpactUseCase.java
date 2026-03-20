package com.airline.impactanalysis.domain.port.in;

import com.airline.impactanalysis.domain.model.DisruptionEventPayload;
import com.airline.impactanalysis.domain.model.ImpactReport;

public interface AnalyzeImpactUseCase {
    ImpactReport execute(DisruptionEventPayload disruption);
}
