import student.model.*;
import student.view.*;
import student.controller.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the DNInfoController class using stubbed DomainRepository
 * and DomainLookupService objects to simulate different lookup scenarios.
 * Sim 0 simulates a domain found in the local repository.
 * Sim 1 simulates a domain not found locally but found via the lookup service.
 * Sim 2 simulates a domain not found anywhere, expecting a DomainNotFoundException.
 */
public class TestDNInfoController {

    /** Captures output written during controller handle calls for assertion. */
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /** A PrintStream backed by outContent, passed to controller handle methods. */
    PrintStream testOut = new PrintStream(outContent);

    /** A simulated Domain object used as the expected result across all simulations. */
    Domain simDomain = new Domain("www.github.com", "140.82.112.3",
            "San Francisco", "California", "US", "94110", 37.7509, -122.4153);

    // *--- SIM 0 fields ---*

    /**
     * Sim 0: stubbed repository that always returns simDomain,
     * simulating a domain found in the local database.
     */
    DomainRepository stubRepo0 = new DomainRepository() {
        @Override
        public Domain findByHostname(String hostname) {
            return simDomain;
        }

        @Override
        public List<String> getAllHostnames() {
            return List.of("www.github.com");
        }
    };

    /**
     * Sim 0: stubbed lookup service that should never be called
     * when the domain is found locally. Fails the test if called.
     */
    DomainLookupService stubLookupService0 = new DomainLookupService() {
        @Override
        public Domain lookup(String hostname) throws Exception {
            fail("Lookup service should not be called if domain found locally");
            return null;
        }
    };

    // *--- SIM 1 fields ---*

    /**
     * Sim 1: stubbed repository that always returns null,
     * simulating a domain not found in the local database.
     */
    DomainRepository stubRepo1 = new DomainRepository() {
        @Override
        public Domain findByHostname(String hostname) {
            return null;
        }

        @Override
        public List<String> getAllHostnames() {
            return List.of("www.github.com");
        }
    };

    /**
     * Sim 1: stubbed lookup service that always returns simDomain,
     * simulating a successful internet lookup.
     */
    DomainLookupService stubLookupService1 = new DomainLookupService() {
        @Override
        public Domain lookup(String hostname) {
            return simDomain;
        }
    };

    // *--- SIM 2 fields ---*

    /**
     * Sim 2: stubbed repository that always returns null,
     * simulating a domain not found in the local database.
     */
    DomainRepository stubRepo2 = new DomainRepository() {
        @Override
        public Domain findByHostname(String hostname) {
            return null;
        }
    };

    /**
     * Sim 2: stubbed lookup service that always returns null,
     * simulating a domain not found anywhere.
     */
    DomainLookupService stubLookupService2 = new DomainLookupService() {
        @Override
        public Domain lookup(String hostname) {
            return null;
        }
    };

    /**
     * Sim 0 - Pretty: verifies the controller renders the hostname and IP
     * in pretty format when the domain is found in the local repository.
     * Fails if the lookup service is called.
     */
    @Test
    void testSim0Pretty() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handle("www.github.com", Format.PRETTY, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 0 - JSON: verifies the controller renders the hostname and IP
     * in JSON format when the domain is found in the local repository.
     * Fails if the lookup service is called.
     */
    @Test
    void testSim0JSON() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handle("www.github.com", Format.JSON, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 0 - XML: verifies the controller renders the hostname and IP
     * in XML format when the domain is found in the local repository.
     * Fails if the lookup service is called.
     */
    @Test
    void testSim0XML() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handle("www.github.com", Format.XML, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 0 - CSV: verifies the controller renders the hostname and IP
     * in CSV format when the domain is found in the local repository.
     * Fails if the lookup service is called.
     */
    @Test
    void testSim0CSV() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handle("www.github.com", Format.CSV, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 1 - XML: verifies the controller falls back to the lookup service
     * and renders correctly in XML format when the domain is not found locally.
     */
    @Test
    void testSim1XML() throws Exception {
        DNInfoController controller1 = new DNInfoController(stubRepo1, stubLookupService1);
        controller1.handle("www.github.com", Format.XML, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 1 - CSV: verifies the controller falls back to the lookup service
     * and renders correctly in CSV format when the domain is not found locally.
     */
    @Test
    void testSim1CSV() throws Exception {
        DNInfoController controller1 = new DNInfoController(stubRepo1, stubLookupService1);
        controller1.handle("www.github.com", Format.CSV, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /**
     * Sim 2: verifies the controller throws a DomainNotFoundException
     * when the domain is not found in the local repository or via the lookup service.
     */
    @Test
    void testSim2DomainNotFound() {
        DNInfoController controller2 = new DNInfoController(stubRepo2, stubLookupService2);
        assertThrows(DomainNotFoundException.class,
                () -> controller2.handle("www.unknown.com", Format.PRETTY, testOut));
    }

    /**
     * Sim 0 - handleAll: verifies the controller renders all domains from the
     * repository correctly when handleAll is called.
     */
    @Test
    void testSim0HandleAll() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handleAll(Format.PRETTY, testOut);
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
    }

    /**
     * Sim 0 - handleAll JSON: verifies the controller renders all domains as a
     * JSON array when handleAll is called with JSON format.
     */
    @Test
    void testSim0HandleAllJSON() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, stubLookupService0);
        controller0.handleAll(Format.JSON, testOut);
        String output = outContent.toString();
        assertTrue(output.startsWith("["));
        assertTrue(output.contains("www.github.com"));
    }
}
