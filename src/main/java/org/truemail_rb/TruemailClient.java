package org.truemail_rb;

import org.json.JSONObject;
import org.truemail_rb.client.TruemailConfiguration;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TruemailClient {
    public static String USER_AGENT = "Truemail Java client";
    public static String MIME_TYPE = "application/json";

    private TruemailConfiguration truemailConfiguration;

    public TruemailClient(TruemailConfiguration truemailConfiguration) {
      this.truemailConfiguration = truemailConfiguration;
    }

    public String validate (String email) {
        try {
            URL url = new URL(uri(email));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            headers().forEach(con::setRequestProperty);

            if ( con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return getResponseBody(con.getInputStream());
            } else {
                JSONObject error = new JSONObject();
                JSONObject response = new JSONObject();
                response.put("responseCode", con.getResponseCode());
                response.put("responseBody", getResponseBody(con.getErrorStream()));
                error.put("truemail_client_error:", response);
                return error.toString();
            }
        } catch (IOException e) {
            JSONObject error = new JSONObject();
            error.put("truemail_client_error", e.toString());
            return error.toString();
        }
    }

    private String getResponseBody(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }

    private String uri(String email) {
        StringBuilder result = new StringBuilder();
        String protocol = this.truemailConfiguration.isSecureConnection() ? "https" : "http";
        result.append(protocol);
        result.append("://");
        result.append(this.truemailConfiguration.getHost());
        result.append(":");
        result.append(this.truemailConfiguration.getPort());
        result.append("?email=");
        result.append(email);

        return result.toString();
    }

    private Map<String, String> headers() {
        HashMap<String, String> headers =  new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        headers.put("Accept", MIME_TYPE);
        headers.put("Content-Type", MIME_TYPE);
        headers.put("Authorization", this.truemailConfiguration.getToken());
        return headers;
    }
}
