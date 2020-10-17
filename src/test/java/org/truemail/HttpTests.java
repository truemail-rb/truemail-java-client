package org.truemail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Http.class})
public class HttpTests {
    @Test
    public void testRunCustomEmail() throws Exception {
        URL u = mock(URL.class);
        String url = "https://test.host:12345?email=test.email";
        whenNew(URL.class).withArguments(url).thenReturn(u);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(u.openConnection()).thenReturn(huc);
        when(huc.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(huc.getResponseMessage()).thenReturn("Be Nice");

        Http http = new Http(true, "test.host", 12345,  "2000");
        String response = http.run("test.email");

        assertEquals("Be Nice", response);
    }
}
