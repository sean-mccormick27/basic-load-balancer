import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/**
 * As per a specified time interval, this class periodically checks the health of
 * backend services i.e. if the backend service is reachable and online. The class
 * continually runs in its own thread, iterating through all of the {@link BackendService}
 * objects in the provided {@link BackendServicePool}, checking if they can be reached via TCP
 * and updating their online status accordingly.
 */
public class BackendServiceHealthChecker extends Thread {

    /** The pool of {@link BackendServicePool} objects whose health will be checked. */
    private final BackendServicePool backendServicePool;

    /** The interval in milliseconds between health checks. */
    private final int healthCheckInterval;

    /**
     * Constructs a new BackendServiceHealthChecker.
     *
     * @param backendServicePool the pool of backend services to check
     * @param healthCheckInterval the interval in milliseconds between health checks
     */
    public BackendServiceHealthChecker(BackendServicePool backendServicePool, int healthCheckInterval) {
        this.backendServicePool = backendServicePool;
        this.healthCheckInterval = healthCheckInterval;
    }


    /**
     * Runs the health checker thread, continuously iterates through all {@link BackendServicePool}
     * objects in the pool, checking if each service is reachable, and updates its online status
     * to reflect this. The thread then sleeps for the specified interval before commencing
     * the next health check.
     * </p>
     */
    public void run() {
        System.out.println("Backend service health checker starting...");
        while (true) {
            List backendServices = backendServicePool.getBackendServices();
            Iterator iterator = backendServices.iterator();
            while (iterator.hasNext()) {
                BackendService backendService = (BackendService) iterator.next();
                boolean isOnline = isBackendServiceOnline(backendService.getHostName(), backendService.getPortNumber());
                backendService.setOnline(isOnline);
                System.out.println("Backend service health: " + backendService);
            }
            try {
                Thread.sleep(healthCheckInterval);
            } catch (InterruptedException e) {
                System.err.println("Backend service health checker interrupted: " + e.getMessage());
                break;
            }
        }
    }

    /**
     * Checks whether a backend service is online by attempting to connect to its hostname and port number.
     *
     * @param hostName the hostname or IP address of the backend service
     * @param portNumber the TCP port number of the backend service
     * @return true if the backend service is reachable, false otherwise
     */
    private boolean isBackendServiceOnline(String hostName, int portNumber) {
        Socket socket = null;
        try {
            socket = new Socket(hostName, portNumber);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket to [" + hostName + ":" + portNumber + "] - " + e.getMessage());
            }
        }
    }
}