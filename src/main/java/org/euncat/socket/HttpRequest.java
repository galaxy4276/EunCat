package org.euncat.socket;

import org.euncat.dto.SimpleHttpRequest;

import java.util.HashMap;

public class HttpRequest {
    private final HashMap<String, String> headers = new HashMap<>();
    private final SimpleHttpRequest simpleHttpRequest;

    public HttpRequest(SimpleHttpRequest request) {
        this.simpleHttpRequest = request;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getRequestUrl() {
        return simpleHttpRequest.getRequestUrl();
    }

    public String getMethod() {
        return simpleHttpRequest.getMethod();
    }

}
