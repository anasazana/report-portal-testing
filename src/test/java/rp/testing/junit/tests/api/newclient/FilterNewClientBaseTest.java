package rp.testing.junit.tests.api.newclient;

import org.junit.jupiter.api.BeforeAll;
import rp.testing.api.client.newclient.ReportPortalFiltersNewClient;

public class FilterNewClientBaseTest {

    protected static ReportPortalFiltersNewClient client;

    @BeforeAll
    public static void initClient() {
        client = new ReportPortalFiltersNewClient();
    }

}
