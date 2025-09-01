import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * A class used to handle single client connection in its own thread.
 * This class is responsible reads messages sent by a client over a socket
 * and will later send them to a specified back-end service.
 * The connection will be closed once the client disconnects or if an
 * error occurs during communication.
 */
public class ClientConnectionHandler implements Runnable {

    /** The socket communicating with the connected client.*/
    private final Socket clientSocket;

    /** The name of the backend service where messages from the client will be sent.*/
    private final String backendServiceName;

    /**
     * Creates a new handler for a client connection.
     *
     * @param clientSocket       the socket representing the client's TCP connection
     * @param backendServiceName the name of the backend service where client messages
     *                           will be sent to.
     */
    public ClientConnectionHandler(Socket clientSocket, String backendServiceName) {
        this.clientSocket = clientSocket;
        this.backendServiceName = backendServiceName;
    }

    /**
     * Processes incoming messages from the client, sending them to the
     * backend service with the given name
     */
    public void run() {
        String clientAddress = clientSocket.getInetAddress().toString();
        System.out.println("Client connected: [" + clientAddress + "]");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Message from client [" + clientAddress + "]: " + line + " sent to service: [" + backendServiceName + "]");
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

