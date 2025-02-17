package smallproject0206.code;

import java.util.ArrayList;
import java.util.List;

/**
 * Student 클래스는 학생 정보를 저장하며, 빌더 패턴을 사용하여 객체를 생성합니다.
 * 과목 관련 데이터는 Subject라는 내부 클래스로 캡슐화하여 관리됩니다.
 */
public class Student {

    private String sno;
    private String name;
    private List<Subject> subjects;

    /**
     * Student 생성자는 StudentBuilder를 통해 생성됩니다.
     * @param builder 학생 객체 빌더
     */
    private Student(StudentBuilder builder) {
        this.sno = builder.sno;
        this.name = builder.name;
        this.subjects = builder.subjects;
    }

    /**
     * 학생의 이름을 반환합니다.
     * @return 학생 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 학생의 과목 리스트를 반환합니다.
     * @return 과목 리스트
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * 학생의 총 점수를 계산합니다.
     * @return 총 점수
     */
    public int getTotal() {
        int total = 0;
        for (Subject subject : subjects) {
            total += subject.getScore();
        }
        return total;
    }

    /**
     * 학생의 평균 점수를 계산합니다.
     * @return 평균 점수
     */
    public double getAverage() {
        return subjects.isEmpty() ? 0 : getTotal() / (double) subjects.size();
    }

    /**
     * 학생의 평균 점수에 따른 학점을 계산합니다.
     * @return 학점 (A, B, C, D, F)
     */
    public String computeGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    /**
     * 학생 정보를 문자열로 반환합니다.
     * @return 학생 정보 문자열
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sno='").append(sno).append("', ");
        sb.append("name='").append(name).append("', ");
        sb.append("subjects=").append(subjects).append(", ");
        sb.append("total=").append(getTotal()).append(", ");
        sb.append("average=").append(getAverage()).append(", ");
        sb.append("grade=").append(computeGrade());
        return sb.toString();
    }

    /**
     * Subject 클래스는 학생의 과목 정보(과목명과 점수)를 표현합니다.
     */
    public static class Subject {
        private String name;
        private int score;

        /**
         * Subject 생성자
         * @param name 과목명
         * @param score 점수
         */
        public Subject(String name, int score) {
            this.name = name;
            this.score = score;
        }

        /**
         * 과목명을 반환합니다.
         * @return 과목명
         */
        public String getName() {
            return name;
        }

        /**
         * 과목 점수를 반환합니다.
         * @return 점수
         */
        public int getScore() {
            return score;
        }

        /**
         * 과목 정보를 문자열로 반환합니다.
         * @return 과목 정보 문자열
         */
        @Override
        public String toString() {
            return name + ":" + score;
        }
    }

    /**
     * StudentBuilder 클래스는 빌더 패턴을 통해 Student 객체를 생성합니다.
     */
    public static class StudentBuilder {
        private String sno;
        private String name;
        private List<Subject> subjects = new ArrayList<>();

        /**
         * 학생의 학번을 설정합니다.
         * @param sno 학번
         * @return StudentBuilder 객체
         */
        public StudentBuilder sno(String sno) {
            this.sno = sno;
            return this;
        }

        /**
         * 학생의 이름을 설정합니다.
         * @param name 이름
         * @return StudentBuilder 객체
         */
        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * 학생의 과목과 점수를 추가합니다.
         * @param subjectName 과목명
         * @param score 점수
         * @return StudentBuilder 객체
         */
        public StudentBuilder addSubject(String subjectName, int score) {
            this.subjects.add(new Subject(subjectName, score));
            return this;
        }

        /**
         * Student 객체를 생성하여 반환합니다.
         * @return 생성된 Student 객체
         */
        public Student build() {
            return new Student(this);
        }
    }

    /**
     * 학생의 학번을 반환합니다.
     * @return 학번
     */
    public String getSno() {
        return sno;
    }

}
