package org.truemail.client;

public class Configuration {
    public static int DEFAULT_PORT = 9292;

    private boolean secureConnection;
    private String host;
    private int port;
    private String token;

    public Configuration(boolean secureConnection, String host, String token, int port) {
        this.secureConnection = secureConnection;
        this.host = host;
        this.port = port;
        this.token = token;
    }

    public Configuration(boolean secureConnection, String host, String token) {
        this.host = host;
        this.secureConnection = secureConnection;
        this.token = token;
        this.port = DEFAULT_PORT;
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
}
