import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * A class representing a load balancer listens for incoming TCP messages
 * on a specified port number. Incoming TCP messages are load balanced and sent
 * to a backend service for further processing, as appropriate.
 */
public class LoadBalancer {

    /** The port number that the TCP server is listening for incoming messages from. */
    private final int listenPort;

    /** The pool of backend services that TCP messages are load balanced across. */
    private final BackendServicePool backendServicePool;

    /**
     * Constructs a load balancer to listen for TCP messages on a given port number
     * and load balance said messages across a list pool of backend services, which
     * will be placed in a selection pool.
     *
     * @param listenPort the port number.
     * @param backendServices the list of backend services to be placed in the
     *                        selection pool.
     */
    public LoadBalancer(int listenPort, List backendServices) {
        this.listenPort = listenPort;
        this.backendServicePool = new BackendServicePool(backendServices);
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

                //TODO: Functionality to deal with scenario where no available backend service exists

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BackendService selectedBackendService;

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    selectedBackendService = backendServicePool.selectBackendService();
                    System.out.println("Message from client [" + clientAddress + "]: " + line + " sent to service: [" + selectedBackendService.getServiceName() + "]");
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

