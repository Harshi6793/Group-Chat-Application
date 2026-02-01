import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Group Chat Server Started...");
        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, clients);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}