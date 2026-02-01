import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            try (Socket socket = new Socket("localhost", 5000)) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader userInput = new BufferedReader(
                        new InputStreamReader(System.in));

                // Read server messages
                new Thread(() -> {
                    try {
                        String msg;
                        while ((msg = in.readLine()) != null) {
                            System.out.println(msg);
                        }
                    } catch (IOException e) {}
                }).start();

                // Send user messages
                String input;
                while ((input = userInput.readLine()) != null) {
                    out.println(input);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}