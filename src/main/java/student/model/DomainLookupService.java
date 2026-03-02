package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DomainLookupService {

    /**
     * This method will look up a domain on the internet by hostname. 
     * Returns a Domain object if found.
     * @param hostname
     * @return domain
     * @throws Exception if anything does not work.
     */
    public Domain lookup(String hostname) throws Exception {
        try {
            // use InetAddress to get addess
            InetAddress address = InetAddress.getByName(hostname);
            // extract IP from InetAddress
            String ip = address.getHostAddress();
            // call ipapi with ip to get a url to a JSON object
            URL url = new URL("https://ipapi.co/" + ip + "/json/");
            // create a connection method
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // create a scanner to read the connection as a stream
            Scanner scanner = new Scanner(conn.getInputStream());
            // save the scanner information as a stream
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            // create an object mapper to map the String response to a JSON Node
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
            // now we can extract information from fields

            String city = node.get("city").asText();
            String region = node.get("region").asText();
            String country = node.get("country_code").asText();
            String postal = node.get("postal").asText();
            double latitude = node.get("latitude").asDouble();
            double longitude = node.get("longitude").asDouble();
            
            return new Domain(hostname, ip, city, region, country, postal, 
                        latitude, longitude);
        } catch (Exception e) {
            throw new Exception(String.format(
                                    "Error looking up %s: %s", 
                                        hostname, e.getMessage()));
        }
    }
}