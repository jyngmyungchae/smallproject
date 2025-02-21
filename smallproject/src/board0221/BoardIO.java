package board0221;

import java.util.List;
import java.util.Scanner;

/**
 * BoardIO 클래스는 게시판 프로그램의 입출력을 담당합니다.
 * 사용자에게 메뉴를 표시하고, 입력을 받아 처리하며, 결과 메시지를 출력합니다.
 */
public class BoardIO {
    // 사용자 입력을 받기 위한 Scanner 객체 (표준 입력(System.in) 사용)
    private final Scanner scanner = new Scanner(System.in);

    /**
     * displayMenu 메서드는 콘솔에 메뉴 옵션을 출력합니다.
     * 메뉴 옵션은 Create, Read, Clear, Exit 네 가지로 구성됩니다.
     */
    public void displayMenu() {
        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Clear");
        System.out.println("4. Exit");
        System.out.print("Select menu: ");
    }

    /**
     * getMenuChoice 메서드는 사용자로부터 메뉴 선택 입력을 받아 정수로 반환합니다.
     * 입력값이 올바른 정수가 아닌 경우 -1을 반환하여 잘못된 입력을 처리합니다.
     *
     * @return 사용자 입력 메뉴 번호 (정수)
     */
    public int getMenuChoice() {
        String input = scanner.nextLine().trim(); // 입력받은 문자열에서 좌우 공백 제거
        try {
            return Integer.parseInt(input);  // 문자열을 정수로 변환
        } catch (NumberFormatException e) {
            // 입력값이 정수로 변환 불가능하면 -1 반환 (잘못된 입력)
            return -1;
        }
    }

    /**
     * prompt 메서드는 특정 메시지를 출력하고 사용자로부터 입력값을 받아 반환합니다.
     *
     * @param message 사용자에게 보여줄 메시지(String)
     * @return 사용자 입력값(String)
     */
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    /**
     * displayMessage 메서드는 콘솔에 전달된 메시지를 출력합니다.
     *
     * @param message 출력할 메시지(String)
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * displayBoards 메서드는 Board 객체 리스트를 출력합니다.
     * <p>
     * 리스트가 비어있을 경우 "doesn`t exist" 메시지를 출력합니다.
     *
     * @param boards 출력할 Board 객체 리스트
     */
    public void displayBoards(List<Board> boards) {
        if (boards.isEmpty()) {
            System.out.println("doesn`t exist");
        } else {
            // 각 Board 객체의 toString() 메서드를 호출하여 출력
            boards.forEach(System.out::println);
        }
    }
}