import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Socket socket;

    public Client(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
    }

    public void start() {
        try (Scanner in = new Scanner(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (in.hasNextLine()) {
                System.out.println("Received from server: " + in.nextLine());
                if (System.in.available() > 0) {
                    String input = new Scanner(System.in).nextLine();
                    System.out.println("Sending to server: " + input);
                    out.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
