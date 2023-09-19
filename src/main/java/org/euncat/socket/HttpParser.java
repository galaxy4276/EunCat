package org.euncat.socket;

import org.euncat.dto.SimpleHttpRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class HttpParser {
    private final List<String> methods = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD");

    public HttpRequest getHttpRequest(InputStream inputStream, BufferedReader br) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        SimpleHttpRequest requestDetails = getRequestDetails(br.readLine());
        HttpRequest httpRequest = new HttpRequest(requestDetails);

        System.out.println("================= Headers =====================");
        String s = br.readLine();
        while (Objects.nonNull(s) && !s.isEmpty()) {
            System.out.println(s);
            String[] tokens = s.split(":");
            if (tokens.length > 1) {
                httpRequest.setHeader(tokens[0], tokens[1]);
            }
            s = br.readLine();
        }
        System.out.println("================= Headers Done =====================");

        return httpRequest;
    }

    private SimpleHttpRequest getRequestDetails(String line) {
        if (line == null) return null;
        String[] tokens = line.split("\s");
        String method = null;
        if (methods.contains(tokens[0])) {
            method = tokens[0];
        }

        return SimpleHttpRequest.builder()
                .addMethod(method)
                .addRequestUrl(tokens[1])
                .addHttpVersion(tokens[2])
                .addHost("")
                .build();
    }

    private String getUri(String line) {
        if (line == null) return null;
        return "http://" + line.split("\s")[1];
    }
}
