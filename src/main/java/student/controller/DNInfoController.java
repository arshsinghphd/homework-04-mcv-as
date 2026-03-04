package student.controller;
import student.model.*;
import student.view.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Looks up all hostnames in the repository and renders them together
     * as a collection in the given format.
     *
     * @param format the format to display the results in
     * @param out the PrintStream to use
     * @throws Exception if a domain cannot be found or there is a rendering error
     */
    public void handleAll(Format format, PrintStream out) throws Exception {
        List<String> allHostnames = repo.getAllHostnames();
        if (allHostnames.isEmpty()) {
            out.println("No entries found in data file.");
            return;
        }

        // collect all domains first
        List<Domain> domains = new ArrayList<>();
        for (String hostname : allHostnames) {
            Domain domain = repo.findByHostname(hostname);
            if (domain == null) {
                domain = lookupService.lookup(hostname);
                if (domain == null) {
                    throw new DomainNotFoundException(hostname);
                }
                repo.save(domain);
            }
            domains.add(domain);
        }

        // render them all together
        IView view = ViewFactory.getView(format);
        view.renderAll(domains, out);
    }
}
