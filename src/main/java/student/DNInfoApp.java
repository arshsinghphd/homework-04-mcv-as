package student;
import student.controller.*;
import student.model.*;
import student.view.*;

/** This class is the main entry point to the app. */
public final class DNInfoApp {

    /** Private constructor to prevent instantiation. */
    private DNInfoApp() {
    }

    /**
    * Prints usage information to System.err.
    */
    private static void printHelp() {
        System.out.println("Usage: DNInfoApp <hostname> <pretty|xml|json|csv>");
        System.out.println("Example: DNInfoApp www.github.com pretty");
    }


    /**
     * Main entry point for the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // take args from user and parse
        if (args == null || args.length < 2 || args.length > 2) {
            printHelp();  // a program that will write example uses
            return;
        } 
        String hostname = args[0];
        Format format;
        try {
            format = Format.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid format: " + args[1]);
            printHelp();
            return;
        }
        // call controller
        try {
            DomainRepository repo = new DomainRepository();
            DomainLookupService lookupService = new DomainLookupService();
            DNInfoController controller = new DNInfoController(repo, lookupService);
            controller.handle(hostname, format, System.out);
        } catch (DomainNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

}
