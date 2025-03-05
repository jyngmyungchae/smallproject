package HRmanager0305.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class Employees {
    private int employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private Date hire_date;
    private String job_id;
    private BigDecimal salary;
    private BigDecimal commission;
    private int manager_id;
    private int department_id;
    private int perfScore; // 성과 평가 점수

    private String newValue;
    private String oldValue;
    private String updateField;

    public static EmployeesBuilder builder() {
        return new EmployeesBuilder();
    }

    // Getters & Setters
    public int getEmployee_id() { return employee_id; }
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    public Date getHire_date() { return hire_date; }
    public void setHire_date(Date hire_date) { this.hire_date = hire_date; }
    public String getJob_id() { return job_id; }
    public void setJob_id(String job_id) { this.job_id = job_id; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public BigDecimal getCommission() { return commission; }
    public void setCommission(BigDecimal commission) { this.commission = commission; }
    public int getManager_id() { return manager_id; }
    public void setManager_id(int manager_id) { this.manager_id = manager_id; }
    public int getDepartment_id() { return department_id; }
    public void setDepartment_id(int department_id) { this.department_id = department_id; }
    public int getPerfScore() { return perfScore; }
    public void setPerfScore(int perfScore) { this.perfScore = perfScore; }
    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    public String getUpdateField() { return updateField; }
    public void setUpdateField(String updateField) { this.updateField = updateField; }

    @Override
    public String toString() {
        return "Employees{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", hire_date=" + hire_date +
                ", job_id='" + job_id + '\'' +
                ", salary=" + salary +
                ", commission=" + commission +
                ", manager_id=" + manager_id +
                ", department_id=" + department_id +
                ", perfScore=" + perfScore +
                ", newValue='" + newValue + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", updateField='" + updateField + '\'' +
                '}';
    }

    public static class EmployeesBuilder {
        private int employee_id;
        private String first_name;
        private String last_name;
        private String email;
        private String phone_number;
        private Date hire_date;
        private String job_id;
        private BigDecimal salary;
        private BigDecimal commission;
        private int manager_id;
        private int department_id;
        private int perfScore;
        private String updateField;
        private String newValue;
        private String oldValue;

        public EmployeesBuilder employee_id(int employee_id) {
            this.employee_id = employee_id;
            return this;
        }
        public EmployeesBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }
        public EmployeesBuilder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }
        public EmployeesBuilder email(String email) {
            this.email = email;
            return this;
        }
        public EmployeesBuilder phone_number(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }
        public EmployeesBuilder hire_date(Date hire_date) {
            this.hire_date = hire_date;
            return this;
        }
        public EmployeesBuilder job_id(String job_id) {
            this.job_id = job_id;
            return this;
        }
        public EmployeesBuilder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }
        public EmployeesBuilder commission(BigDecimal commission) {
            this.commission = commission;
            return this;
        }
        public EmployeesBuilder manager_id(int manager_id) {
            this.manager_id = manager_id;
            return this;
        }
        public EmployeesBuilder department_id(int department_id) {
            this.department_id = department_id;
            return this;
        }
        public EmployeesBuilder perfScore(int perfScore) {
            this.perfScore = perfScore;
            return this;
        }
        public EmployeesBuilder updateField(String updateField) {
            this.updateField = updateField;
            return this;
        }
        public EmployeesBuilder newValue(String newValue) {
            this.newValue = newValue;
            return this;
        }
        public EmployeesBuilder oldValue(String oldValue) {
            this.oldValue = oldValue;
            return this;
        }
        public Employees build() {
            Employees employee = new Employees();
            employee.setEmployee_id(this.employee_id);
            employee.setFirst_name(this.first_name);
            employee.setLast_name(this.last_name);
            employee.setEmail(this.email);
            employee.setPhone_number(this.phone_number);
            employee.setHire_date(this.hire_date);
            employee.setJob_id(this.job_id);
            employee.setSalary(this.salary);
            employee.setCommission(this.commission);
            employee.setManager_id(this.manager_id);
            employee.setDepartment_id(this.department_id);
            employee.setPerfScore(this.perfScore);
            employee.setUpdateField(this.updateField);
            employee.setNewValue(this.newValue);
            employee.setOldValue(this.oldValue);
            return employee;
        }
    }
}