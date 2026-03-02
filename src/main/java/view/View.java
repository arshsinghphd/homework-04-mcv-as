package view;

import model.Domain;

import java.io.PrintStream;

public interface View {
    void render(Domain domain, PrintStream out);
}
