package org.euncat.service;

import org.euncat.dto.BufferSet;
import org.euncat.socket.HttpParser;
import org.euncat.socket.HttpRequest;
import org.euncat.socket.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HttpWorker implements Runnable {
    private final HttpParser httpParser;
    private final Socket socket;
    private final BufferSet bufferSet;
    private final FileService fileService;

    public HttpWorker(HttpParser httpParser, Socket socket, FileService fileService) {
        this.httpParser = httpParser;
        this.socket = socket;
        this.bufferSet = createBufferSet(socket);
        this.fileService = fileService;
    }

    @Override
    public void run() {
        try {
            Reactor.threadLocal.set(bufferSet);
            HttpRequest httpRequest = httpParser.getHttpRequest(bufferSet.reader());

            route(httpRequest);

            bufferSet.writer().close();
            Reactor.threadLocal.remove();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void route(HttpRequest httpRequest) {
        String url = httpRequest.getRequestUrl();
        HttpResponse responseWriter = new HttpResponse(httpRequest);

        if (url.equals("/")) {
            writeFileListHtml(responseWriter);
        }

        if (url.contains("html")) {
            String target = url.substring(1);
            writeHtml(target, responseWriter);
        }

    }

    private void writeHtml(String reqUrl, HttpResponse responseWriter) {
        File target = fileService.getFiles().stream()
                .filter(file -> reqUrl.equals(file.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("없는 파일입니다."));

        StringBuilder successHeaders = responseWriter.get200SuccessHeaders();
        StringBuilder htmlSb = new StringBuilder(successHeaders.toString());

        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String enter = "\r\n";
            String line = reader.readLine() + enter;

            while (line != null) {
                htmlSb.append(line + enter);
                line = reader.readLine();
            }

            bufferSet.write(htmlSb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeFileListHtml(HttpResponse responseWriter) {
        List<File> files = fileService.getHtmlList();
        StringBuilder response = responseWriter.get200SuccessHeaders();
        response.append("<h1>List of Web Resource</h1>\r\n");

        response.append("<div style=\"display: flex; flex-direction: column; row-gap: 10px;\">\r\n");
        files.forEach(file -> {
            response.append("<a href=\"/" + file.getName() + "\">" + file.getName() + "</a>\r\n");
        });
        response.append("</div>\r\n");

        bufferSet.write(response.toString());

    }

    private BufferSet createBufferSet(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            return BufferSet.builder()
                    .reader(in)
                    .writer(out)
                    .build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
