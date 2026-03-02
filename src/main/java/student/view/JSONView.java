package student.view;
import student.model.*;

import java.io.PrintStream;

public class JSONView implements View {
    @Override
    public void render(Domain domain, PrintStream out) {
        out.printf("{%n");
        out.printf("    \"hostname\": \"%s\",%n", domain.hostname());
        out.printf("    \"ip\": \"%s\",%n", domain.ip());
        out.printf("    \"city\": \"%s\",%n", domain.city());
        out.printf("    \"region\": \"%s\",%n", domain.region());
        out.printf("    \"country\": \"%s\",%n", domain.country());
        out.printf("    \"postal\": \"%s\",%n", domain.postal());
        out.printf("    \"latitude\": %.4f,%n", domain.latitude());
        out.printf("    \"longitude\": %.4f%n", domain.longitude());
        out.printf("}%n");
    }
}
