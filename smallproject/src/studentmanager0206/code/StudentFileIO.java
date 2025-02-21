package studentmanager0206.code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentFileIO 클래스는 파일 기반의 학생 I/O 기능을 구현합니다.
 * 단일 인스턴스 패턴(Singleton)을 사용하여 인스턴스를 관리합니다.
 */
public class StudentFileIO extends StudentDBIO {
    /**
     * 단일 인스턴스를 보관하는 static final 필드
     */
    private static final StudentFileIO INSTANCE = new StudentFileIO();

    /**
     * 학생 백업 파일 경로 (CSV 형식)
     */
    private static final String FILE_PATH = "students_backup.csv";

    /**
     * private 생성자: 외부에서 인스턴스 생성 불가
     */
    private StudentFileIO() {
    }

    /**
     * 단일 인스턴스를 반환합니다.
     * @return StudentFileIO 인스턴스
     */
    public static StudentFileIO getInstance() {
        return INSTANCE;
    }

    /**
     * 학생 정보를 CSV 형식으로 파일에 저장합니다.
     * @param student 저장할 학생 객체
     */
    @Override
    public void save(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                    student.getSno(),
                    student.getName(),
                    getSubjectScore(student, "korean"),
                    getSubjectScore(student, "english"),
                    getSubjectScore(student, "math"),
                    getSubjectScore(student, "science"),
                    student.getTotal(),
                    student.getAverage(),
                    student.computeGrade());
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일에서 모든 학생 정보를 읽어와 리스트로 반환합니다.
     * @return 학생 리스트
     */
    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // CSV 형식: sno,name,korean,english,math,science,total,average,grade
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    String sno = tokens[0];
                    String name = tokens[1];
                    int korean = Integer.parseInt(tokens[2]);
                    int english = Integer.parseInt(tokens[3]);
                    int math = Integer.parseInt(tokens[4]);
                    int science = Integer.parseInt(tokens[5]);
                    Student student = new Student.StudentBuilder()
                            .sno(sno)
                            .name(name)
                            .addSubject("korean", korean)
                            .addSubject("english", english)
                            .addSubject("math", math)
                            .addSubject("science", science)
                            .build();
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * 파일에서 특정 학번의 학생 정보를 삭제합니다.
     * 전체 파일을 재작성합니다.
     * @param sno 삭제할 학생의 학번
     */
    @Override
    public void delete(String sno) {
        List<Student> studentList = getAllStudents();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : studentList) {
                if (!s.getSno().equals(sno)) {
                    String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                            s.getSno(),
                            s.getName(),
                            getSubjectScore(s, "korean"),
                            getSubjectScore(s, "english"),
                            getSubjectScore(s, "math"),
                            getSubjectScore(s, "science"),
                            s.getTotal(),
                            s.getAverage(),
                            s.computeGrade());
                    writer.write(csvLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Student 객체에서 특정 과목 점수를 추출합니다.
     * @param student 학생 객체
     * @param subjectName 과목명
     * @return 해당 과목의 점수, 없으면 0 반환
     */
    private int getSubjectScore(Student student, String subjectName) {
        return student.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(subjectName))
                .mapToInt(Student.Subject::getScore)
                .findFirst()
                .orElse(0);
    }
}
