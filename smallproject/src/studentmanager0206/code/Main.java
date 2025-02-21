package studentmanager0206.code;
/**
 * Main 클래스는 애플리케이션의 진입점입니다.
 * StudentManager 객체를 생성하고 실행합니다.
 */
public class Main {
    /**
     * 프로그램의 시작점입니다.
     * @param args 명령행 인자
     */
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.run();
    }
}