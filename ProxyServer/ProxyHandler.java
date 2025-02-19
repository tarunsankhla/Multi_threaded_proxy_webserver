package ProxyServer;
import java.io.*;
import java.net.*;
public class ProxyHandler implements Runnable {
    private final Socket clientSocket;

    public ProxyHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = clientReader.readLine();
            if (requestLine == null) return;

            URL url = new URL(requestLine.split(" ")[1]); // Extract target URL
            String host = url.getHost();
            int port = (url.getPort() == -1) ? 80 : url.getPort(); // Default to port 80 if not specified

            // Connect to target server
            try (Socket serverSocket = new Socket(host, port);
                 BufferedWriter serverWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
                 BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

                // Forward request
                serverWriter.write(requestLine + "\r\n");
                serverWriter.flush();

                // Forward response back to client
                String responseLine;
                while ((responseLine = serverReader.readLine()) != null) {
                    clientWriter.write(responseLine + "\r\n");
                    clientWriter.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Forward Proxy Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
