package student.view;

import student.model.*;

import java.io.PrintStream;

public interface View {
    void render(Domain domain, PrintStream out);
}
