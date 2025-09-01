/**
 * Exception thrown no {@link BackendService} object is available to direct
 * a TCP message to.
 */
public class NoBackendServiceAvailableException extends RuntimeException {

    /**
     * Constructs a new NoBackendServiceAvailableException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoBackendServiceAvailableException(String message) {
        super(message);
    }
}
