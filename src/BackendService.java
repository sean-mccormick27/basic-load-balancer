/**
 * A class representing a backend service with a given hostname and
 * port number that incoming TCP traffic can be forwarded to, as well as its online status.
 */
public class BackendService {

    /** The hostname of the backend service. */
    private final String hostName;

    /** The port number of the backend service. */
    private final int portNumber;

    /** Denotes if the backend service is online or not. */
    private boolean isOnline;

    /**
     * Constructs a {@link BackendService} object with the given host name
     * and port number, initially assumed to be online.
     *
     * @param hostName the backend service's host name
     * @param portNumber the backend service's port number
     */
    public BackendService(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.isOnline = true;
    }

    /**
     * Gets the host name of the backend service.
     *
     * @return the host name of the backend service.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Gets the port number of the backend service.
     *
     * @return the port number of the backend service.
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Gets the online status of the backend service.
     *
     * @return the online status of the backend service.
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Sets the online status of the backend service.
     *
     * @param online the online status of the backend service.
     */
    public void setOnline(boolean online) {
        isOnline = online;
    }

    /**
     * Gets a String representation of the backend service details i.e.
     * its hostname, port number and online status.
     *
     * @return String representation of the backend service details.
     */
    public String toString() {
        return hostName + ":" + portNumber + " is " + (isOnline ? "ON" : "OFF") + "LINE";
    }

}
