package HRmanager0305.dto;

public class Departments {
    int department_id;
    String department_name;
    int manager_id;
    int location_id;

    public static DepartmentsBuilder builder() {
        return new DepartmentsBuilder();
    }

    public static class DepartmentsBuilder {
        private int department_id;
        private String department_name;
        private int manager_id = 0;
        private int location_id = 0;

        public DepartmentsBuilder department_id(int department_id) {
            this.department_id = department_id;
            return this;
        }

        public DepartmentsBuilder department_name(String department_name) {
            this.department_name = department_name;
            return this;
        }

        public DepartmentsBuilder manager_id(int manager_id) {
            this.manager_id = manager_id;
            return this;
        }

        public DepartmentsBuilder location_id(int location_id) {
            this.location_id = location_id;
            return this;
        }

        public Departments build() {
            Departments department = new Departments();
            department.department_id = this.department_id;
            department.department_name = this.department_name;
            department.manager_id = this.manager_id;
            department.location_id = this.location_id;
            return department;
        }
    }
}