import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private final Socket clientSocket;
    private final BackendService backendService;

    public ClientConnectionHandler(Socket clientSocket, BackendService backendService) {
        this.clientSocket = clientSocket;
        this.backendService = backendService;
    }

    public void run() {
        String clientAddress = clientSocket.getInetAddress().toString();
        System.out.println("Client connected: [" + clientAddress + "]");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Message from client [" + clientAddress + "]: " + line + " sent to service: [" + backendService.getServiceName() + "]");
            }
        } catch (IOException e) {
            System.err.println("Issue with processing TCP message: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Issue with closing client socket: " + e.getMessage());
            }
        }
    }
}

