package smallproject0206.code;

public class Student {


    private String sno;
    private String name;
    private int korean;
    private int english;
    private int math;
    private int science;


    private Student(StudentBuilder builder) {
        this.sno = builder.sno;
        this.name = builder.name;
        this.korean = builder.korean;
        this.english = builder.english;
        this.math = builder.math;
        this.science = builder.science;
    }

    public String getName() {
        return name;
    }

    public static class StudentBuilder {
        private String sno;
        private String name;
        private int korean;
        private int english;
        private int math;
        private int science;

        public StudentBuilder sno(String sno) {
            this.sno = sno;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder korean(int korean) {
            this.korean = korean;
            return this;
        }

        public StudentBuilder english(int english) {
            this.english = english;
            return this;
        }

        public StudentBuilder math(int math) {
            this.math = math;
            return this;
        }

        public StudentBuilder science(int science) {
            this.science = science;
            return this;
        }

        public Student build() {
            return new Student(this);
        }


    }
    public int getTotal() {
        return korean + english + math + science;
    }

    public double getAverage() {
        return getTotal() / 4.0;
    }

    public String computeGrade() {
        if (getAverage() >= 90) return "A";
        else if (getAverage() == 80) return "B";
        else if (getAverage() == 70) return "D";
        else if (getAverage() == 60) return "D";
        else return "F";
    }


    @Override
    public String toString() {
        return  "sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", korean=" + korean +
                ", english=" + english +
                ", math=" + math +
                ", science=" + science +
                ", total=" + getTotal()+
                ", average=" + getAverage()+
                ", grade=" + computeGrade();
    }

}
