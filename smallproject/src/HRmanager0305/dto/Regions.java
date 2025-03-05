package HRmanager0305.dto;

public class Regions {
    int region_id;
    String region_name;

    public static RegionsBuilder builder() {
        return new RegionsBuilder();
    }

    public static class RegionsBuilder {
        private int region_id;
        private String region_name = null;

        public RegionsBuilder region_id(int region_id) {
            this.region_id = region_id;
            return this;
        }

        public RegionsBuilder region_name(String region_name) {
            this.region_name = region_name;
            return this;
        }

        public Regions build() {
            Regions region = new Regions();
            region.region_id = this.region_id;
            region.region_name = this.region_name;
            return region;
        }
    }
}