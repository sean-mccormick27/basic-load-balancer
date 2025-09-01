import java.io.IOException;
import java.net.Socket;
/**
 * A class used to handle single client connection in its own thread.
 * This class is responsible reads messages sent by a client over a socket
 * and will then send them to a specified back-end service.
 * The connection will be closed once the client disconnects or if an
 * error occurs during communication.
 */
public class ClientConnectionHandler implements Runnable {

    /** The socket communicating with the connected client.*/
    private final Socket clientSocket;

    /** The host name of the backend service where messages from the client will be sent.*/
    private final String backendServiceHostName;

    /** The port number of the backend service where messages from the client will be sent.*/
    private final int backendServicePortNumber;

    /**
     * Creates a new handler for a client connection.
     *
     * @param clientSocket              the socket representing the client's TCP connection
     * @param backendServiceHostName    the host name of the backend service where client messages will be sent to.
     * @param backendServicePortNumber  the port number of the backend service where client messages will be sent to.
     */
    public ClientConnectionHandler(Socket clientSocket, String backendServiceHostName, int backendServicePortNumber) {
        this.clientSocket = clientSocket;
        this.backendServiceHostName = backendServiceHostName;
        this.backendServicePortNumber = backendServicePortNumber;
    }

    /**
     * Processes incoming messages from the client, sending them to the
     * backend service with the given host name and port number.
     */
    public void run() {
        Socket backendServiceSocket = null;
        String clientAddress = clientSocket.getInetAddress().toString();
        try {
            backendServiceSocket = new Socket(backendServiceHostName, backendServicePortNumber);
            System.out.println("Sending client [" + clientAddress + "] message to backend service [" + backendServiceHostName + ":" + backendServicePortNumber + "]");

            Thread clientToBackendPipe = new Thread(new SocketPipe(clientSocket, backendServiceSocket));
            clientToBackendPipe.start();
            clientToBackendPipe.join();
        } catch (Exception e) {
            System.err.println("Issue with connecting to backend service [" + backendServiceHostName + ":" + backendServicePortNumber + "] - " + e.getMessage());
        } finally {
            try {
                if (backendServiceSocket != null) backendServiceSocket.close();
            } catch (IOException e) {
                System.err.println("Issue with closing backend service socket: " + e.getMessage());
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
}

