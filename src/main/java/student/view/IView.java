package student.view;

import student.model.*;
import java.io.PrintStream;

/** An interface that the other views classes are based on. */
public interface IView {

    /** The only task of view is to render information stored in the Domain object on a PrintStream.
     * @param domain Domain object.
     * @param out the PrintStream to render domain information on.
     */
    void render(Domain domain, PrintStream out);
}
