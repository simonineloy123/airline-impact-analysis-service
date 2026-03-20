package com.airline.impactanalysis.domain.port.out;

import com.airline.impactanalysis.domain.model.ImpactReport;

public interface ImpactEventPublisherPort {
    void publish(ImpactReport report);
}
