package model;

public record Domain(String hostname, String ip, String city, String region,
                     String country, String postal, double latitude, 
                     double longitude) {
    public Domain {
        if (hostname == null || hostname.isBlank())
            throw new IllegalArgumentException("Hostname cannot be null or empty");
        if (ip == null || ip.isBlank())
            throw new IllegalArgumentException("IP cannot be null or empty");
        if (city == null || city.isBlank())
            throw new IllegalArgumentException("City cannot be null or empty");
        if (region == null || region.isBlank())
            throw new IllegalArgumentException("Region cannot be null or empty");
        if (country == null || country.isBlank())
            throw new IllegalArgumentException("Country cannot be null or empty");
        if (postal == null || postal.isBlank())
            throw new IllegalArgumentException("Postal cannot be null or empty");
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
    }
}
