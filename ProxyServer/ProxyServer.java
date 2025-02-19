package ProxyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer {
    private static final int PORT = 8080;
    private static final boolean FORWARD_PROXY_MODE = true; // Set to false for reverse proxy
    private static final int THREAD_POOL_SIZE = 10; // Adjust based on server capacity

    public static void main(String[] args) {
        // ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Proxy Server running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from: " + clientSocket.getInetAddress());

                if (FORWARD_PROXY_MODE) {
                    threadPool.execute(new ProxyHandler(clientSocket));
                } else {
                    threadPool.execute(new ReverseProxyHandler(clientSocket));
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown(); // Ensure proper shutdown of the thread pool
        }
    }
}
