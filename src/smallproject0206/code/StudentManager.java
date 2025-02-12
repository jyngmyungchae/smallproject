package smallproject0206.code;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentManager extends StudentDBIO {

    Scanner scanner = new Scanner(System.in);

    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{6}$");  // 학번은 6자리 숫자
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$"); // 한글 및 영문 허용

    public void run() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. exit");
        System.out.println("choice menu");

        int menu = scanner.nextInt();
        scanner.nextLine();  // 개행 문자 처리
        switch (menu) {
            case 1:
                inputStudent();
                break;
            case 2:
                outputStudent();
                break;
            case 3:
                searchStudentByName();
                break;
            case 4:
                return;
            default:
                System.out.println("재입력");

        }
    }

    @Override
    public void inputStudent() {
        System.out.println("add");

        String sno;
        do {
            System.out.print("sno (6자리수): ");
            sno = scanner.next();
            if (!SNO_PATTERN.matcher(sno).matches()) {
                System.out.println("정확히 6자리 수 재입력");
            }
        } while (!SNO_PATTERN.matcher(sno).matches());

        String name;
        do {
            System.out.print("name (한, 영): ");
            name = scanner.next();
            if (!NAME_PATTERN.matcher(name).matches()) {
                System.out.println("한, 영문으로 재입력");
            }
        } while (!NAME_PATTERN.matcher(name).matches());

        // StudentBuilder를 사용해 학생 정보를 생성
        Student.StudentBuilder builder = new Student.StudentBuilder()
                .sno(sno)
                .name(name);

        // 과목별 점수를 입력받아 추가
        builder.addSubject("korean", getValidatedScore("korean"));
        builder.addSubject("english", getValidatedScore("english"));
        builder.addSubject("math", getValidatedScore("math"));
        builder.addSubject("science", getValidatedScore("science"));

        // 빌드 후 students 리스트에 추가
        students.add(builder.build());

        System.out.println("success");
        run();
    }
    private int getValidatedScore(String subjectName) {
        int score;
        do {
            System.out.print(subjectName + ": ");
            score = scanner.nextInt();
            if (score < 0 || score > 100) {
                System.out.println("점수는 0에서 100 사이여야 합니다. 다시 입력하세요.");
            }
        } while (score < 0 || score > 100);
        return score;
    }

    @Override
    public void outputStudent() {
        for (Student student : students) {
            System.out.println(student);
        }
        run();
    }

    @Override
    public void searchStudentByName() {
        System.out.print("enter (이름기준검색) :");
        String searchName = scanner.nextLine().trim();

        List<Student> foundStudents = students.stream()
                .filter(s -> containsIgnoreCase(s.getName(), searchName)) // 부분 검색 (대소문자 구분 없이)
                .collect(Collectors.toList());

        if (foundStudents.isEmpty()) {
            System.out.println("no" + searchName);
        } else {
            System.out.println("result");
            for (Student student : foundStudents) {
                System.out.println(student);
            }
        }
        System.out.println();
        run();
    }

    private boolean containsIgnoreCase(String name, String searchName) {
        return name.toLowerCase().contains(searchName.toLowerCase());
    }

}