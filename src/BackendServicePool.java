import java.util.List;

/**
 * A class representing a pool that maintains a list of {@link BackendService}
 * objects that the load balancer can forward a TCP message to, as well as the
 * algorithm for deciding which {@link BackendService} the messages will be
 * forwarded to.
 *
 * It uses a round robin selection process to select the backend service to
 * forward the TCP message to, keeping track of the next available backend
 * service using an index.
 */
public class BackendServicePool {

    /**
     * A list representing the pool of {@link BackendService} objects that
     * a TCP message can be forwarded to.
     */
    private final List backendServices;

    /**
     * The index of the next {@link BackendService} object in the backend
     * service pool that a TCP message is to be forwarded to.
     */
    private int nextBackendServiceIndex;

    /**
     * Constructs a {@link BackendServicePool} object with a given
     * list of {@link BackendService} objects for selection, with
     * the index of the next {@link BackendService} object instantiated
     * as 0 i.e. the first in the pool.
     *
     * @param backendServices the list of backend services available
     *                        for selection.
     */
    public BackendServicePool(List backendServices) {
        this.nextBackendServiceIndex = 0;
        this.backendServices = backendServices;
    }

    /**
     * Selects the {@link BackendService} where an incoming TCP message is
     * to be forwarded to. A round robin selection algorithm is used, with
     * an index being maintained to select the next available {@link BackendService}
     * object in the list.
     *
     * @return the selected {@link BackendService} object.
     */
    public BackendService selectBackendService() {
        if (nextBackendServiceIndex == backendServices.size()) {
            nextBackendServiceIndex = 0;
        }
        return (BackendService) backendServices.get(nextBackendServiceIndex++);
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
