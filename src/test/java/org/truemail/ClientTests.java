package org.truemail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.truemail.client.Configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Client.class})
public class ClientTests {
    @Test
    public void validateReturnsSuccessResponseWhenValidEmail() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email@com";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(huc.getResponseMessage()).thenReturn("Be Nice");

        Configuration config = new Configuration(true, "test.host", 12345,  "2000");
        Client client = new Client(config);
        String response = client.validate("test.email@com");

        assertEquals("Be Nice", response);
    }

    @Test
    public void validateReturnsErrorResponseWhenHttpConnectionFailed() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenThrow(new IOException());

        Configuration config = new Configuration(true, "test.host", 12345,  "2000");
        Client client = new Client(config);
        String response = client.validate("test.email");

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
        when(huc.getResponseMessage()).thenReturn("Something went wrong");

        Configuration config = new Configuration(true, "test.host", 12345,  "2000");
        Client client = new Client(config);
        String response = client.validate("test.email");

        assertEquals(
                "{\"truemail_client_error:\":{\"responseBody\":\"Something went wrong\",\"responseCode\":500}}",
                response
        );
    }
}
