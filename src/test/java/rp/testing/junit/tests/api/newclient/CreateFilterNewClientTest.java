package rp.testing.junit.tests.api.newclient;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import rp.testing.api.datagenerator.FilterGenerator;
import rp.testing.api.model.filter.EntryCreatedRS;
import rp.testing.api.model.filter.UpdateUserFilterRQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@IncludeEngines("junit-jupiter")
public class CreateFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @DisplayName("Create a filter with all required fields present, 200 status code should be returned")
    public void createNewFilterTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        EntryCreatedRS responseBody = client.createFilter(testFilter)
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().body().as(EntryCreatedRS.class);
        Assertions.assertNotNull(responseBody.getId());
        testFilter.setId(responseBody.getId());

        UpdateUserFilterRQ actualFilter = client.getFilterById(testFilter.getId())
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().body().as(UpdateUserFilterRQ.class);
        assertThat(actualFilter).usingRecursiveComparison().isEqualTo(testFilter);
    }

    @Test
    @DisplayName("Create a filter with ID, ID from the request should be ignored, filter should be created with automatically generated ID")
    public void createFilterProvidedIDShouldBeIgnoredTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        testFilter.setId("999");
        EntryCreatedRS responseBody = client.createFilter(testFilter)
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().body().as(EntryCreatedRS.class);
        String actualFilterId = responseBody.getId();
        Assertions.assertNotNull(actualFilterId);
        Assertions.assertNotEquals(actualFilterId, testFilter.getId());

        client.getFilterById(testFilter.getId())
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

        testFilter.setId(actualFilterId);
        UpdateUserFilterRQ actualFilter = client.getFilterById(actualFilterId)
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().body().as(UpdateUserFilterRQ.class);
        assertThat(actualFilter).usingRecursiveComparison().isEqualTo(testFilter);
    }

    @Test
    @DisplayName("Create a filter with missing required fields, 400 status code should be returned")
    public void createFilterMissingRequiredFieldTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        testFilter.setName(null);
        client.createFilter(testFilter)
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errorCode", equalTo(4001))
                .body("message", equalTo("Incorrect Request. [Field 'name' should not be null.] "));
    }

}
