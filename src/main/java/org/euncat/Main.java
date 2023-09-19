package org.euncat;

import org.euncat.socket.ServerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.HttpRequest;

public class Main {
    public static void main(String[] args) {
        ServerSocket server = new ServerFactory().createServer(8888);

        while (true) {
            try {
                Socket clientRequest = server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientRequest.getInputStream()));
                String line = reader.readLine();
                String ret = line;
                while (line != null) {
                    line = reader.readLine();
                    ret += line + "\n";
                }
                System.out.println(ret);
                clientRequest.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
