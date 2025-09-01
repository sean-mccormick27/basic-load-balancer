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
     * to be forwarded to. A round robin selection algorithm is used, maintaining an
     * index of the next backend service to be used. Each backend service in
     * the pool is checked in turn, returning the first available {@link BackendService}
     * that is online. If no available backend services exist in the pool, no
     * {@link BackendService} object is returned.
     *
     * @return the selected {@link BackendService} object that is online, or null if none are available.
     */
    public BackendService selectBackendService() throws NoBackendServiceAvailableException {
        int selectionAttempts = 0;
        while (selectionAttempts < backendServices.size()) {
            BackendService selectedBackendService = (BackendService) backendServices.get(nextBackendServiceIndex);
            nextBackendServiceIndex = (nextBackendServiceIndex + 1) % backendServices.size();
            selectionAttempts++;
            if (selectedBackendService.isOnline()) {
                return selectedBackendService;
            }
        }
        throw new NoBackendServiceAvailableException("No backend service available!");
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
