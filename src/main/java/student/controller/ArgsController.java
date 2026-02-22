package student.controller;

import java.io.OutputStream;
import student.model.DomainNameModel;
import student.model.formatters.Formats;

/**
 * A controller to handle the arguments.
 */
public class ArgsController {
    /** Model of the application. */
    private DomainNameModel model;
    /** The format to output. */
    private Formats format = Formats.PRETTY;
    /** The format of the output stream defaulting to System.out. */
    private OutputStream output = System.out;
    /** The hostname to look up. */
    private String hostname = "all"; // default to all



    /**
     * Get the help message. Left this here, so you didn't have to write it - however you are free
     * to change it and the file name if you want/need to.
     * 
     * @return the help message
     */
    public String getHelp() {
        return """
                DNInfoApp [hostname|all] [-f json|xml|csv|pretty] [-o file path] [-h | --help] [--data filepath]

                Looks up the information for a given hostname (url) or displays information for
                all domains in the database. Can be output in json, xml, csv, or pretty format.
                If -o file is provided, the output will be written to the file instead of stdout.

                --data is mainly used in testing to provide a different data file, defaults to the hostrecords.xml file.
                """;
    }



}
