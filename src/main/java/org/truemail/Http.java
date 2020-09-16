package org.truemail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Http {
    public static String USER_AGENT = "Truemail Crystal client";
    public static String MIME_TYPE = "application/json";

    private boolean secureConnection;
    private String host;
    private int port;
    private String token;

    public Http(boolean secureConnection, String host, int port, String token) {
        this.secureConnection = secureConnection;
        this.host = host;
        this.port = port;
        this.token = token;
    }

    public boolean isSecureConnection() {
        return secureConnection;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getToken() {
        return token;
    }

    public void run (String email) throws IOException {
        URL url = new URL(uri(email));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        headers().forEach(con::setRequestProperty);

        con.getResponseCode();
        con.getResponseMessage();
    }

    private String uri(String email) {
        StringBuilder result = new StringBuilder();
        String protocol = this.secureConnection ? "https" : "http";
        result.append(protocol);
        result.append("://");
        result.append(this.host);
        result.append(":");
        result.append(port);
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
                put("Authorization", token);
            }
        };
    }
}
