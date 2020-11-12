package org.truemail_rb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.truemail_rb.client.TruemailConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TruemailClient.class})
public class TruemailClientTests {
    @Test
    public void validateReturnsSuccessResponseWhenValidEmail() throws Exception {
        URL u = mock(URL.class);
        String url = "http://localhost:9292?email=test.email%40google.com";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        String expectedResponse = "{\"date\":\"2020-10-25 10:08:27 +0200\",\"email\":\"test.email@google.com\"," +
                "\"validation_type\":\"smtp\",\"success\":true,\"errors\":null,\"smtp_debug\":null," +
                "\"configuration\":{\"validation_type_by_domain\":null,\"whitelist_validation\":false," +
                "\"whitelisted_domains\":null,\"blacklisted_domains\":null,\"not_rfc_mx_lookup_flow\":false," +
                "\"smtp_safe_check\":false,\"email_pattern\":\"default gem value\"," +
                "\"smtp_error_body_pattern\":\"default gem value\"}}";

        when(huc.getInputStream()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));

        TruemailConfiguration config = new TruemailConfiguration(false, "localhost",  "my_token");
        TruemailClient truemailClient = new TruemailClient(config);
        String response = truemailClient.validate("test.email@google.com");

        assertEquals(expectedResponse, response);
    }

    @Test
    public void validateReturnsErrorResponseWhenHttpConnectionFailed() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        when(u.openConnection()).thenThrow(new IOException());

        TruemailConfiguration config = new TruemailConfiguration(true, "test.host", "2000", 12345);
        TruemailClient truemailClient = new TruemailClient(config);
        String response = truemailClient.validate("test.email");

        assertEquals("{\"truemail_client_error\":\"java.io.IOException\"}", response);
    }

    @Test
    public void validateReturnsErrorResponseWithDescriptionsWhenInternalError() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);
        when(huc.getErrorStream()).thenReturn(new ByteArrayInputStream("Something went wrong".getBytes()));

        TruemailConfiguration config = new TruemailConfiguration(true, "test.host",  "2000",  12345);
        TruemailClient truemailClient = spy(new TruemailClient(config));

        String response = truemailClient.validate("test.email");

        assertEquals(
                "{\"truemail_client_error:\":{\"responseBody\":\"Something went wrong\",\"responseCode\":500}}",
                response
        );
    }
}
