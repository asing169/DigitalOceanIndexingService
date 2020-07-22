package com.digitalocean.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class WorkerRunnableTest {

    private Socket socketHost;

    private WorkerRunnable workerRunnable;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        new Thread(new ServerRunnable()).start();

    }

    @Test
    void run() throws Exception {

        Socket socketClient = new Socket("localhost", 8080);
        InputStream inputStream = socketClient.getInputStream();
        OutputStream outputStream = socketClient.getOutputStream();
        PrintWriter toServer = new PrintWriter(outputStream, true);
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        toServer.println("INDEX|cloog|");

        String line = input.readLine();
        assertEquals("OK", line);

        inputStream.close();
        outputStream.close();
        socketClient.close();


    }

    class ServerRunnable implements Runnable {
        ServerSocket server;


        public void run() {
            try {
                this.server = new ServerSocket(8080);

                socketHost = server.accept();
                workerRunnable = new WorkerRunnable(socketHost, "MultiThreaded server");
                workerRunnable.run();
                server.close();

            } catch (Exception e) {
                fail("IOException in Server");
            }
        }
    }
}