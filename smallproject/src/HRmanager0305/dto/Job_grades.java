package HRmanager0305.dto;

public class Job_grades {
    String grade_level;
    int lowest_sal;
    int highest_sal;

    public static Job_gradesBuilder builder() {
        return new Job_gradesBuilder();
    }

    public static class Job_gradesBuilder {
        private String grade_level;
        private int lowest_sal;
        private int highest_sal;

        public Job_gradesBuilder grade_level(String grade_level) {
            this.grade_level = grade_level;
            return this;
        }

        public Job_gradesBuilder lowest_sal(int lowest_sal) {
            this.lowest_sal = lowest_sal;
            return this;
        }

        public Job_gradesBuilder highest_sal(int highest_sal) {
            this.highest_sal = highest_sal;
            return this;
        }

        public Job_grades build() {
            Job_grades jobGrade = new Job_grades();
            jobGrade.grade_level = this.grade_level;
            jobGrade.lowest_sal = this.lowest_sal;
            jobGrade.highest_sal = this.highest_sal;
            return jobGrade;
        }
    }
}