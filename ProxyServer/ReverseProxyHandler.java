package ProxyServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ReverseProxyHandler implements Runnable {
    private final Socket clientSocket;
    private static final List<String> backendServers = Arrays.asList(
        "https://info-6150-web-design-final-project-backend-eta.vercel.app/"
    );
    private static int currentServerIndex = 0;

    public ReverseProxyHandler(Socket socket) {
        this.clientSocket = socket;
    }

    private synchronized String getBackendServer() {
        // Round-robin load balancing
        String server = backendServers.get(currentServerIndex);
        currentServerIndex = (currentServerIndex + 1) % backendServers.size();
        return server;
    }

    @Override
    public void run() {
        try (BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = clientReader.readLine();
            if (requestLine == null) return;

            String backendServer = getBackendServer();
            int backendPort = 8080; // Adjust based on backend setup

            // Connect to backend server
            try (Socket backendSocket = new Socket(backendServer, backendPort);
                 BufferedWriter backendWriter = new BufferedWriter(new OutputStreamWriter(backendSocket.getOutputStream()));
                 BufferedReader backendReader = new BufferedReader(new InputStreamReader(backendSocket.getInputStream()))) {

                // Forward request to backend server
                backendWriter.write(requestLine + "\r\n");
                backendWriter.flush();

                // Forward response back to the client
                String responseLine;
                while ((responseLine = backendReader.readLine()) != null) {
                    clientWriter.write(responseLine + "\r\n");
                    clientWriter.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Reverse Proxy Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
