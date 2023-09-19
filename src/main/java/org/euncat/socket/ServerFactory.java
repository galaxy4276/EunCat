package org.euncat.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFactory {


    public ServerSocket createServer(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("error when create socket server");
            ex.printStackTrace();
            return null;
        }
    }

}
