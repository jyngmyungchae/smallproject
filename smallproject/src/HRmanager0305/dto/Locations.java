package HRmanager0305.dto;

public class Locations {
    int location_id;
    String street_address;
    String postal_code;
    String city;
    String state_province;
    String country_id;

    public static LocationsBuilder builder() {
        return new LocationsBuilder();
    }

    public static class LocationsBuilder {
        private int location_id;
        private String street_address = null;
        private String postal_code = null;
        private String city;
        private String state_province = null;
        private String country_id;

        public LocationsBuilder location_id(int location_id) {
            this.location_id = location_id;
            return this;
        }

        public LocationsBuilder street_address(String street_address) {
            this.street_address = street_address;
            return this;
        }

        public LocationsBuilder postal_code(String postal_code) {
            this.postal_code = postal_code;
            return this;
        }

        public LocationsBuilder city(String city) {
            this.city = city;
            return this;
        }

        public LocationsBuilder state_province(String state_province) {
            this.state_province = state_province;
            return this;
        }

        public LocationsBuilder country_id(String country_id) {
            this.country_id = country_id;
            return this;
        }

        public Locations build() {
            Locations location = new Locations();
            location.location_id = this.location_id;
            location.street_address = this.street_address;
            location.postal_code = this.postal_code;
            location.city = this.city;
            location.state_province = this.state_province;
            location.country_id = this.country_id;
            return location;
        }
    }
}