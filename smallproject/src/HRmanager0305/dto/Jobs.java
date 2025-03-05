package HRmanager0305.dto;

import java.math.BigDecimal;

public class Jobs {
    String job_id;
    String job_title;
    BigDecimal min_salary;
    BigDecimal max_salary;

    public static JobsBuilder builder() {
        return new JobsBuilder();
    }

    public static class JobsBuilder {
        private String job_id;
        private String job_title;
        private BigDecimal min_salary = BigDecimal.ZERO;
        private BigDecimal max_salary = BigDecimal.ZERO;

        public JobsBuilder job_id(String job_id) {
            this.job_id = job_id;
            return this;
        }

        public JobsBuilder job_title(String job_title) {
            this.job_title = job_title;
            return this;
        }

        public JobsBuilder min_salary(BigDecimal min_salary) {
            this.min_salary = min_salary;
            return this;
        }

        public JobsBuilder max_salary(BigDecimal max_salary) {
            this.max_salary = max_salary;
            return this;
        }

        public Jobs build() {
            Jobs job = new Jobs();
            job.job_id = this.job_id;
            job.job_title = this.job_title;
            job.min_salary = this.min_salary;
            job.max_salary = this.max_salary;
            return job;
        }
    }
}