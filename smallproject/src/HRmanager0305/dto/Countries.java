
package HRmanager0305.dto;

public class Countries {
    String country_id;
    String country_name;
    int region_id;

    public static CountriesBuilder builder() {
        return new CountriesBuilder();
    }

    public static class CountriesBuilder {
        private String country_id;
        private String country_name = null;
        private int region_id;

        public CountriesBuilder country_id(String country_id) {
            this.country_id = country_id;
            return this;
        }

        public CountriesBuilder country_name(String country_name) {
            this.country_name = country_name;
            return this;
        }

        public CountriesBuilder region_id(int region_id) {
            this.region_id = region_id;
            return this;
        }

        public Countries build() {
            Countries country = new Countries();
            country.country_id = this.country_id;
            country.country_name = this.country_name;
            country.region_id = this.region_id;
            return country;
        }
    }
}