package student;

import student.controller.*;
import student.model.*;
import student.view.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

/** This class is the main entry point to the app. */
public final class DNInfoApp {

    /** Private constructor to prevent instantiation. */
    private DNInfoApp() {
    }

    /**
    * Prints usage information to System.out.
    */
    private static void printHelp() {
        System.out.println("DNInfoApp [hostname|all] [-f json|xml|csv|pretty] [-o file path] [-h | --help] "
                + "[--data filepath]");
        System.out.println();
        System.out.println("Looks up the information for a given hostname (url) or displays information for");
        System.out.println("all domains in the database. Can be output in json, xml, csv, or pretty format.");
        System.out.println("If -o file is provided, the output will be written to the file instead of stdout.");
        System.out.println();
        System.out.println("--data is mainly used in testing to provide a different data file,"
                + " defaults to the hostrecords.xml file.");
    }


    /**
     * Main entry point for the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // take args from user and validate
        if (args == null || args.length == 0) {
            printHelp();
            return;
        }

        // Stores the hostname.
        String hostname = null;

        // Stores the format being requested.
        Format format = Format.PRETTY; // default format

        //Stores the outputPath of the file to be written, Defaults to printing on the terminal.
        String outputPath = null;

        // Stores the Path of the local repo, defaults to hostrecords.xml.
        String dataPath = null;

        //parse arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    printHelp();
                    return;
                case "-f":
                    if (i + 1 >= args.length) {
                        System.out.println("Error: -f requires a format argument.");
                        printHelp();
                        return;
                    }
                    try {
                        format = Format.valueOf(args[++i].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid format: " + args[i]);
                        printHelp();
                        return;
                    }
                    break;
                case "-o":
                    if (i + 1 >= args.length) {
                        System.out.println("Error: -o requires a file path.");
                        printHelp();
                        return;
                    }
                    String oArg = args[++i];
                    if (!oArg.equalsIgnoreCase("stdout")) {
                        outputPath = oArg;
                    }
                    break;
                case "--data":
                    if (i + 1 >= args.length) {
                        System.out.println("Error: --data requires a file path.");
                        printHelp();
                        return;
                    }
                    dataPath = args[++i];
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        System.out.println("Unknown option: " + args[i]);
                        printHelp();
                        return;
                    }
                    if (hostname == null) {
                        hostname = args[i]; // first positional arg is hostname
                    } else {
                        // second positional arg is format
                        try {
                            format = Format.valueOf(args[i].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid format: " + args[i]);
                            printHelp();
                            return;
                        }
                    }
                    break;
            }
        }

        // set up output stream
        PrintStream out;
        if (outputPath != null) {  // printing on outputPath file
            try {
                out = new PrintStream(outputPath);
            } catch (FileNotFoundException e) {
                System.out.println("Error: Could not open output file: " + outputPath);
                return;
            }
        } else {  // if outputPath is null printing on System.out by default
            out = System.out;
        }

        // call controller
        try {
            DomainRepository repo = new DomainRepository(dataPath);
            DomainLookupService lookupService = new DomainLookupService();
            DNInfoController controller = new DNInfoController(repo, lookupService);
            if (hostname == null || hostname.equalsIgnoreCase("all")) {
                List<String> allHostnames = repo.getAllHostnames();
                if (allHostnames.isEmpty()) {
                    System.out.println("No entries found in data file.");
                    return;
                }
                for (String host : allHostnames) {
                    controller.handle(host, format, out);
                }
            } else {
                controller.handle(hostname, format, out);
            }

        } catch (DomainNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        // using finally for tasks after try and catch
        } finally {
            if (outputPath != null) {
                // if writing files, close PrintStream to save the file
                out.close();
            }
        }
    }

}
