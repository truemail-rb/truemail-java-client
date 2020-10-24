package org.truemail;

import org.json.JSONObject;
import org.truemail.client.Configuration;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static String USER_AGENT = "Truemail Crystal client";
    public static String MIME_TYPE = "application/json";

    private Configuration configuration;

    public Client(Configuration configuration) {
      this.configuration = configuration;
    }

    public String validate (String email) {
        try {
            URL url = new URL(uri(email));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            headers().forEach(con::setRequestProperty);

            if ( con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return con.getResponseMessage();
            } else {
                JSONObject error = new JSONObject();
                JSONObject response = new JSONObject();
                response.put("responseCode", con.getResponseCode());
                response.put("responseBody", con.getResponseMessage());
                error.put("truemail_client_error:", response);
                return error.toString();
            }
        } catch (IOException e) {
            JSONObject error = new JSONObject();
            error.put("truemail_client_error", e.toString());
            return error.toString();
        }
    }

    private String uri(String email) {
        StringBuilder result = new StringBuilder();
        String protocol = this.configuration.isSecureConnection() ? "https" : "http";
        result.append(protocol);
        result.append("://");
        result.append(this.configuration.getHost());
        result.append(":");
        result.append(this.configuration.getPort());
        result.append("?email=");
        result.append(email);

        return result.toString();
    }

    private Map<String, String> headers() {
        return new HashMap<String, String>() {
            {
                put("User-Agent", USER_AGENT);
                put("Accept", MIME_TYPE);
                put("Content-Type", MIME_TYPE);
                put("Authorization", configuration.getToken());
            }
        };
    }
}
