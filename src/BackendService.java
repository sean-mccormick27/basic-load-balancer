/**
 * A class representing a backend service that incoming TCP traffic
 * can be forwarded to.
 */
public class BackendService {

    /** The name of the backend service. */
    private final String serviceName;

    /**
     * Constructs a {@link BackendService} object with the given service name.
     *
     * @param serviceName the backend service name.
     */
    public BackendService(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets the name of this backend service.
     *
     * @return the name of the backend service.
     */
    public String getServiceName() {
        return serviceName;
    }
}
