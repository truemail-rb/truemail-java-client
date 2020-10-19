package org.truemail.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Http.class})
public class HttpTests {
    @Test
    public void runReturnsSuccessResponseWhenValidEmail() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.email@com:12345?email=test.email@com";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(huc.getResponseMessage()).thenReturn("Be Nice");

        Http http = new Http(true, "test.email@com", 12345,  "2000");
        String response = http.run("test.email@com");

        assertEquals("Be Nice", response);
    }

    @Test
    public void runReturnsErrorResponseWhenHttpConnectionFailed() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenThrow(new IOException());

        Http http = new Http(true, "test.host", 12345,  "2000");
        String response = http.run("test.email");

        assertEquals("{\"truemail_client_error\":\"java.io.IOException\"}", response);
    }

    @Test
    public void runReturnsErrorResponseWithDescriptionsWhenInternalError() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);
        when(huc.getResponseMessage()).thenReturn("Something went wrong");

        Http http = new Http(true, "test.host", 12345,  "2000");
        String response = http.run("test.email");

        assertEquals(
                "{\"truemail_client_error:\":{\"responseBody\":\"Something went wrong\",\"responseCode\":500}}",
                response
        );
    }
}
