package view;

import model.Domain;
import java.io.PrintStream;

public class PrettyView implements View {

    @Override
    public void render(Domain domain, PrintStream out) {
        out.printf(domain.hostname() + "%n");
        out.printf("%16s %s%n", "IP:", domain.ip());
        out.printf("%16s %s, %s, %s, %s%n", 
            "Location:", 
            domain.city(),
            domain.region(),
            domain.country(),
            domain.postal()
        );
        out.printf("%16s %.4f, %.4f%n",
            "Coordinates:", 
            domain.latitude(),
            domain.longitude()
        );
    }
}