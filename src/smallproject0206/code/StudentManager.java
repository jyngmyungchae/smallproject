package smallproject0206.code;

import java.util.*;
import java.util.regex.Pattern;

public class StudentManager extends StudentDBIO {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();
    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
    private final Map<Integer, Runnable> menuChoice = new HashMap<>();

    //객체 생성할때 mainMenu()호출해서 동작을 초기화함
    //메인메서드에서 객체 생성하구 manager.run()실행 --> mainmenu()와 run()이 결합하는 느낌
    public StudentManager() {
        mainMenu();
    }
    // 각 메뉴 번호와 실행할 작업을 menuChoice 맵에 등록
    private void mainMenu() {
        menuChoice.put(1, () -> this.inputStudent()); //람다
        menuChoice.put(2, this::outputStudent); //메서드 참조
        menuChoice.put(3, this::searchBySno);
        menuChoice.put(4, this::sortStudents);
        menuChoice.put(5, this::exitApp);
    }

    private void printMenu() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. sort student info");
        System.out.println("5. exit");
        System.out.println("choice menu");
    }
    // printmenu(), 사용자 입력받고 해당 메뉴 실행하는 작업수행
    public void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim(); // 입력받고

            int choice;

            try {
                choice = Integer.parseInt(input); // string -> int 파싱
                /* str입력받고 int로 파싱할거면 int로 입력받으면 안되나요? 예외처리가 까다로워짐 */
            } catch (NumberFormatException e) { //잘못된 입력 잡아내기
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }
            Runnable action = menuChoice.get(choice); // 입력값 대응 Runnable
            if (action != null) {
                action.run();
            } else {
                System.out.println("재입력");
            }
        }
    }

    // 입력 검증 메서드
    // 기존에 input()에서 조건문 통해 패턴 검사하는 방식이었는데 분리함
    private String readValidatedString(String prompt, Pattern pattern, String errorMessage) {
        while (true) {
            System.out.print(prompt); // 프롬프트 출력
            String input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) { // 정규식 패턴 검사
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

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

    // 1. add menu
    // 신규 -> 정보입력, 기존 등록 -> edit all or edit scroe or exit 선택
    @Override
    public void inputStudent() {
        System.out.println("add");
        String sno = readValidatedString(
                "sno (10자리수): ", SNO_PATTERN, "정확히 10자리 수 재입력");

        Student existingStudent = studentDAO.findStudentBySno(sno); // sno 입력값이 db에 존재하는지 확인하는 메서드 호출
        if (existingStudent == null) { //존재하지 않으면
            String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
            int korean = readValidatedInt("korean: ", 0, 100);
            int english = readValidatedInt("english: ", 0, 100);
            int math = readValidatedInt("math: ", 0, 100);
            int science = readValidatedInt("science: ", 0, 100);

            //빌더 패턴 통한 객체 생성
            Student student = new Student.StudentBuilder()
                    .sno(sno)
                    .name(name)
                    .addSubject("korean", korean)
                    .addSubject("english", english)
                    .addSubject("math", math)
                    .addSubject("science", science)
                    .build();
            studentDAO.save(student);
            students.add(student);
        } else {
            //이미 등록된 학번은 수정
            System.out.println("already regist sno: " + existingStudent.getName());
            System.out.println("1.edit all info");
            System.out.println("2.edit subject score");
            System.out.println("3.exit add");
            String option = scanner.nextLine().trim();
            if ("1".equals(option)) {
                String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
                int korean = readValidatedInt("korean: ", 0, 100);
                int english = readValidatedInt("english: ", 0, 100);
                int math = readValidatedInt("math: ", 0, 100);
                int science = readValidatedInt("science: ", 0, 100);

                Student student = new Student.StudentBuilder()
                        .sno(sno)
                        .name(name)
                        .addSubject("korean", korean)
                        .addSubject("english", english)
                        .addSubject("math", math)
                        .addSubject("science", science)
                        .build();
                studentDAO.updateStudent(student);
                updateInMemoryStudent(student);
            } else if ("2".equals(option)) {
                int korean = readValidatedInt("korean: ", 0, 100);
                int english = readValidatedInt("english: ", 0, 100);
                int math = readValidatedInt("math: ", 0, 100);
                int science = readValidatedInt("science: ", 0, 100);

                Student student = new Student.StudentBuilder()
                        .sno(sno)
                        .name(existingStudent.getName())
                        .addSubject("korean", korean)
                        .addSubject("english", english)
                        .addSubject("math", math)
                        .addSubject("science", science)
                        .build();
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

    // 메모리 내 리스트 업데이트 (동일 학번 학생 제거 후 새 객체 추가)
    private void updateInMemoryStudent(Student student) {
        students.removeIf(s -> s.getSno().equals(student.getSno()));
        students.add(student);
    }

    //2. view menu
    //db에 저장된 학생정보를 불러와서 출력하고 delete메서드 호출
    @Override
    public void outputStudent() {
        studentDAO.getAllStudents().forEach(System.out::println);
        deleteStudentInfo();
    }

    //삭제 메뉴 메서드
    public void deleteStudentInfo() {
        System.out.print("삭제할 학생의 학번을 입력 (삭제하지 않으려면 그냥 엔터): ");
        String deleteSno = scanner.nextLine().trim();

        if (!deleteSno.isEmpty()) {
            deleteStudent(deleteSno);
        } else {
            System.out.println("삭제 없이 종료합니다");
        }
    }

    //db와 메모리 데이터 삭제
    private void deleteStudent(String sno) {
        studentDAO.delete(sno);
        students.removeIf(s -> s.getSno().equals(sno)); //removeIf사용해서 조건에 맞는 객체 삭제
        System.out.println("삭제 완료");
    }

    // 3.search menu
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

    // 4. sort menu
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

        List<Student> studentList = studentDAO.getAllStudents(); //입력된 sno 학생 정보를 List<Student> 로 불러옴
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
    //정렬 기능 수행하는 메서드
    @Override
    public void sortByTotal(List<Student> studentList) {
        studentList.sort(Comparator.comparingInt(Student::getTotal).reversed());
        System.out.println("Sorted by total score (descending):");
    }
    @Override
    public void sortBySno(List<Student> studentList) {
        studentList.sort(Comparator.comparing(Student::getSno));
        System.out.println("Sorted by sno:");
    }

    private void exitApp() {
        System.exit(0); //jvm 종료 표준 메서드
    }
}