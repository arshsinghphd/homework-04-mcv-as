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
    /** The repository used to store and retrieve Domain information from the local XML file. */
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
     * Looks up a domain by hostname and renders it in the given format.
     * If the domain is not found in the repository, it is looked up online
     * and saved to the repository for future use.
     *
     * @param hostname the hostname to look up
     * @param format the format to display the result in
     * @param out the PrintStream to write the output to
     * @throws DomainNotFoundException if the domain cannot be found locally or online
     * @throws Exception if there is an error reading or writing the repository
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
     * @throws DomainNotFoundException if any domain in the repository cannot be found online
     * @throws Exception if there is an error reading the repository
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
