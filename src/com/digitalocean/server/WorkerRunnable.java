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
//                Scanner scanner = new Scanner(inputStream);
//                String input = scanner.nextLine();
            //String resp = processService.processCommand(input).getStatus();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;
            while ((s = input.readLine()) != null) {
                System.out.println(s);
                outputStream.write((processService.processCommand(s).getStatus() + '\n').getBytes());
                outputStream.flush();
                //outputStream.write(("OK" + '\n').getBytes());
            }


            outputStream.close();
            inputStream.close();
            clientSocket.close();

            System.out.println("Request processed: " + "OK" + " at thread " + Thread.currentThread());
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}