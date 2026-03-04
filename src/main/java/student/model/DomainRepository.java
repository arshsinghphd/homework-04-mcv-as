package student.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
 * Repository for Domain objects based on a local XML file.
 * Supports looking up domains by hostname, retrieving all hostnames,
 * and saving new domains. Defaults to DEFAULT_XML_FILE if no path is provided.
 */
public class DomainRepository {

    /** Stores the string path to the local XML file. */
    private String xmlFile;

    /** Stores the path to the default local XML file. */
    private static final String DEFAULT_XML_FILE = "data/hostrecords.xml";

    /** XmlMapper used to serialize and deserialize Domain objects to and from XML. */
    private final XmlMapper mapper = new XmlMapper();

    /**
     * Setter for xmlFile.
     * @param xmlFile path to the xmlFile.
     */
    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * Primary constructor.
     * @param xmlFile path to the xmlFile.
     */
    public DomainRepository(String xmlFile) {
        if (xmlFile == null || xmlFile.isBlank()) {
            setXmlFile(DEFAULT_XML_FILE);
        } else {
            setXmlFile(xmlFile);
        }
    }

    /** Empty args constructor. */
    public DomainRepository() {
        this(DEFAULT_XML_FILE);
    }

    /** Record DomainList stores a list of Domain objected created by reading the XML file row by row.
     * @param domains a list of Domain objects.
     */
    @JacksonXmlRootElement(localName = "domainList")
    record DomainList(
        @JacksonXmlElementWrapper(useWrapping = false)  // Decorator parameter: says we do not have wrapper
        @JacksonXmlProperty(localName = "domain")       /* Decorator parameter: says each item in the list will map to
                                                            <domain> tag in the XML, following the style in the
                                                            sample_working. */
        List<Domain> domains) {
        }

    /**
     * Helper method to load and parse the XML file into a DomainList.
     * @return the DomainList, or null if the file does not exist or has no domains
     * @throws Exception if there is an error reading the XML file
     */
    private DomainList loadDomainList() throws Exception {
        File file = new File(xmlFile);
        if (!file.exists()) {
            return null;
        }
        try {
            DomainList domainList = mapper.readValue(file, DomainList.class);
            return (domainList.domains() == null) ? null : domainList;
        } catch (Exception e) {
            throw new Exception("Error reading XML file: " + e.getMessage());
        }
    }

    /**
     * Reader: looks for domain information by hostname and returns it.
     * @param hostname the hostname to search for
     * @return the Domain if found, null if not or if there was error in the reading XML
     * @throws Exception if there is an error reading the XML file
     */
    public Domain findByHostname(String hostname) throws Exception {
        DomainList domainList = loadDomainList();
        if (domainList == null) {
            return null;
        }
        for (Domain domain : domainList.domains()) {
            if (domain.hostname().equalsIgnoreCase(hostname)) {
                return domain;
            }
        }
        return null;
    }

    /**
     * Returns a list of all hostnames in the XML file.
     * @return a list of all hostnames, or an empty list if none found
     * @throws Exception if there is an error reading the XML file
     */
    public List<String> getAllHostnames() throws Exception {
        DomainList domainList = loadDomainList();
        if (domainList == null) {
            return new ArrayList<>();
        }
        List<String> hostnames = new ArrayList<>();
        for (Domain domain : domainList.domains()) {
            hostnames.add(domain.hostname());
        }
        return hostnames;
    }

    /**
     * Writer: adds a domain to local database file if it does not already exist.
     * Does not update existing information.
     * @param domain a new domain to be added to the local database file.
     * @throws Exception if the file does not exist or there is an error writing to it.
     */
    public void save(Domain domain) throws Exception {
        File file = new File(xmlFile);

        if (!file.exists()) {
            throw new Exception("Data file not found: " + xmlFile);
        }

        try {
            DomainList domainList = mapper.readValue(file, DomainList.class);
            List<Domain> updatedDomains = domainList.domains() == null ? new ArrayList<>() 
                                             : new ArrayList<>(domainList.domains());
            // Linear search for hostname among domains in domainList
            for (Domain d : updatedDomains) {
                if (domain.hostname().equalsIgnoreCase(d.hostname())) {
                    return;  // return void if domain exists, no update
                }
            }
            updatedDomains.add(domain);
            DomainList updatedDomainList = new DomainList(updatedDomains);
            mapper.writeValue(file, updatedDomainList);
        } catch (Exception e) {
            throw new Exception("Error writing XML file: " + e.getMessage());
        }
    }

}
