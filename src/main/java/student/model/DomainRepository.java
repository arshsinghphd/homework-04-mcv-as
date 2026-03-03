package student.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

/** Class DomainRepository reads and writes to a local XML file. */
public class DomainRepository {
    /** Stores the string path to the local XML file. */
    private static final String XML_FILE = "data/hostrecords.xml";

    /** Stores an xml mapper */
    private final XmlMapper mapper = new XmlMapper();

    /** Empty constructor to prevent instantiation */
    public DomainRepository() {
    }

    /** Record DomainList stores a list of Domain objected created by reading the XML file row by row. */
    @JacksonXmlRootElement(localName = "domainList")
    record DomainList(
        //decorator parameter. says we do not have wrapper
        @JacksonXmlElementWrapper(useWrapping = false)  
        // decorator parameter. says each item in the list will map to <domain>. 
        // tag in the XML
        @JacksonXmlProperty(localName = "domain")  // following the example xml

        List<Domain> domains) {
        }

    /**
     * Reader: looks for domain information by hostname and returns it.
     * @param hostname the hostname to search for
     * @return the Domain if found, null if not or if there was error in the reading XML
     */
    public Domain findByHostname(String hostname) throws Exception {
        File file = new File(XML_FILE);

        if (!file.exists()) {
            return null;   // return null if file not found
        }

        try {
            DomainList domainList = mapper.readValue(file, DomainList.class);
            // Linear search for hostname among domains in domainList
            if (domainList.domains() == null) {
                return null;   // return null if file not found
            }

            for (Domain domain : domainList.domains()) {
                if (domain.hostname().equalsIgnoreCase(hostname)) {
                    return domain;  
                }
            }
        } catch (Exception e) {
            throw new Exception("Error reading XML file: " + e.getMessage());
        }

    return null;   // return null if not found
    }


    /**
     * Writer: adds a domain to local database file if it does not already exist.
     * Does not update existing information.
     * @param domain a new domain to be added to the local database file
     * @throws Exception
     */
    public void save(Domain domain) throws Exception {
        File file = new File(XML_FILE);

        if (!file.exists()) {
            return;   // return void if file not found
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
