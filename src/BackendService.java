/**
 * A class representing a backend service that incoming TCP traffic
 * can be forwarded to, as well as its online status.
 */
public class BackendService {

    /** The name of the backend service. */
    private final String serviceName;

    /** Denotes if the backend service is online or not. */
    private boolean isOnline;

    /**
     * Constructs a {@link BackendService} object with the given service name,
     * initially assumed to be online.
     *
     * @param serviceName the backend service name.
     */
    public BackendService(String serviceName) {
        this.serviceName = serviceName;
        this.isOnline = true;
    }

    /**
     * Gets the name of this backend service.
     *
     * @return the name of the backend service.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Gets the online status of the backend service.
     *
     * @return isOnline the backend service name.
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
}
