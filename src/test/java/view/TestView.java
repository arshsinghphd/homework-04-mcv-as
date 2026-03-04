import student.view.*;
import student.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all view classes.
 * Format enum enforces the kind of input possible to ViewFactory.
 * Thus, the only edge case is null input.
 */
class TestView {

    /** A simulated Domain object used as test input across all view tests. */
    Domain simDomain;

    /** Captures output written to the test PrintStream for assertion. */
    ByteArrayOutputStream outContent;

    /** A PrintStream backed by outContent, passed to view render methods. */
    PrintStream testOut;

    /** Sets up a simulated domain and output stream before each test. */
    @BeforeEach
    void setUp() {
        simDomain = new Domain("www.github.com", "140.82.112.3",
                "San Francisco", "California", "US", "94110", 37.7509, -122.4153);
        // this will capture output
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
     * Tests that PrettyView renders the hostname, IP, location, and coordinates
     * on separate lines in the expected format.
     */
    @Test
    void testPrettyView() {
        IView pv = ViewFactory.getView(Format.PRETTY);
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("www.github.com", lines[0].strip());
        assertEquals("IP: 140.82.112.3", lines[1].strip());
        assertEquals("Location: San Francisco, California, US, 94110", lines[2].strip());
        assertEquals("Coordinates: 37.7509, -122.4153", lines[3].strip());
    }

    /**
     * Tests that CSVView renders a header row followed by a correctly
     * formatted data row for a single domain.
     */
    @Test
    void testCSVView() {
        IView pv = ViewFactory.getView(Format.CSV);
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
     * Tests that XMLView renders a domain wrapped in a domainList root element,
     * with the hostname on line 3 and longitude on line 10.
     */
    @Test
    void testXMLView() {
        IView pv = ViewFactory.getView(Format.XML);
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("<hostname>www.github.com</hostname>",
                lines[2].strip());
        assertEquals("<longitude>-122.4153</longitude>",
                lines[9].strip());
    }

    /**
     * Tests that JSONView renders a single domain as a JSON array containing
     * one compact JSON object, with the hostname as the first field
     * and longitude as the last field.
     */
    @Test
    void testJSONView() {
        IView pv = ViewFactory.getView(Format.JSON);
        pv.render(simDomain, testOut);
        String output = outContent.toString();
        String[] lines = output.split(",");
        assertEquals("[{\"hostname\":\"www.github.com\"", lines[0].strip());
        assertEquals("\"longitude\":-122.4153}]", lines[7].strip());
    }

    /**
     * Tests that JSONView renderAll wraps multiple domains in a single
     * JSON array with a comma separator between entries.
     */
    @Test
    void testJSONRenderAll() {
        List<Domain> domains = List.of(simDomain, simDomain);
        IView view = ViewFactory.getView(Format.JSON);
        view.renderAll(domains, testOut);
        String output = outContent.toString();
        assertTrue(output.startsWith("["));
        assertTrue(output.endsWith("]\n") || output.endsWith("]"));
        assertTrue(output.contains(","));
    }

    /**
     * Tests that XMLView renderAll wraps all domain elements in a single
     * domainList root element.
     */
    @Test
    void testXMLRenderAll() {
        List<Domain> domains = List.of(simDomain, simDomain);
        IView view = ViewFactory.getView(Format.XML);
        view.renderAll(domains, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("<domainList>"));
        assertTrue(output.contains("</domainList>"));
    }

    /**
     * Tests that CSVView renderAll prints the header row exactly once,
     * regardless of how many domains are rendered.
     */
    @Test
    void testCSVRenderAllHasOneHeader() {
        List<Domain> domains = List.of(simDomain, simDomain);
        IView view = ViewFactory.getView(Format.CSV);
        view.renderAll(domains, testOut);
        String output = outContent.toString();
        long headerCount = output.lines()
                .filter(l -> l.equals("hostname,ip,city,region,country,postal,latitude,longitude"))
                .count();
        assertEquals(1, headerCount, "Header should appear exactly once");
    }

    /**
     * Tests that JSONView renderAll produces an empty JSON array
     * when given an empty list of domains.
     */
    @Test
    void testRenderAllEmptyList() {
        List<Domain> domains = List.of();
        IView view = ViewFactory.getView(Format.JSON);
        view.renderAll(domains, testOut);
        String output = outContent.toString();
        assertEquals("[]", output.strip());
    }

    /**
     * Tests that JSONView render wraps a single domain in a JSON array,
     * starting with '[' and ending with ']'.
     */
    @Test
    void testJSONSingleWrappedInArray() {
        IView view = ViewFactory.getView(Format.JSON);
        view.render(simDomain, testOut);
        String output = outContent.toString().strip();
        assertTrue(output.startsWith("["));
        assertTrue(output.endsWith("]"));
    }
}
