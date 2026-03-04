
import student.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;


/** Test DNIInfoApp. */
class TestDNInfoApp {

    /** Stores the System.out stream.*/
    private final PrintStream originalOut = System.out;

    /** Converts PrintStream to byte array. */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /** Set-up, divert the output to System.out. */
    @BeforeEach
    void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    /** Restore the output to System.out after tests. */
    @AfterEach
    void restoreStream() {
        System.setOut(originalOut);
        outContent.reset();
    }

    /** Tests the app returns as expected: with no args. */
    @Test
    void testPrintsHelpMessage() {
        DNInfoApp.main(new String[]{});
        String errors = outContent.toString();
        String[] lines = errors.split("\\n");
        assertTrue(lines[0].contains("Usage:"));
    }

    /** Tests the app returns as expected: with unexpected format. */
    @Test
    void testUnxpectedFormat() {
        DNInfoApp.main(new String[]{"www.github.com", "-f", "yml"});
        String errors = outContent.toString();
        String[] lines = errors.split("\\n");
        assertTrue(lines[0].contains("Invalid"));
    }

    /**
     * Tests the app does not run into errors with happy path args: pretty print format.
     */
    @Test
    void testRunWithProperArgumentsPretty() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "-f", "pretty"}));
    }

    /**
     * Tests the app does not run into errors with happy path args: XML format.
     */
    @Test
    void testRunWithProperArgumentsXML() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "-f", "xml"}));
    }

    /**
     * Tests the app does not run into errors with happy path args: json format.
     */
    @Test
    void testRunWithProperArgumentsJSON() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "-f", "json"}));
    }

    /**
     * Tests the app does not run into errors with happy path args: csv format.
     */
    @Test
    void testRunWithProperArgumentsCSV() {
        assertDoesNotThrow(() -> DNInfoApp.main(new String[]{"www.github.com", "-f", "csv"}));
    }

    /**
     * Tests the app returns as expected: pretty print format.
     */
    @Test
    void testPrintsExpectedPretty() {
        DNInfoApp.main(new String[]{"www.github.com"});
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertTrue(lines[0].contains("www.github.com"));
    }

    /**
     * Tests the app returns as expected: csv format.
     */
    @Test
    void testPrintCSV() {
        DNInfoApp.main(new String[]{"www.github.com", "-f", "csv"});
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertEquals("www.github.com,140.82.112.3,San Francisco,California,US,94110,37.7509,-122.4153",
                    lines[1]);
    }

    /**
     * Tests the app returns as expected: xml format.
     */
    @Test
    void testPrintXML() {
        DNInfoApp.main(new String[]{"www.github.com", "-f", "xml"});
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertTrue(lines[1].contains("www.github.com"));
    }

    /**
     * Tests the app returns as expected: json format.
     */
    @Test
    void testPrintJSON() {
        DNInfoApp.main(new String[]{"www.github.com", "-f", "json"});
        String output = outContent.toString();
        String[] lines = output.split("\\n");
        assertTrue(lines[1].contains("www.github.com"));
    }

    // Testing output files
    @TempDir
    Path tempDir;

    @Test
    void testWritesToTxtFile() throws Exception {
        Path outputFile = tempDir.resolve("output.txt");
        Files.deleteIfExists(outputFile); // delete if exists
        DNInfoApp.main(new String[]{
                "www.github.com",
                "-o", outputFile.toString()
        });

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String content = Files.readString(outputFile);
        assertFalse(content.isBlank(), "Output file should not be empty");
        assertTrue(content.contains("www.github.com"));
    }

    @Test
    void testWritesToJsonFile() throws Exception {
        Path outputFile = tempDir.resolve("output.json");
        Files.deleteIfExists(outputFile); // delete if exists
        DNInfoApp.main(new String[]{
                "www.github.com", "-f", "json",
                "-o", outputFile.toString()
        });

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String content = Files.readString(outputFile);
        assertFalse(content.isBlank(), "Output file should not be empty");
        assertTrue(content.contains("\"hostname\": \"www.github.com\""));
    }

    @Test
    void testWritesToXmlFile() throws Exception {
        Path outputFile = tempDir.resolve("output.xml");
        Files.deleteIfExists(outputFile); // delete if exists
        DNInfoApp.main(new String[]{
                "www.github.com", "-f", "xml",
                "-o", outputFile.toString()
        });

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String content = Files.readString(outputFile);
        assertFalse(content.isBlank(), "Output file should not be empty");
        assertTrue(content.contains("<hostname>www.github.com</hostname>"));
    }

    @Test
    void testWritesToCsvFile() throws Exception {
        Path outputFile = tempDir.resolve("output.csv");
        Files.deleteIfExists(outputFile); // delete if exists
        DNInfoApp.main(new String[]{
                "www.github.com", "-f", "csv",
                "-o", outputFile.toString()
        });
        assertTrue(Files.exists(outputFile), "Output file should be created");
        String content = Files.readString(outputFile);
        //originalOut.println(content);
        assertFalse(content.isBlank(), "Output file should not be empty");
        assertTrue(content.contains("www.github.com,140.82.112.3,San Francisco,California,US,94110,37.7509,-122.4153"));
    }

    @Test
    void testTryingWriteInvalidFormat() throws Exception {
        Path outputFile = tempDir.resolve("output.yml");
        Files.deleteIfExists(outputFile); // delete if exists
        DNInfoApp.main(new String[]{
                "www.github.com", "-f", "yml",
                "-o", outputFile.toString()
        });
        assertFalse(Files.exists(outputFile), "Output file should not be created");
    }
}
