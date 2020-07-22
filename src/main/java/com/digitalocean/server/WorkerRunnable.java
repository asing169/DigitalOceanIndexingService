package com.digitalocean.server;

import com.digitalocean.service.ProcessService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 *
 */
public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    private ProcessService processService = new ProcessService();

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run() {
        try {

            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;

            while ((s = input.readLine()) != null) {
                System.out.println(s);
                outputStream.write((processService.processCommand(s).getStatus() + '\n').getBytes());
                outputStream.flush();
            }

            outputStream.close();
            inputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}