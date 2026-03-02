package view;

import model.Domain;
import java.io.PrintStream;

public class CSVView implements View {
    @Override
    public void render(Domain domain, PrintStream out) {
        out.println("hostname,ip,city,region,country,postal,latitude,longitude");
        out.printf("%s,%s,%s,%s,%s,%s,%.4f,%.4f%n", 
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
