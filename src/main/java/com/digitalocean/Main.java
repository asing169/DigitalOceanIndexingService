package com.digitalocean;


import com.digitalocean.server.MultiThreadedServer;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MultiThreadedServer server = new MultiThreadedServer(8080);
        new Thread(server).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
