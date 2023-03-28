package trafficcontrol.controller.port.in.adapter.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import trafficcontrol.controller.port.in.adapter.HandleMessageJavaAdapter;

public class HandleMessageSocketServer {
    public static final int PORT = 8000;
    private final HandleMessageJavaAdapter HandleMessageJavaAdapter;

    public HandleMessageSocketServer(HandleMessageJavaAdapter HandleMessageJavaAdapter) {
        this.HandleMessageJavaAdapter = HandleMessageJavaAdapter;
    }

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(PORT);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(HandleMessageJavaAdapter, clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;
        private final HandleMessageJavaAdapter HandleMessageJavaAdapter;

        private ClientTask(HandleMessageJavaAdapter HandleMessageJavaAdapter, Socket clientSocket) {
            this.HandleMessageJavaAdapter = HandleMessageJavaAdapter;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    HandleMessageJavaAdapter.handleMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}