import student.view.*;
import student.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all view classes.
 * Format enum enforces the kind of input possible to ViewFactory.
 * Thus, the only edge case is null input.
 */
class TestView {
    
    Domain simDomain;
    ByteArrayOutputStream outContent;
    PrintStream testOut;

    @BeforeEach
    void setUp() {
        simDomain = new Domain("www.github.com", "140.82.112.3", 
            "San Francisco", "California", "US", "94110", 37.7509, -122.4153);
        /** this will capture output */
        outContent = new ByteArrayOutputStream();
        testOut = new PrintStream(outContent);
    }

    /**
     * Tests that ViewFactory throws an exception for a null format.
     */
    @Test
    void testInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> 
        ViewFactory.getView(null));
    }

    /**
     * Tests the outcome in pretty view. Ignores spaces.
     */
    @Test
    void testPrettyView() {
        IView pv = ViewFactory.getView(Format.PRETTY);  // static method getView
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("www.github.com", lines[0].strip());
        assertEquals("IP: 140.82.112.3", lines[1].strip());
        assertEquals("Location: San Francisco, California, US, 94110", lines[2].strip());
        assertEquals("Coordinates: 37.7509, -122.4153", lines[3].strip());
    }

    /**
     * Tests the outcome in CSV View. Ignores spaces.
     */
    @Test
    void testCSVView() {
        IView pv = ViewFactory.getView(Format.CSV);  // static method getView
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("hostname,ip,city,region,country,postal,latitude,longitude", 
                    lines[0]);
        assertEquals("www.github.com,140.82.112.3,San Francisco,California,US,"+
            "94110,37.7509,-122.4153", 
                    lines[1]);
    }

    /**
     * Tests the outcome in XMLView. Ignores spaces.
     */
    @Test
    void testXMLView() {
        IView pv = ViewFactory.getView(Format.XML);  // static method getView
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("<hostname>www.github.com</hostname>",
                    lines[1].strip());
        assertEquals("<longitude>-122.4153</longitude>",
                    lines[8].strip());
    }

    /**
     * Tests the outcome in JSONView. Ignores spaces.
     */
    @Test
    void testJSONView() {
        IView pv = ViewFactory.getView(Format.JSON);  // static method getView
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split(",");
        testOut.println(lines);
        assertEquals("{\"hostname\":\"www.github.com\"", lines[0].strip());
        assertEquals("\"longitude\":-122.4153}", lines[7].strip());
    }
}
