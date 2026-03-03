package student.view;

import student.model.*;

import java.io.PrintStream;
/** An interface that the other views classes are based on. */
public interface IView {
    void render(Domain domain, PrintStream out);
}
