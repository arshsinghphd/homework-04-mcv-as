package student.view;

/** Parses format and returns a matching function. */
public class ViewFactory {

    private ViewFactory() {
        // to prevent instantiation
    }

    /** The only task of ViewFactory is to return a method based on format.
     *@param format in which the output is expected.
     *@return an IView object.
     *@throws IllegalArgumentException if an invalid Format string is passed.
     */
    public static IView getView(Format format) {
        if (format == null) {
            throw new IllegalArgumentException("Format cannot be null");
        }
        switch (format) {
            case PRETTY:
                return new PrettyView();
            case XML:
                return new XMLView();
            case JSON:
                return new JSONView();
            case CSV:
                return new CSVView();
            default:
                throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}
