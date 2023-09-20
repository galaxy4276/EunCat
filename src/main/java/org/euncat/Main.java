package org.euncat;

import org.euncat.service.Reactor;
import org.euncat.socket.HttpParser;
import org.euncat.socket.HttpRequest;
import org.euncat.socket.ServerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ServerSocket server = new ServerFactory().createServer(8888);
        Reactor reactor = new Reactor();
        System.out.println("""
                ,------.,--. ,--.,--.  ,--. ,-----.  ,---. ,--------.\s
                |  .---'|  | |  ||  ,'.|  |'  .--./ /  O  \\'--.  .--'\s
                |  `--, |  | |  ||  |' '  ||  |    |  .-.  |  |  |   \s
                |  `---.'  '-'  '|  | `   |'  '--'\\|  | |  |  |  |   \s
                `------' `-----' `--'  `--' `-----'`--' `--'  `--'   \s
                                                                    \s
                """);

        System.out.println("EUNCAT is READY");

        while (true) {
            try {
                Socket client = server.accept();
                reactor.run(client);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
