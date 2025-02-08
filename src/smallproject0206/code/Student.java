package smallproject0206.code;

public class Student {

    private String sno;
    private String name;
    private int korean;
    private int english;
    private int math;
    private int science;
    private int total;
//    private double average;
//    private String grade;

    public Student(String sno, String name, int korean, int english, int math, int science) {
        this.sno = sno;
        this.name = name;
        this.korean = korean;
        this.english = english;
        this.math = math;
        this.science = science;
    }


    @Override
    public String toString() {
        return "Student{" +
                "sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", korean=" + korean +
                ", english=" + english +
                ", math=" + math +
                ", science=" + science +
                '}';
    }
}

