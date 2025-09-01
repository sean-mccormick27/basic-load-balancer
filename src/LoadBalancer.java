import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * A class representing a load balancer listens for incoming TCP messages
 * on a specified port number. Incoming TCP messages are load balanced and sent
 * to a backend service for further processing, as appropriate. The load balancer
 * also conducts a health check as per a specified interval, ensuring that no
 * requests can be sent to these if they are offline.
 */
public class LoadBalancer {

    /** The port number that the TCP server is listening for incoming messages from. */
    private final int listenPort;

    /** The pool of backend services that TCP messages are load balanced across. */
    private final BackendServicePool backendServicePool;

    /** The health checker which periodically checks the online status of any
     *  backend services where traffic can be directed towards.
     */
    private final BackendServiceHealthChecker backendServiceHealthChecker;

    /**
     * Constructs a load balancer to listen for TCP messages on a given port number
     * and load balance said messages across a list pool of backend services, which
     * will be placed in a selection pool.
     *
     * @param listenPort the port number.
     * @param backendServices the list of backend services to be placed in the
     *                        selection pool.
     */
    public LoadBalancer(int listenPort, int healthCheckInterval, List backendServices) {
        this.listenPort = listenPort;
        this.backendServicePool = new BackendServicePool(backendServices);
        this.backendServiceHealthChecker = new BackendServiceHealthChecker(backendServicePool, healthCheckInterval);
    }

    /**
     * Starts the load balancer.
     * The load balancer will listen for TCP messages on the given port number, printing the
     * content of these messages whenever they are received. It will also conduct a periodic
     * health check
     *
     * @throws IOException if an error occurs when attempting to listen for, accept or
     * process any incoming TCP messages.
     */
    public void start() {
        ServerSocket serverSocket = null;
        new Thread(backendServiceHealthChecker).start();

        try {
            serverSocket = new ServerSocket(listenPort);
            System.out.println("Load balancer listening for requests on port: [" + listenPort + "]");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BackendService selectedBackendService = backendServicePool.selectBackendService();

                Thread clientConnectionThread = new Thread(new ClientConnectionHandler(clientSocket, selectedBackendService.getHostName(), selectedBackendService.getPortNumber()));
                clientConnectionThread.start();
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

