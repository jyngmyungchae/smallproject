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

        System.out.print("korean: ");
        int korean = scanner.nextInt();
        System.out.print("english: ");
        int english = scanner.nextInt();
        System.out.print("math: ");
        int math = scanner.nextInt();
        System.out.print("science: ");
        int science = scanner.nextInt();

        Student student = new Student.StudentBuilder()
                .sno(sno)
                .name(name)
                .korean(korean)
                .english(english)
                .math(math)
                .science(science)
                .build();

        students.add(student);
        System.out.println("success");
        run();
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
