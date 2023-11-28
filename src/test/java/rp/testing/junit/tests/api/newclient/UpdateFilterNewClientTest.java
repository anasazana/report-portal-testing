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

import static rp.testing.utils.TestConfiguration.projectName;

@IncludeEngines("junit-jupiter")
public class UpdateFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @DisplayName("Update existing filter, 200 status code should be returned")
    public void updateFilterTest() {
        UpdateUserFilterRQ testRecord = FilterGenerator.generateTestFilter();
        EntryCreatedRS responseBody = client.createFilter(testRecord)
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class);
        testRecord.setId(responseBody.getId());
        testRecord.setName(testRecord.getName() + "-updated");

        OperationCompletionRS response = client.updateFilterById(testRecord.getId(), testRecord)
                .validateStatusCode(HttpStatus.SC_OK)
                .getBodyAsObject(OperationCompletionRS.class);
        Assertions.assertEquals(response.getMessage(),
                String.format("User filter with ID = '%s' successfully updated.", responseBody.getId())
        );

        client.getFilterById(testRecord.getId())
                .validateStatusCode(HttpStatus.SC_OK)
                .validateResponseBodyEquals(testRecord);
    }

    @Test
    @DisplayName("Update non-existent filter, 404 status code should be returned")
    public void updateNonExistentFilterTest() {
        String nonExistentId = "99999";
        client.getFilterById(nonExistentId)
                .validateStatusCode(HttpStatus.SC_NOT_FOUND);

        OperationCompletionRS response = client.updateFilterById(nonExistentId, FilterGenerator.generateTestFilter())
                .validateStatusCode(HttpStatus.SC_NOT_FOUND)
                .getBodyAsObject(OperationCompletionRS.class);

        Assertions.assertEquals(response.getErrorCode(), 40421);
        Assertions.assertEquals(response.getMessage(), String.format(
                "User filter with ID '%s' not found on project '%s'. Did you use correct User Filter ID?",
                nonExistentId, projectName())
        );
    }

}
