package HRmanager0305.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Job_history {
    int employee_id;
    Date start_date;
    Date end_date;
    String job_id;
    int department_id;

    public static Job_historyBuilder builder() {
        return new Job_historyBuilder();
    }

    public static class Job_historyBuilder {
        private int employee_id;
        private Date start_date;
        private Date end_date;
        private String job_id;
        private int department_id;

        public Job_historyBuilder employee_id(int employee_id) {
            this.employee_id = employee_id;
            return this;
        }

        public Job_historyBuilder start_date(Date start_date) {
            this.start_date = start_date;
            return this;
        }

        public Job_historyBuilder end_date(Date end_date) {
            this.end_date = end_date;
            return this;
        }

        public Job_historyBuilder job_id(String job_id) {
            this.job_id = job_id;
            return this;
        }

        public Job_historyBuilder department_id(int department_id) {
            this.department_id = department_id;
            return this;
        }

        public Job_history build() {
            Job_history jobHistory = new Job_history();
            jobHistory.employee_id = this.employee_id;
            jobHistory.start_date = this.start_date;
            jobHistory.end_date = this.end_date;
            jobHistory.job_id = this.job_id;
            jobHistory.department_id = this.department_id;
            return jobHistory;
        }
    }
}