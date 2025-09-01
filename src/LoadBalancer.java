import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A class representing a load balancer listens for incoming TCP messages
 * on a specified port number, and prints any received messages' contents.
 */
public class LoadBalancer {

    /** The port number that the TCP server is listening for incoming messages from. */
    private final int listenPort;

    /**
     * Constructs a load balancer to listen for TCP messages on a given port number.
     *
     * @param listenPort the port number.
     */
    public LoadBalancer(int listenPort) {
        this.listenPort = listenPort;
    }

    /**
     * Starts the load balancer.
     * The load balancer will listen for TCP messages on the given port number, printing the
     * content of these messages whenever they are received.
     *
     * @throws IOException if an error occurs when attempting to listen for, accept or
     * process any incoming TCP messages.
     */
    public void start() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(listenPort);
            System.out.println("Load balancer listening for requests on port: [" + listenPort + "]");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().toString();
                System.out.println("Client connected: [" + clientAddress + "]");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Message from client [" + clientAddress + "]: " + line);
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error occurred while attempting open server socket: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error occurred while attempting to close server socket: " + e.getMessage());
            }
        }
    }
}

