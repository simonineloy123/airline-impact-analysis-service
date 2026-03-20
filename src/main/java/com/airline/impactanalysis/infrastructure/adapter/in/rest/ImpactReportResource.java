package com.airline.impactanalysis.infrastructure.adapter.in.rest;

import com.airline.impactanalysis.domain.model.ImpactReport;
import com.airline.impactanalysis.domain.model.ImpactSeverity;
import com.airline.impactanalysis.domain.port.in.GetImpactReportUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api/v1/impact-reports")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImpactReportResource {

    private final GetImpactReportUseCase getImpactReportUseCase;

    public ImpactReportResource(GetImpactReportUseCase getImpactReportUseCase) {
        this.getImpactReportUseCase = getImpactReportUseCase;
    }

    @GET
    public List<ImpactReport> getAll() {
        return getImpactReportUseCase.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        return getImpactReportUseCase.findById(id)
            .map(r -> Response.ok(r).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/severity/{severity}")
    public List<ImpactReport> getBySeverity(@PathParam("severity") String severity) {
        return getImpactReportUseCase.findBySeverity(ImpactSeverity.valueOf(severity));
    }
}
