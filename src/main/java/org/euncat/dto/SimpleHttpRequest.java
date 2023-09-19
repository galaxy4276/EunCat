package org.euncat.dto;

public class SimpleHttpRequest {
    private String method;
    private String httpVersion;
    private String host;

    private SimpleHttpRequest() {}

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHost(String host) {
        this.host = host;
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

    public static class Builder {
        private SimpleHttpRequest target;

        public Builder addMethod(String method) {
            target.setMethod(method);
            return this;
        }

        public Builder addHttpVersion(String version) {
            target.setHttpVersion(version);
            return this;
        }

        public Builder addHost(String host) {
            target.setHost(host);
            return this;
        }

        public SimpleHttpRequest build() {
            if (
                target.getHost() == null ||
                target.getMethod() == null ||
                target.getHttpVersion() == null
            ) {
                throw new IllegalArgumentException();
            }
            return target;
        }

    }

    public static Builder builder() {
        return new Builder();
    }

}
