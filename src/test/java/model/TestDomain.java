import student.model.*;
import student.view.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Domain Classes.
 */
class TestDomain {

    /** Tests the constructor based on happy path input. */
    @Test
    void testHappyConstructor() {
        Domain domain = new Domain("www.github.com", "140.82.112.3", 
            "San Francisco", "California", "US", "94110", 37.7509, -122.4153);
        
        assertEquals("www.github.com", domain.hostname());
        assertEquals("140.82.112.3", domain.ip());
    }

    /** Tests the constructor with an invalid hostname argument. */
    @Test
    void testInvalidHostName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "", "140.82.112.3", "San Francisco", "California", "US", 
                        "94110", 37.7509, -122.4153); 
                    });
        assertEquals("Hostname cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid IP argument. */
    @Test
    void testInvalidIP() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "", "San Francisco", "California", "US", 
                        "94110", 37.7509, -122.4153); 
                    });
        assertEquals("IP cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid city argument. */
    @Test
    void testInvalidCity() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "", "California", "US", 
                        "94110", 37.7509, -122.4153); 
                    });
        assertEquals("City cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid region argument. */
    @Test
    void testInvalidRegion() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "San Francisco", "", "US", 
                        "94110", 37.7509, -122.4153); 
                    });
        assertEquals("Region cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid country argument. */
    @Test
    void testInvalidCountry() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "San Francisco", 
                    "California", "", "94110", 37.7509, -122.4153); 
                    });
        assertEquals("Country cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid postal-code argument. */
    @Test
    void testInvalidPostal() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "San Francisco", 
                    "California", "US", "", 37.7509, -122.4153); 
                    });
        assertEquals("Postal cannot be null or empty", exception.getMessage());
    }

    /** Tests the constructor with an invalid latitude argument. */
    @Test
    void testInvalidLatitude() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "San Francisco", 
                    "California", "US", "94110", 137.7509, -122.4153); 
                    });
        assertEquals("Latitude must be between -90 and 90", exception.getMessage());
    }

    /** Tests the constructor with an invalid longitude argument. */
    @Test
    void testInvalidLongitude() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> { 
                Domain domain = new Domain(
                    "www.github.com", "140.82.112.3", "San Francisco", 
                    "California", "US", "94110", 37.7509, -222.4153); 
                    });
        assertEquals("Longitude must be between -180 and 180", exception.getMessage());
    }

}
