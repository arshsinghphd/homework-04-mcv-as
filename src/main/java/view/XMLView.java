package view;

import model.Domain;
import java.io.PrintStream;

public class XMLView implements View {
    @Override
    public void render(Domain domain, PrintStream out) {
        out.printf("<domain>%n");
        out.printf("    <hostname>%s</hostname>%n", domain.hostname());
        out.printf("    <ip>%s</ip>%n", domain.ip());
        out.printf("    <city>%s</city>%n", domain.city());
        out.printf("    <region>%s</region>%n", domain.region());
        out.printf("    <country>%s</country>%n", domain.country());
        out.printf("    <postal>%s</postal>%n", domain.postal());
        out.printf("    <latitude>%.4f</latitude>%n", domain.latitude());
        out.printf("    <longitude>%.4f</longitude>%n", domain.longitude());
        out.printf("</domain>%n");
    }
}