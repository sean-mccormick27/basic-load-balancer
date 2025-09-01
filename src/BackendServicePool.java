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

    /**
     * A list representing the pool of {@link BackendService} objects that
     * a TCP message can be forwarded to.
     */
    private final List backendServices;

    /**
     * Constructs a {@link BackendServicePool} object with a given
     * list of {@link BackendService} objects for selection.
     *
     * @param backendServices the list of backend services available
     *                        for selection.
     */
    public BackendServicePool(List backendServices) {
        this.backendServices = backendServices;
    }

    /**
     * Selects the {@link BackendService} where an incoming TCP message is
     * to be forwarded to. For now, the first object in the pool is selected.
     *
     * @return the selected {@link BackendService} object.
     */
    public BackendService selectBackendService() {
        //TODO: Write a more sophisticated selection algorithm.
        return (BackendService) backendServices.get(0);
    }

    /**
     * Gets the list of {@link BackendService} objects in the pool.
     *
     * @return the list of {@link BackendService} objects in the pool.
     */
    public List getBackendServices() {
        return backendServices;
    }
}
