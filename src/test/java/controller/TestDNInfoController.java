package student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Tests the class DNIInfoController */
public class TestDNInfoController {

    // these local variables will capture output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream testOut = new PrintStream(outContent);
    
    // this Domain object will be used in Sim 0 and Sim 1
    Domain simDomain = new Domain("www.github.com", "140.82.112.3", 
            "San Francisco", "California", "US", "94110", 37.7509, -122.4153);


    /*--- SIM 0 fileds ---*/
    // Sim 0: simulated DomainRepo object found in local database
    DomainRepository stubRepo0 = new DomainRepository() {
        @Override
        public Domain findByHostname(String hostname) {
            return simDomain;
        }
    };
    // Sim 0: simulated DomainLookupService object that will not be called
    DomainLookupService stubLookupService0 = new DomainLookupService() {
        @Override
        public Domain lookup(String hostname) throws Exception {
            fail("Lookup service should not be called if domain found locally");
            return null;
        }
    };

    /*--- SIM 1 fields ---*/
    // Sim 1: hostname not found in local database
    DomainRepository stubRepo1 = new DomainRepository() {
        @Override
        public Domain findByHostname(String hostname) {
            return null; // simulate not found
        }
    };

    // Sim 1: hostname Lookup on Internet returns simDomain
    DomainLookupService stubLookupService1 = new DomainLookupService() {
        @Override
        public Domain lookup(String hostname) {  
            return simDomain;
        }
    };

    /** 
     * SIM 0 Test 1
     * If stubLookupService0 gets called the test fails
     * Also tests Pretty Print interaction
     */
    @Test
    void testSim0Pretty() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, 
                                                        stubLookupService0);

        controller0.handle("www.github.com", Format.PRETTY, testOut);
        // check output contains hostname
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /** 
     * SIM 0 Test 2
     * If stubLookupService0 gets called the test fails
     *  Also tests JSON interaction.
     */
    @Test
    void testSim0JSON() throws Exception {
        DNInfoController controller0 = new DNInfoController(stubRepo0, 
                                                        stubLookupService0);

        controller0.handle("www.github.com", Format.JSON, testOut);
        // check output contains hostname
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /** 
     * SIM 1 Test 1. stubRep1 return null, and stubLookupService1 should be called.
     * Also tests XML interaction.
     */
    @Test
    void testSim1XML() throws Exception {
        DNInfoController controller1 = new DNInfoController(stubRepo1, 
                                                        stubLookupService1);

        controller1.handle("www.github.com", Format.XML, testOut);
        // check output contains hostname
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

    /** 
     * SIM 1 Test 2. stubRep1 return null, and stubLookupService1 should be called.
     * Also tests CSV format interaction.
     */
    @Test
    void testSim1CSV() throws Exception {
        DNInfoController controller1 = new DNInfoController(stubRepo1, 
                                                        stubLookupService1);

        controller1.handle("www.github.com", Format.CSV, testOut);
        // check output contains hostname
        String output = outContent.toString();
        assertTrue(output.contains("www.github.com"));
        assertTrue(output.contains("140.82.112.3"));
    }

}