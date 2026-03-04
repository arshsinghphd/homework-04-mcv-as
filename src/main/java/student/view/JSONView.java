package student.view;

import student.model.*;
import java.io.PrintStream;
import java.util.List;

/** Renders Domain information in JSON format. */
public class JSONView implements IView {

    /**
     * Renders a single domain as a compact JSON object.
     *
     * @param domain the Domain object to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void render(Domain domain, PrintStream out) {
        out.print(toJson(domain));
    }

    /**
     * Renders a list of domains as a compact JSON array.
     *
     * @param domains the list of Domain objects to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void renderAll(List<Domain> domains, PrintStream out) {
        out.print("[");
        for (int i = 0; i < domains.size(); i++) {
            out.print(toJson(domains.get(i)));
            if (i < domains.size() - 1) {
                out.print(",");
            }
        }
        out.println("]");
    }

    /**
     * Converts a Domain object to a compact JSON string.
     *
     * @param domain the Domain object to convert.
     * @return a compact JSON string representation of the domain.
     */
    private String toJson(Domain domain) {
        return String.format(
                "{\"hostname\":\"%s\",\"ip\":\"%s\",\"city\":\"%s\","
                        + "\"region\":\"%s\",\"country\":\"%s\",\"postal\":\"%s\","
                        + "\"latitude\":%.4f,\"longitude\":%.4f}",
                domain.hostname(), domain.ip(), domain.city(),
                domain.region(), domain.country(), domain.postal(),
                domain.latitude(), domain.longitude()
        );
    }
}
