package student.controller;
import student.model.*;
import student.view.*;
import java.io.PrintStream;

/**
 * Controller for DNInfo application.
 * Handles the logic of looking up a domain and displaying it.
 */
public class DNInfoController {
    /** Stores Domain information in the local XML file. */
    private final DomainRepository repo;
    /** The DomainLookupService object that will look-up information if corresponding Domain is not in repo.*/
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
     * @param out the PrintStream to use
     * @throws Exception if the domain cannot be found
     */
    public void handle(String hostname, Format format, PrintStream out) throws Exception {
        Domain domain;
        domain = repo.findByHostname(hostname); // look up domain in repo
        if (domain == null) {  // if not found
            domain = lookupService.lookup(hostname);  // look-up on the internet
            if (domain == null) {
               throw new DomainNotFoundException(hostname);   // still not found, throw an error
            }
            repo.save(domain);  // if found, add to repo
        }
        IView view = ViewFactory.getView(format); // return a view based on format
        view.render(domain, out);  // render the information from domain to out
    }
}
