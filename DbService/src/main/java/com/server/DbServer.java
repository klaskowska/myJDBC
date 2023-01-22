package com.server;

import java.io.*;
import java.net.*;

public class DbServer {
    private int port;
    public DbServer(int _port) {
        port = _port;
    }

    public void runServer() {
        Repository repository = new Repository();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("mimDB is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String request = reader.readLine();
                while (request == null)
                    request = reader.readLine();

                System.out.println("Request: " + request);

                String executedRequest = repository.executeRequest(request);

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(executedRequest);

                System.out.println("Executed request: " + executedRequest);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}