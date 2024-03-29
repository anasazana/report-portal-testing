package rp.testing.junit.tests.api.newclient;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import rp.testing.api.datagenerator.FilterGenerator;
import rp.testing.api.model.OperationCompletionRS;
import rp.testing.api.model.filter.EntryCreatedRS;
import rp.testing.api.model.filter.UpdateUserFilterRQ;
import rp.testing.listeners.AlmLink;

@IncludeEngines("junit-jupiter")
public class CreateFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @AlmLink(id = "RP-24")
    @DisplayName("Create a filter with all required fields present, 200 status code should be returned")
    public void createNewFilterTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        EntryCreatedRS responseBody = client.createFilter(testFilter)
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class);
        Assertions.assertNotNull(responseBody.getId());
        testFilter.setId(responseBody.getId());

        client.getFilterById(testFilter.getId())
                .validateStatusCode(HttpStatus.SC_OK)
                .validateResponseBodyEquals(testFilter);
    }

    @Test
    @AlmLink(id = "RP-25")
    @DisplayName("Create a filter with ID, ID from the request should be ignored, filter should be created with automatically generated ID")
    public void createFilterProvidedIDShouldBeIgnoredTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        testFilter.setId("999");
        client.deleteFilterById(testFilter.getId());
        EntryCreatedRS responseBody = client.createFilter(testFilter)
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class);
        String actualFilterId = responseBody.getId();
        Assertions.assertNotNull(actualFilterId);
        Assertions.assertNotEquals(testFilter.getId(), actualFilterId);

        client.getFilterById(testFilter.getId())
                .validateStatusCode(HttpStatus.SC_NOT_FOUND);

        testFilter.setId(actualFilterId);
        client.getFilterById(actualFilterId)
                .validateStatusCode(HttpStatus.SC_OK)
                .validateResponseBodyEquals(testFilter);
    }

    @Test
    @AlmLink(id = "RP-26")
    @DisplayName("Create a filter with missing required fields, 400 status code should be returned")
    public void createFilterMissingRequiredFieldTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        testFilter.setName(null);
        OperationCompletionRS response = client.createFilter(testFilter)
                .validateStatusCode(HttpStatus.SC_BAD_REQUEST)
                .getBodyAsObject(OperationCompletionRS.class);

        Assertions.assertEquals(4001, response.getErrorCode());
        Assertions.assertEquals("Incorrect Request. [Field 'name' should not be null.] ", response.getMessage());
    }

}
