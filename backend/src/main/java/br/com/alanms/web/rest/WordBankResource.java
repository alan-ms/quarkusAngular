package br.com.alanms.web.rest;

import br.com.alanms.dto.CountryDTO;
import br.com.alanms.dto.IndicatorDTO;
import br.com.alanms.service.WorldBankService;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/api/countries")
public class WordBankResource {

    @RestClient
    WorldBankService worldBankService;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @CacheResult(cacheName = "country-cache")
//    @Operation(summary = "Busca de países", description = "Busca de ids de países catalogados e seu nome internacional")
//    @APIResponse(responseCode = "200", description = "OK")
//    @APIResponse(responseCode = "400", description = "Falha na busca dos países")
//    @Tag(name = "países")
//    public Response getCountries() {
//        int page = 1;
//        Integer pages;
//
//        Set<CountryDTO> countries = new HashSet<>();
//        do {
//            Object result = worldBankService.getCountries(page);
//            if (((ArrayList<?>) result).size() == 0)
//                throw new BadRequestException("Failed to get countries");
//
//            pages = (Integer) ((LinkedHashMap<?, ?>) ((ArrayList<?>) result).get(0)).get("pages");
//
//            ((ArrayList<?>) ((ArrayList<?>) result).get(1))
//                    .forEach(countryHashMap -> countries.add(createCountry((LinkedHashMap<String, String>) countryHashMap)));
//            page++;
//        } while (page <= pages);
//
//        Set<CountryDTO> countrySorted =  countries.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toSet());
//
//        return Response.ok(countrySorted).build();
//    }

//    public CountryDTO createCountry(LinkedHashMap<String, String> countryHashMap) {
//        CountryDTO country = new CountryDTO();
//        country.setId(countryHashMap.get("id"));
//        country.setName(countryHashMap.get("name"));
//        return country;
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "country-cache")
    @Operation(summary = "Busca de países", description = "Busca de ids de países catalogados e seu nome internacional")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Falha na busca dos países")
    @Tag(name = "países")
    public Multi<CountryDTO> getCountriesTest() {
        return Multi.createBy()
                .repeating()
                .uni(AtomicInteger::new, pageCount -> worldBankService.getCountries(pageCount.incrementAndGet())
                        .onItem().transform(listObject -> {
                            if (listObject != null)
                                return ((ArrayList<?>) listObject).get(1);
                            return new ArrayList<>();
                        }))
                .until(o -> ((ArrayList<?>) o).size() == 0)
                .onFailure()
                .transform(throwable -> new BadRequestException("Failed to get countries"))
                .onItem().disjoint()
                .onItem().transform(countryHashMap -> {
                    CountryDTO country = new CountryDTO();
                    country.setId((String) ((LinkedHashMap<?, ?>) countryHashMap).get("id"));
                    country.setName((String) ((LinkedHashMap<?, ?>) countryHashMap).get("name"));
                    return country;
                });
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{countryId}/indicators")
    @Operation(summary = "Busca de indicador de pobreza", description = "Busca dos indicadores de pobreza por ano pelo ID do país")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Falha na busca do indicador de pobreza")
    @Tag(name = "indicadores")
    public Multi<IndicatorDTO> getIndicatorByCountryId(@PathParam("countryId") @Parameter(example = "BRA") String countryId) {
        return Multi.createBy()
                .repeating()
                .uni(AtomicInteger::new, pageCount -> worldBankService.getIndicator(countryId, pageCount.incrementAndGet())
                        .onItem().transform(listObject -> {
                            if (listObject != null)
                                return ((ArrayList<?>) listObject).get(1);
                            return new ArrayList<>();
                        }))
                .until(o -> ((ArrayList<?>) o).size() == 0)
                .onFailure()
                .transform(throwable -> new BadRequestException("Failed to get countries"))
                .onItem().disjoint()
                 .onItem().transform(indicatorHashMap -> {
                     IndicatorDTO indicator = new IndicatorDTO();
                     if (((LinkedHashMap<?, ?>) indicatorHashMap).get("value") != null) {
                        if (((LinkedHashMap<?, ?>) indicatorHashMap).get("value") instanceof Double) {
                            indicator.setValue((Double) ((LinkedHashMap<?, ?>) indicatorHashMap).get("value"));
                        } else if
                        (((LinkedHashMap<?, ?>) indicatorHashMap).get("value") instanceof Integer value) {
                             indicator.setValue(Double.valueOf(value));
                         } else
                         if (((LinkedHashMap<?, ?>) indicatorHashMap).get("value") instanceof Float value) {
                             indicator.setValue(Double.valueOf(value));
                         }
                     }
                     indicator.setDate(((LinkedHashMap<?, ?>) indicatorHashMap).get("date") != null ? (String) ((LinkedHashMap<?, ?>) indicatorHashMap).get("date") : "");
                     return indicator;
                 });
    }
}