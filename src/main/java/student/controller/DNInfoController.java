package student;

import java.io.PrintStream;

/**
 * Controller for DNInfo application.
 * Handles the logic of looking up a domain and displaying it.
 */
public class DNInfoController {
    private final DomainRepository repo;
    private final DomainLookupService lookupService;

    /**
     * Constructor for DNInfoController.
     * @param repo the domain repository
     * @param lookupService the domain lookup service
     */
    public DNInfoController(DomainRepository repo, 
                    DomainLookupService lookupService) {
        this.repo = repo;
        this.lookupService = lookupService;
    }

    /**
     * This is the main task of controller, it will pass user's requests:
     * hostname and format to the model objects. 
     * Then It will process the Domain object received using View methods.
     * 
     * @param hostname the hostname to look up
     * @param format the format to display the result in
     * @throws Exception if the domain cannot be found
     */
    public void handle(String hostname, Format format, PrintStream out) throws Exception {
        Domain domain;
        domain = repo.findByHostname(hostname);
        if (domain == null) {
            domain = lookupService.lookup(hostname);
            if (domain == null) {
               throw new DomainNotFoundException(hostname);
            }
            repo.save(domain);
        }
        View view = ViewFactory.getView(format);
        view.render(domain, out);
    }
}