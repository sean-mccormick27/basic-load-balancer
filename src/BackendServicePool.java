import java.util.List;

/**
 * A class representing a pool that maintains a list of {@link BackendService}
 * objects that the load balancer can forward a TCP message to, as well as the
 * algorithm for deciding which {@link BackendService} the messages will be
 * forwarded to.
 *
 * For now, it simply selects the first {@link BackendService} in the pool.
 */
public class BackendServicePool {

    private final List backendServices;

    public BackendServicePool(List backendServices) {
        this.backendServices = backendServices;
    }

    public BackendService selectBackendService() {
        return (BackendService) backendServices.get(0);
    }

    public List getBackendServices() {
        return backendServices;
    }
}
