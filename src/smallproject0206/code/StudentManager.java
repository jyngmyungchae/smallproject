package smallproject0206.code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * StudentManager 클래스는 학생 정보를 관리하는 메뉴 기반의 애플리케이션을 구현합니다.
 * 데이터 입력, 출력, 검색, 정렬, 백업, 삭제 등의 기능을 제공합니다.
 */
public class StudentManager extends StudentDBIO {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();
    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
    private final Map<Integer, Runnable> menuChoice = new HashMap<>();

    /**
     * StudentManager 생성자.
     * 메뉴 초기화를 수행합니다.
     */
    public StudentManager() {
        mainMenu();
    }

    /**
     * 메인 메뉴와 각 메뉴 번호에 대응하는 작업을 설정합니다.
     */
    private void mainMenu() {
        menuChoice.put(1, this::inputStudent);
        menuChoice.put(2, this::outputStudent);
        menuChoice.put(3, this::searchBySno);
        menuChoice.put(4, this::sortStudents);
        menuChoice.put(5, this::backupToFile);
        menuChoice.put(6, this::exitApp);
    }

    /**
     * 메뉴를 화면에 출력합니다.
     */
    private void printMenu() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. sort student info");
        System.out.println("5. backup to file");
        System.out.println("6. exit");
        System.out.println("choice menu");
    }

    /**
     * 사용자 입력을 통해 메뉴를 실행합니다.
     */
    public void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }
            Runnable action = menuChoice.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("재입력");
            }
        }
    }

    /**
     * 사용자 입력에 대해 정규식 검증을 수행합니다.
     * @param prompt 입력 프롬프트
     * @param pattern 검증할 정규식 패턴
     * @param errorMessage 에러 메시지
     * @return 검증된 문자열 입력
     */
    private String readValidatedString(String prompt, Pattern pattern, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * 사용자 입력에 대해 정수값 범위 검증을 수행합니다.
     * @param prompt 입력 프롬프트
     * @param min 최소값
     * @param max 최대값
     * @return 검증된 정수값
     */
    private int readValidatedInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(min + "~" + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    private int[] readSubjectScores() {
        int korean = readValidatedInt("korean: ", 0, 100);
        int english = readValidatedInt("english: ", 0, 100);
        int math = readValidatedInt("math: ", 0, 100);
        int science = readValidatedInt("science: ", 0, 100);
        return new int[] { korean, english, math, science };
    }

    private Student createStudent(String sno, String name, int korean, int english, int math, int science) {
        return new Student.StudentBuilder()
                .sno(sno)
                .name(name)
                .addSubject("korean", korean)
                .addSubject("english", english)
                .addSubject("math", math)
                .addSubject("science", science)
                .build();
    }

    /**
     * 학생 정보를 입력받아 추가하거나, 기존 정보가 있으면 수정합니다.
     */
    @Override
    public void inputStudent() {
        System.out.println("add");
        String sno = readValidatedString(
                "sno (10자리수): ", SNO_PATTERN, "정확히 10자리 수 재입력");

        Student existingStudent = studentDAO.findStudentBySno(sno);
        if (existingStudent == null) {
            String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
            int[] scores = readSubjectScores();
            Student student = createStudent(sno, name, scores[0], scores[1], scores[2], scores[3]);

            studentDAO.save(student);
            students.add(student);
        } else {
            System.out.println("already regist sno, student name: " + existingStudent.getName());
            System.out.println("1.edit all info");
            System.out.println("2.edit subject score");
            System.out.println("3.exit add");
            String option = scanner.nextLine().trim();
            if ("1".equals(option)) {
                String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
                int[] scores = readSubjectScores();
                Student student = createStudent(sno, name, scores[0], scores[1], scores[2], scores[3]);

                studentDAO.updateStudent(student);
                updateInMemoryStudent(student);
            } else if ("2".equals(option)) {
                int[] scores = readSubjectScores();
                Student student = createStudent(sno, existingStudent.getName(), scores[0], scores[1], scores[2], scores[3]);

                studentDAO.updateStudentScores(student);
                updateInMemoryStudent(student);
            } else if ("3".equals(option)){
                System.out.println("exit");
                return;
            } else {
                System.out.println("잘못된 입력");
            }
        }
        System.out.println("success");
    }

    /**
     * 메모리 내 학생 리스트를 업데이트합니다.
     * @param student 업데이트할 학생 객체
     */
    private void updateInMemoryStudent(Student student) {
        students.removeIf(s -> s.getSno().equals(student.getSno()));
        students.add(student);
    }

    /**
     * 데이터베이스의 학생 정보를 출력하고 삭제 옵션을 제공합니다.
     */
    @Override
    public void outputStudent() {
        studentDAO.getAllStudents().forEach(System.out::println);
        deleteStudentInfo();
    }

    /**
     * 삭제할 학생의 학번을 입력받아 삭제 작업을 수행합니다.
     */
    public void deleteStudentInfo() {
        System.out.print("삭제할 학생의 학번을 입력 (삭제하지 않으려면 그냥 엔터): ");
        String deleteSno = scanner.nextLine().trim();

        if (!deleteSno.isEmpty()) {
            deleteStudent(deleteSno);
        } else {
            System.out.println("삭제 없이 종료합니다");
        }
    }

    /**
     * 데이터베이스와 메모리에서 해당 학번의 학생 정보를 삭제합니다.
     * @param sno 삭제할 학생의 학번
     */
    private void deleteStudent(String sno) {
        studentDAO.delete(sno);
        students.removeIf(s -> s.getSno().equals(sno));
        System.out.println("삭제 완료");
    }

    /**
     * 학번을 기준으로 학생 정보를 검색합니다.
     */
    @Override
    public void searchBySno() {
        System.out.print("enter (sno 기준검색) :");
        String searchSno = scanner.nextLine().trim();

        Student foundStudent = studentDAO.findStudentBySno(searchSno);

        if (foundStudent == null) {
            System.out.println("no " + searchSno);
        } else {
            System.out.println("result");
            System.out.println(foundStudent);
        }
    }

    /**
     * 정렬 옵션에 따라 학생 정보를 정렬합니다.
     */
    public void sortStudents() {
        System.out.println("select");
        System.out.println("1. Sort by total score ");
        System.out.println("2. Sort by sno ");
        String option = scanner.nextLine().trim();

        int sortChoice;
        try {
            sortChoice = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력");
            return;
        }

        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("DB에 저장된 학생 데이터가 없습니다.");
            return;
        }

        Map<Integer, Runnable> sortActions = new HashMap<>();
        sortActions.put(1, () -> sortByTotal(studentList));
        sortActions.put(2, () -> sortBySno(studentList));

        Runnable sortAction = sortActions.get(sortChoice);
        if (sortAction == null) {
            System.out.println("잘못된 입력");
            return;
        }
        sortAction.run();

        System.out.println("Sorted Students:");
        studentList.forEach(System.out::println);
    }

    /**
     * 학생 리스트를 총점 기준 내림차순으로 정렬합니다.
     * @param studentList 정렬할 학생 리스트
     */
    @Override
    public void sortByTotal(List<Student> studentList) {
        studentList.sort(Comparator.comparingInt(Student::getTotal).reversed());
        System.out.println("Sorted by total score (descending):");
    }

    /**
     * 학생 리스트를 학번 기준으로 정렬합니다.
     * @param studentList 정렬할 학생 리스트
     */
    @Override
    public void sortBySno(List<Student> studentList) {
        studentList.sort(Comparator.comparing(Student::getSno));
        System.out.println("Sorted by sno:");
    }

    /**
     * 데이터베이스의 학생 데이터를 백업 파일로 저장합니다.
     */
    private void backupToFile() {
        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("백업할 데이터가 없습니다.");
            return;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "students_backup_" + timestamp + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String header = "sno,name,korean,english,math,science,total,average,grade";
            writer.write(header);
            writer.newLine();

            for (Student s : studentList) {
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
            System.out.println("파일 백업 완료: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 과목의 점수를 반환하는 헬퍼 메서드.
     * @param student 학생 객체
     * @param subjectName 과목명
     * @return 과목 점수, 없으면 0 반환
     */
    private int getSubjectScore(Student student, String subjectName) {
        return student.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(subjectName))
                .mapToInt(Student.Subject::getScore)
                .findFirst()
                .orElse(0);
    }

    /**
     * 애플리케이션을 종료합니다.
     */
    private void exitApp() {
        System.exit(0);
    }
}