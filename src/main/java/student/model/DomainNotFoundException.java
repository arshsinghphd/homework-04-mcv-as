package student.model;

/**
 * Exception thrown when a domain cannot be found locally or on the internet.
 */
public class DomainNotFoundException extends Exception {
    /**
     * Constructor for DomainNotFoundException
     * @param hostname
     */
    public DomainNotFoundException(String hostname) {
        super("Domain not found: " + hostname);
    }
}
