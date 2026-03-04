package student.view;

import student.model.*;
import java.io.PrintStream;
import java.util.List;

/** An interface that the other views classes are based on. */
public interface IView {

    /**
     * Renders the information stored in a single Domain object to a PrintStream.
     *
     * @param domain the Domain object containing the information to render.
     * @param out the PrintStream to render the domain information on.
     */
    void render(Domain domain, PrintStream out);

    /**
     * Renders a list of Domain objects to a PrintStream.
     * By default, renders each domain individually by calling {@link #render(Domain, PrintStream)}.
     * Implementations should override this method to provide format-specific
     * collection rendering, such as wrapping in a JSON array or XML root element.
     *
     * @param domains the list of Domain objects to render.
     * @param out the PrintStream to render the domain information on.
     */
    default void renderAll(List<Domain> domains, PrintStream out) {
        for (Domain domain : domains) {
            render(domain, out);
        }
    }
}
