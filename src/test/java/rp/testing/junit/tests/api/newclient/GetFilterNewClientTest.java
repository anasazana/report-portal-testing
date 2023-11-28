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
import rp.testing.api.model.filter.UserFilterBatch;

import static rp.testing.utils.TestConfiguration.projectName;

@IncludeEngines("junit-jupiter")
public class GetFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @DisplayName("GET all filters request should return all existing filters")
    public void getAllFiltersTest() {
        UserFilterBatch getAllResponse = client.getFilters()
                .validateStatusCode(HttpStatus.SC_OK)
                .getBodyAsObject(UserFilterBatch.class);
        Assertions.assertFalse(getAllResponse.getContent().isEmpty());
    }

    @Test
    @DisplayName("Get an existing filter, 200 status code should be returned")
    public void getExistingFilterTest() {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        EntryCreatedRS responseBody = client.createFilter(testFilter)
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class);
        testFilter.setId(responseBody.getId());

        client.getFilterById(testFilter.getId())
                .validateStatusCode(HttpStatus.SC_OK)
                .validateResponseBodyEquals(testFilter);
    }

    @Test
    @DisplayName("Get a non-existent filter, 404 status code should be returned")
    public void getNonExistentFilterTest() {
        String nonExistentId = "99999";
        OperationCompletionRS response = client.getFilterById(nonExistentId)
                .validateStatusCode(HttpStatus.SC_NOT_FOUND)
                .getBodyAsObject(OperationCompletionRS.class);

        Assertions.assertEquals(response.getErrorCode(), 40421);
        Assertions.assertEquals(response.getMessage(), String.format(
                "User filter with ID '%s' not found on project '%s'. Did you use correct User Filter ID?",
                nonExistentId, projectName())
        );
    }

}
