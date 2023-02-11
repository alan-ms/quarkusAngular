package br.com.alanms.service;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@RegisterRestClient(configKey = "world-bank-country")
public interface WorldBankService {

    @GET
    @ClientQueryParam(name = "format", value="json")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Object> getCountries(@QueryParam("page") int page);

    @GET
    @ClientQueryParam(name = "format", value="json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{countryId}/indicator/SI.POV.DDAY")
    Uni<Object> getIndicator(@PathParam("countryId") String countryId, @QueryParam("page") int page);

}
