package org.euncat.dto;

import lombok.Builder;

@Builder
public class SimpleHttpRequest {
    private final String method;
    private final String httpVersion;
    private final String host;
    private final String requestUrl;

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHost() {
        return host;
    }

}
