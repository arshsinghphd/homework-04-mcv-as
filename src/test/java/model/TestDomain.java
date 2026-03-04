import student.model.*;
import student.view.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Domain class.
 * Tests cover valid construction, invalid string fields (null and empty),
 * and invalid numeric fields (out of range latitude and longitude).
 */
class TestDomain {

    /**
     * Tests that a Domain is correctly constructed with valid arguments,
     * and that all fields are stored and returned as expected.
     */
    @Test
    void testHappyConstructor() {
        Domain domain = new Domain("www.github.com", "140.82.112.3",
                "San Francisco", "California", "US", "94110", 37.7509, -122.4153);
        assertEquals("www.github.com", domain.hostname());
        assertEquals("140.82.112.3", domain.ip());
        assertEquals("San Francisco", domain.city());
        assertEquals("California", domain.region());
        assertEquals("US", domain.country());
        assertEquals("94110", domain.postal());
        assertEquals(37.7509, domain.latitude());
        assertEquals(-122.4153, domain.longitude());
    }

    /**
     * Tests that the constructor throws an exception when an empty hostname is provided.
     */
    @Test
    void testInvalidHostName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("", "140.82.112.3", "San Francisco", "California", "US",
                        "94110", 37.7509, -122.4153));
        assertEquals("Hostname cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null hostname is provided.
     */
    @Test
    void testNullHostName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain(null, "140.82.112.3", "San Francisco", "California", "US",
                        "94110", 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when an empty IP is provided.
     */
    @Test
    void testInvalidIP() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "", "San Francisco", "California", "US",
                        "94110", 37.7509, -122.4153));
        assertEquals("IP cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null IP is provided.
     */
    @Test
    void testNullIP() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", null, "San Francisco", "California", "US",
                        "94110", 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when an empty city is provided.
     */
    @Test
    void testInvalidCity() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "", "California", "US",
                        "94110", 37.7509, -122.4153));
        assertEquals("City cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null city is provided.
     */
    @Test
    void testNullCity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", null, "California", "US",
                        "94110", 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when an empty region is provided.
     */
    @Test
    void testInvalidRegion() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco", "", "US",
                        "94110", 37.7509, -122.4153));
        assertEquals("Region cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null region is provided.
     */
    @Test
    void testNullRegion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco", null, "US",
                        "94110", 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when an empty country is provided.
     */
    @Test
    void testInvalidCountry() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "", "94110", 37.7509, -122.4153));
        assertEquals("Country cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null country is provided.
     */
    @Test
    void testNullCountry() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", null, "94110", 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when an empty postal code is provided.
     */
    @Test
    void testInvalidPostal() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", "", 37.7509, -122.4153));
        assertEquals("Postal cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a null postal code is provided.
     */
    @Test
    void testNullPostal() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", null, 37.7509, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when a latitude above 90 is provided.
     */
    @Test
    void testInvalidLatitude() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", "94110", 137.7509, -122.4153));
        assertEquals("Latitude must be between -90 and 90", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a latitude below -90 is provided.
     */
    @Test
    void testInvalidLatitudeNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", "94110", -91.0, -122.4153));
    }

    /**
     * Tests that the constructor accepts latitude at the boundary values of 90 and -90.
     */
    @Test
    void testBoundaryLatitude() {
        assertDoesNotThrow(() -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                "California", "US", "94110", 90.0, -122.4153));
        assertDoesNotThrow(() -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                "California", "US", "94110", -90.0, -122.4153));
    }

    /**
     * Tests that the constructor throws an exception when a longitude below -180 is provided.
     */
    @Test
    void testInvalidLongitude() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", "94110", 37.7509, -222.4153));
        assertEquals("Longitude must be between -180 and 180", exception.getMessage());
    }

    /**
     * Tests that the constructor throws an exception when a longitude above 180 is provided.
     */
    @Test
    void testInvalidLongitudePositive() {
        assertThrows(IllegalArgumentException.class,
                () -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                        "California", "US", "94110", 37.7509, 181.0));
    }

    /**
     * Tests that the constructor accepts longitude at the boundary values of 180 and -180.
     */
    @Test
    void testBoundaryLongitude() {
        assertDoesNotThrow(() -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                "California", "US", "94110", 37.7509, 180.0));
        assertDoesNotThrow(() -> new Domain("www.github.com", "140.82.112.3", "San Francisco",
                "California", "US", "94110", 37.7509, -180.0));
    }
}
