import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.InputStream;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;
import student.model.formatters.Formats;
import student.model.net.NetUtils;

/**
 * Public tests for all methods in the NetUtils class.
 * 
 * Uses Junit Jupyter tests
 * 
 * NOTE: many of these tests will actually fail, as servers will switch depending on your local area
 * (google for example keeps servers in denver to enable quicker access). As this is just the
 * initial server location, it may vary based on where you are in the US. However, it should still
 * give you an idea of how it is working!
 */
public class TestNetUtils {

    /**
     * Tests the getApiUrl method.
     */
    @Test
    public void testGetApiUrl() {
        String ip = "11.11.11.12";
        String expected = "https://ipapi.co/11.11.11.12/xml/";
        String actual = NetUtils.getApiUrl(ip);

        assertEquals(expected, actual);

        expected = "https://ipapi.co/11.11.11.12/json/";
        actual = NetUtils.getApiUrl(ip, Formats.JSON);

        assertEquals(expected, actual);
    }

    /**
     * Tests the lookUpIp method.
     */
    @Test
    public void testLookUpIp() {
        String hostname = "google.com";
        String expected = "142.250.72.78";
        try {
            String actual = NetUtils.lookUpIp(hostname);
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // now try the exception case
        hostname = "www.google.commmmm";
        try {
            NetUtils.lookUpIp(hostname);
        } catch (Exception e) {
            assertEquals(UnknownHostException.class, e.getClass());
        }
    }


    /**
     * Test get URL contents
     */
    @Test
    public void testGetUrlContents() {
        String ip = "142.250.72.78";
        String expected = """
                <?xml version="1.0" encoding="utf-8"?>
                <root>
                <ip>142.250.72.78</ip>
                <network>142.250.64.0/19</network>
                <version>IPv4</version>
                <city>Plainview</city>
                <region>New York</region>
                <region_code>NY</region_code>
                <country>US</country>
                <country_name>United States</country_name>
                <country_code>US</country_code>
                <country_code_iso3>USA</country_code_iso3>
                <country_capital>Washington</country_capital>
                <country_tld>.us</country_tld>
                <continent_code>NA</continent_code>
                <in_eu>False</in_eu>
                <postal>11803</postal>
                <latitude>40.774600</latitude>
                <longitude>-73.476100</longitude>
                <timezone>America/New_York</timezone>
                <utc_offset>-0400</utc_offset>
                <country_calling_code>+1</country_calling_code>
                <currency>USD</currency>
                <currency_name>Dollar</currency_name>
                <languages>en-US,es-US,haw,fr</languages>
                <country_area>9629091.0</country_area>
                <country_population>327167434</country_population>
                <asn>AS15169</asn>
                <org>GOOGLE</org>
                </root>""".replace("\n", "");
        try {
            InputStream ioStream = NetUtils.getUrlContents(NetUtils.getApiUrl(ip));
            String actual = new String(ioStream.readAllBytes()).replace("\n", "");
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Test get IP details
     */
    @Test
    public void testGetIpDetails() {
        String ip = "142.250.72.78";
        String expected = """
                <?xml version="1.0" encoding="utf-8"?>
                <root>
                <ip>142.250.72.78</ip>
                <network>142.250.64.0/19</network>
                <version>IPv4</version>
                <city>Plainview</city>
                <region>New York</region>
                <region_code>NY</region_code>
                <country>US</country>
                <country_name>United States</country_name>
                <country_code>US</country_code>
                <country_code_iso3>USA</country_code_iso3>
                <country_capital>Washington</country_capital>
                <country_tld>.us</country_tld>
                <continent_code>NA</continent_code>
                <in_eu>False</in_eu>
                <postal>11803</postal>
                <latitude>40.774600</latitude>
                <longitude>-73.476100</longitude>
                <timezone>America/New_York</timezone>
                <utc_offset>-0400</utc_offset>
                <country_calling_code>+1</country_calling_code>
                <currency>USD</currency>
                <currency_name>Dollar</currency_name>
                <languages>en-US,es-US,haw,fr</languages>
                <country_area>9629091.0</country_area>
                <country_population>327167434</country_population>
                <asn>AS15169</asn>
                <org>GOOGLE</org>
                </root>""".replace("\n", "");
        try {
            InputStream ioStream = NetUtils.getIpDetails(ip);
            String actual = new String(ioStream.readAllBytes()).replace("\n", "");
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test get IP details, using JSON format
     */
    @Test
    public void testGetIpDetailsJson() {
        String ip = "142.250.72.78";
        String expected = """
                {
                    "ip": "142.250.72.78",
                    "network": "142.250.64.0/19",
                    "version": "IPv4",
                    "city": "Plainview",
                    "region": "New York",
                    "region_code": "NY",
                    "country": "US",
                    "country_name": "United States",
                    "country_code": "US",
                    "country_code_iso3": "USA",
                    "country_capital": "Washington",
                    "country_tld": ".us",
                    "continent_code": "NA",
                    "in_eu": false,
                    "postal": "11803",
                    "latitude": 40.7746,
                    "longitude": -73.4761,
                    "timezone": "America/New_York",
                    "utc_offset": "-0400",
                    "country_calling_code": "+1",
                    "currency": "USD",
                    "currency_name": "Dollar",
                    "languages": "en-US,es-US,haw,fr",
                    "country_area": 9629091.0,
                    "country_population": 327167434,
                    "asn": "AS15169",
                    "org": "GOOGLE"
                }""".replace("\n", "");

        try

        {
            InputStream ioStream = NetUtils.getIpDetails(ip, Formats.JSON);
            String actual = new String(ioStream.readAllBytes()).replace("\n", "");
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
