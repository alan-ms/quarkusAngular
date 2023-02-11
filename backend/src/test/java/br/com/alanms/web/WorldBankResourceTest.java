package br.com.alanms.web;

import br.com.alanms.service.WorldBankService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@DisplayName("Workd Bank Api Tests")
public class WorldBankResourceTest {

    static final String DEFAULT_COUNTRY_ID_PAGE_1 = "BRA";

    static final String DEFAULT_COUNTRY_NAME_PAGE_1 = "BRAZIL";

    static final String DEFAULT_COUNTRY_ID_PAGE_2 = "CHN";

    static final String DEFAULT_COUNTRY_NAME_PAGE_2 = "CHINA";

    static final float DEFAULT_INDICATOR_PAGE_1 = 1.0F;

    static final float DEFAULT_INDICATOR_PAGE_2 = 2.0F;

    static final String DEFAULT_INDICATOR_DATE_PAGE_1 = "2022";

    static final String DEFAULT_INDICATOR_DATE_PAGE_2 = "2023";

    @InjectMock
    @RestClient
    WorldBankService worldBankService;

    @Test
    public void testGetIndicatorWithSuccess() {
        Mockito.when(worldBankService.getIndicator(DEFAULT_COUNTRY_ID_PAGE_1, 1))
                .thenReturn(Uni.createFrom().item(mockIndicatorPage(1,2, DEFAULT_INDICATOR_PAGE_1, DEFAULT_INDICATOR_DATE_PAGE_1)));
        Mockito.when(worldBankService.getIndicator(DEFAULT_COUNTRY_ID_PAGE_1, 2))
                .thenReturn(Uni.createFrom().item(mockIndicatorPage(2,2, DEFAULT_INDICATOR_PAGE_2, DEFAULT_INDICATOR_DATE_PAGE_2)));

        given()
                .when()
                .pathParams("countryId", DEFAULT_COUNTRY_ID_PAGE_1)
                .get("/api/countries/{countryId}/indicators")
                .then()
                .statusCode(200)
                .body("size()", is(2),
                        "[0].value", is(DEFAULT_INDICATOR_PAGE_1),
                        "[0].date", is(DEFAULT_INDICATOR_DATE_PAGE_1),
                        "[1].value", is(DEFAULT_INDICATOR_PAGE_2),
                        "[1].date", is(DEFAULT_INDICATOR_DATE_PAGE_2));
    }

    @Test
    public void testGetIndicatorWithError() {
        Object mock = mockIndicatorPage(1,2, DEFAULT_INDICATOR_PAGE_1, DEFAULT_INDICATOR_DATE_PAGE_1);
        ((ArrayList<?>) mock).remove(1);
        Mockito.when(worldBankService.getIndicator(DEFAULT_COUNTRY_ID_PAGE_1, 1))
                .thenReturn(Uni.createFrom().item(mock));

        given()
                .when()
                .pathParams("countryId", DEFAULT_COUNTRY_ID_PAGE_1)
                .get("/api/countries/{countryId}/indicators")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetCountriesWithSuccess() {
        Mockito.when(worldBankService.getCountries(1))
                .thenReturn(Uni.createFrom().item(mockCountryPage(1, 2,DEFAULT_COUNTRY_ID_PAGE_1, DEFAULT_COUNTRY_NAME_PAGE_1)));
        Mockito.when(worldBankService.getCountries(2))
                .thenReturn(Uni.createFrom().item(mockCountryPage(2, 2, DEFAULT_COUNTRY_ID_PAGE_2, DEFAULT_COUNTRY_NAME_PAGE_2)));

        given()
                .when().get("/api/countries")
                .then()
                .statusCode(200)
                .body("size()", is(2),
                        "[0].id", is(DEFAULT_COUNTRY_ID_PAGE_1),
                        "[0].name", is(DEFAULT_COUNTRY_NAME_PAGE_1),
                        "[1].id", is(DEFAULT_COUNTRY_ID_PAGE_2),
                        "[1].name", is(DEFAULT_COUNTRY_NAME_PAGE_2)
                );
    }


    @Test
    public void testGetCountriesWithErrorObjectResponse() {
        Object mock = mockCountryPage(1, 2,DEFAULT_COUNTRY_ID_PAGE_1, DEFAULT_COUNTRY_NAME_PAGE_1);
        ((ArrayList<?>) mock).remove(1);
        Mockito.when(worldBankService.getCountries(1)).thenReturn(Uni.createFrom().item(mock));

        given()
                .when().get("/api/countries")
                .then()
                .statusCode(400);
    }

    public Object mockCountryPage(Integer page, Integer pages, String countryId, String countryName) {
        LinkedHashMap<String, Object> pageMock = new LinkedHashMap<>();
        pageMock.put("page", page);
        pageMock.put("pages", pages);

        LinkedHashMap<String, String> countryMock = new LinkedHashMap<>();
        countryMock.put("id", countryId);
        countryMock.put("name", countryName);

        ArrayList<LinkedHashMap<String, String>> countriesMock = new ArrayList<>();
        countriesMock.add(countryMock);

        ArrayList<Object> responseMock = new ArrayList<>();
        responseMock.add(pageMock);
        responseMock.add(countriesMock);

        return responseMock;
    }

    public Object mockIndicatorPage(Integer page, Integer pages, Float indicatorValue, String indicatorDate) {
        LinkedHashMap<String, Object> pageMock = new LinkedHashMap<>();
        pageMock.put("page", page);
        pageMock.put("pages", pages);

        LinkedHashMap<String, Object> indicatorMock = new LinkedHashMap<>();
        indicatorMock.put("value", indicatorValue);
        indicatorMock.put("date", indicatorDate);

        ArrayList<LinkedHashMap<String, Object>> indicatorsMock = new ArrayList<>();
        indicatorsMock.add(indicatorMock);

        ArrayList<Object> responseMock = new ArrayList<>();
        responseMock.add(pageMock);
        responseMock.add(indicatorsMock);

        return responseMock;
    }
}
