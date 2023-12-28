package rp.testing.junit.tests.api.newclient;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import rp.testing.api.datagenerator.FilterGenerator;
import rp.testing.api.model.OperationCompletionRS;
import rp.testing.api.model.filter.EntryCreatedRS;
import rp.testing.utils.AlmLink;

import static rp.testing.utils.TestConfiguration.projectName;

@IncludeEngines("junit-jupiter")
public class DeleteFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @AlmLink(id = "RP-27")
    @DisplayName("Delete an existing filter, 200 status code should be returned")
    public void deleteExistingFilterTest() {
        EntryCreatedRS responseBody = client.createFilter(FilterGenerator.generateTestFilter())
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class);
        client.getFilterById(responseBody.getId())
                .validateStatusCode(HttpStatus.SC_OK);

        OperationCompletionRS response = client.deleteFilterById(responseBody.getId())
                .validateStatusCode(HttpStatus.SC_OK)
                .getBodyAsObject(OperationCompletionRS.class);
        Assertions.assertEquals(
                String.format("User filter with ID = '%s' successfully deleted.", responseBody.getId()),
                response.getMessage()
        );

        client.getFilterById(responseBody.getId())
                .validateStatusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @AlmLink(id = "RP-28")
    @DisplayName("Delete a non-existent filter, 404 status code should be returned")
    public void getNonExistentFilterTest() {
        String nonExistentId = "99999";
        client.getFilterById(nonExistentId)
                .validateStatusCode(HttpStatus.SC_NOT_FOUND);

        OperationCompletionRS response = client.deleteFilterById(nonExistentId)
                .validateStatusCode(HttpStatus.SC_NOT_FOUND)
                .getBodyAsObject(OperationCompletionRS.class);

        Assertions.assertEquals(40421, response.getErrorCode());
        Assertions.assertEquals(String.format(
                "User filter with ID '%s' not found on project '%s'. Did you use correct User Filter ID?",
                nonExistentId, projectName()),
                response.getMessage()
        );
    }

}
