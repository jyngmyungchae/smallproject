package board0221;

import board0221.controller.BoardManager;

/**
 * {@code Main} 클래스는 게시판 애플리케이션을 실행하는 진입점(entry point)입니다.
 * <p>
 * {@code BoardManager} 객체를 생성하고 실행하여 게시판 기능을 시작합니다.
 */
public class Main {
    /**
     * 프로그램의 진입점(Main 메서드)입니다.
     * <p>
     * {@code BoardManager} 객체를 생성하고 {@code run()} 메서드를 호출하여
     * 게시판 프로그램을 실행합니다.
     *
     * @param args 실행 시 전달되는 명령줄 인자 (사용되지 않음)
     */
    public static void main(String[] args) {
        BoardManager manager = new BoardManager();
        manager.run();
    }
}