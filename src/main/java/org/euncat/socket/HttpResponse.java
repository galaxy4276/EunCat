package org.euncat.socket;

import lombok.RequiredArgsConstructor;
import org.euncat.dto.BufferSet;
import org.euncat.service.Reactor;

@RequiredArgsConstructor
public class HttpResponse {
    private final HttpRequest httpRequest;
    private final BufferSet bufferSet = Reactor.threadLocal.get();
    private final StringBuilder sb = new StringBuilder();

    public String publish() {
        String ret = sb.toString();
        bufferSet.write(ret);
        return ret;
    }

    public StringBuilder get200SuccessHeaders() {
        write200Ok();
        writeServer();
        return sb;
    }

    public void write200Ok() {
        sb.append("HTTP/1.1 200 OK\r\n");
    }

    public void writeHelloWorld() {
        sb.append("<h1>Hello World v3</h1>");
    }
    public void writeServer() {
        sb.append("Server: EunCat/0.0.1\r\n\r\n");
    }
}
