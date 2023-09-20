package org.euncat.service;

import org.euncat.dto.BufferSet;
import org.euncat.socket.HttpParser;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Reactor {

    private final BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1000);
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 32, 10, TimeUnit.SECONDS, blockingQueue);
    public final static ThreadLocal<BufferSet> threadLocal = new ThreadLocal<>();
    private final HttpParser httpParser = new HttpParser();
    private final FileService fileService = new FileService();

    public void run(Socket socket) {
        HttpWorker worker = new HttpWorker(httpParser, socket, fileService);
        threadPoolExecutor.execute(worker);
    }

}
