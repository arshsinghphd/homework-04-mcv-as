package student.view;

import student.model.*;
import java.io.PrintStream;
import java.util.List;

/** Renders Domain information in XML format. */
public class XMLView implements IView {

    /**
     * Renders a single domain as an XML domain element wrapped in a domainList root element.
     *
     * @param domain the Domain object to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void render(Domain domain, PrintStream out) {
        out.printf("<domainList>%n");
        out.print(toXml(domain));
        out.printf("</domainList>%n");
    }

    /**
     * Renders a list of domains as XML domain elements wrapped in a domainList root element.
     *
     * @param domains the list of Domain objects to render.
     * @param out the PrintStream to render the domain information on.
     */
    @Override
    public void renderAll(List<Domain> domains, PrintStream out) {
        out.printf("<domainList>%n");
        for (Domain domain : domains) {
            out.print(toXml(domain));
        }
        out.printf("</domainList>%n");
    }

    /**
     * Converts a Domain object to an XML domain element string.
     *
     * @param domain the Domain object to convert.
     * @return an XML string representation of the domain.
     */
    private String toXml(Domain domain) {
        return String.format(
                "<domain>%n"
                        + "    <hostname>%s</hostname>%n"
                        + "    <ip>%s</ip>%n"
                        + "    <city>%s</city>%n"
                        + "    <region>%s</region>%n"
                        + "    <country>%s</country>%n"
                        + "    <postal>%s</postal>%n"
                        + "    <latitude>%.4f</latitude>%n"
                        + "    <longitude>%.4f</longitude>%n"
                        + "</domain>%n",
                domain.hostname(), domain.ip(), domain.city(),
                domain.region(), domain.country(), domain.postal(),
                domain.latitude(), domain.longitude()
        );
    }
}
