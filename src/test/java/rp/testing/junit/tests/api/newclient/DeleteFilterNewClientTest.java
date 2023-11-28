package rp.testing.junit.tests.api.newclient;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import rp.testing.api.datagenerator.FilterGenerator;
import rp.testing.api.model.filter.EntryCreatedRS;

import static org.hamcrest.Matchers.equalTo;
import static rp.testing.utils.TestConfiguration.projectName;

@IncludeEngines("junit-jupiter")
public class DeleteFilterNewClientTest extends FilterNewClientBaseTest {

    @Test
    @DisplayName("Delete an existing filter, 200 status code should be returned")
    public void deleteExistingFilterTest() {
        EntryCreatedRS responseBody = client.createFilter(FilterGenerator.generateTestFilter())
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().body().as(EntryCreatedRS.class);
        client.getFilterById(responseBody.getId())
                .assertThat().statusCode(HttpStatus.SC_OK);

        client.deleteFilterById(responseBody.getId())
                .assertThat().statusCode(HttpStatus.SC_OK)
                .body("message", equalTo(String.format(
                        "User filter with ID = '%s' successfully deleted.",
                        responseBody.getId()))
                );

        client.getFilterById(responseBody.getId())
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Delete a non-existent filter, 404 status code should be returned")
    public void getNonExistentFilterTest() {
        String nonExistentId = "99999";
        client.getFilterById(nonExistentId)
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

        client.deleteFilterById(nonExistentId)
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body("errorCode", equalTo(40421))
                .body("message", equalTo(String.format(
                        "User filter with ID '%s' not found on project '%s'. Did you use correct User Filter ID?",
                        nonExistentId, projectName()))
                );
    }

}
