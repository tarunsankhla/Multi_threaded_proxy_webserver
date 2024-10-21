package SingleThreaded;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public void run() throws IOException{
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        while(true){
            try{
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted from client " + socket.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(socket.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                toClient.println("Hello, from the server!");
                toClient.close();
                fromClient.close();
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args){
        System.out.println("Server is running...");
        Server server = new Server();
        try{
            server.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Server is stopped.");
    }
}
