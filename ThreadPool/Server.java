package ThreadPool;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize) {
        threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
                toClient.println("Hello, from the server!"+ clientSocket.getInetAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }

    public static void main(String[] args) {
        System.out.println("Server is running...");
        int port = 8010;
        Server server = new Server(10);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                server.threadPool.execute(() -> server.handleClient(acceptedSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }
    }
}
