package view;

public class ViewFactory{
    public static View getView(Format format) {
        if (format == null)
            throw new IllegalArgumentException("Format cannot be null");
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