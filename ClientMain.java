import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 8080);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}