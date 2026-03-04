package student.view;

import student.model.*;
import java.io.PrintStream;
import java.util.List;

/** Renders Domain information in CSV format. */
public class CSVView implements IView {

    /** The CSV header row defining the column order for all CSV output. */
    private static final String HEADER = "hostname,ip,city,region,country,postal,latitude,longitude";

    /**
     * Renders a single domain as a CSV row, including the header.
     *
     * @param domain the Domain object to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void render(Domain domain, PrintStream out) {
        out.println(HEADER);
        out.print(toRow(domain));
    }

    /**
     * Renders a list of domains as CSV rows with a single header row.
     *
     * @param domains the list of Domain objects to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void renderAll(List<Domain> domains, PrintStream out) {
        out.println(HEADER);
        for (Domain domain : domains) {
            out.print(toRow(domain));
        }
    }

    /**
     * Converts a Domain object to a CSV data row string.
     *
     * @param domain the Domain object to convert.
     * @return a CSV row string representation of the domain.
     */
    private String toRow(Domain domain) {
        return String.format("%s,%s,%s,%s,%s,%s,%.4f,%.4f%n",
                domain.hostname(),
                domain.ip(),
                domain.city(),
                domain.region(),
                domain.country(),
                domain.postal(),
                domain.latitude(),
                domain.longitude()
        );
    }
}
