package rp.testing.junit.tests.api.apachehttpclient;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import rp.testing.api.client.ReportPortalFiltersClient;

import java.io.IOException;

public class FilterBaseTest {

    protected static ReportPortalFiltersClient client;

    @BeforeAll
    public static void initClient() {
        client = new ReportPortalFiltersClient();
    }

    @AfterAll
    public static void closeClient() throws IOException {
        client.closeHttpClient();
    }

}
