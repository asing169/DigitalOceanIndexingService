package com.digitalocean.server;

import com.digitalocean.models.Response;
import com.digitalocean.models.Status;
import com.digitalocean.service.ProcessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MultiThreadedServerTest {

    HashMap<Integer, Socket> socketHashMap = new HashMap<>();
    MultiThreadedServer server;

    @BeforeEach
    void setUp() {
        server = new MultiThreadedServer(8080);
        new Thread(server).start();
    }

    @AfterEach
    void tearDown() throws Exception {
        for (Map.Entry<Integer, Socket> entry : socketHashMap.entrySet()) {
            entry.getValue().close();
        }
        socketHashMap.clear();
        server.stop();
    }


    @Test
    void run() throws Exception {


        for (int i = 0; i < 10; i++) {
            Socket socket = new Socket("localhost", 8080);
            assertTrue(socket.isConnected());
            socketHashMap.put(i, socket);

        }

        assertEquals(10, socketHashMap.size());

    }

}