import student.*;
import java.io.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TestDNInfoApp {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStream() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testRunWithProperArgumentsPretty() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "pretty"}));
    }

    @Test
    void testRunWithProperArgumentsXML() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "xml"}));
    }

    @Test
    void testRunWithProperArgumentsJSON() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "json"}));
    }

    @Test
    void testRunWithProperArgumentsCSV() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "csv"}));
    }

    @Test
    void testPrintsExpectedCSV() {
        DNInfoApp.main(new String[]{"www.github.com", "csv"});
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("www.github.com,140.82.112.3,San Francisco,California,US,94110,37.7509,-122.4153", lines[1]);
    }

    @Test
    void testPrintsExpectedHelpMessage() {
        DNInfoApp.main(new String[]{});
        String errors = outContent.toString();
        String[] lines = errors.split("\\n");
        assertTrue(lines[0].contains("Usage:"));
    }

    @Test
    void testUnxpectedFormat() {
        DNInfoApp.main(new String[]{"www.github.com", "yml"});
        String errors = outContent.toString();
        String[] lines = errors.split("\\n");
        assertTrue(lines[0].contains("Invalid"));
    }
}
