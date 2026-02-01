import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Set<ClientHandler> clients;
    private String userName;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your name:");
            userName = in.readLine();
            broadcast("🔔 " + userName + " joined the chat");

            String message;
            while ((message = in.readLine()) != null) {
                broadcast(userName + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clients.remove(this);
            broadcast("🔕 " + userName + " left the chat");
            try {
                socket.close();
            } catch (IOException e) {
                
            }
        }
    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.out.println(message);
        }
    }
}