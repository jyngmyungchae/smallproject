package board0221;

import java.util.List;
import java.util.Scanner;

/**
 * {@code BoardIO} 클래스는 게시판 프로그램의 입출력을 담당합니다.
 * <p>
 * 사용자에게 메뉴를 표시하고, 입력을 받아 처리하며, 결과 메시지를 출력하는 역할을 합니다.
 */
public class BoardIO {
    // 사용자 입력을 받기 위한 Scanner 객체 (표준 입력(System.in) 사용)
    private final Scanner scanner = new Scanner(System.in);

    /**
     * 콘솔에 메뉴 옵션을 출력하는 메서드입니다.
     * <p>
     * 사용자가 선택할 수 있는 메뉴는 다음과 같습니다:
     * <ul>
     *     <li>1. Create (게시글 작성)</li>
     *     <li>2. Read (게시글 목록 조회)</li>
     *     <li>3. Clear (게시글 전체 삭제)</li>
     *     <li>4. Exit (프로그램 종료)</li>
     * </ul>
     */
    public void displayMenu() {
        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Clear");
        System.out.println("4. Exit");
        System.out.print("Select menu: ");
    }

    /**
     * 사용자로부터 메뉴 선택 입력을 받아 정수로 반환하는 메서드입니다.
     * <p>
     * 입력값이 올바른 정수가 아닌 경우 {@code -1}을 반환하여 잘못된 입력을 처리합니다.
     *
     * @return 사용자 입력 메뉴 번호 (정수). 입력이 잘못된 경우 {@code -1} 반환.
     */
    public int getMenuChoice() {
        String input = scanner.nextLine().trim(); // 입력값의 앞뒤 공백 제거
        try {
            return Integer.parseInt(input);  // 문자열을 정수로 변환
        } catch (NumberFormatException e) {
            return -1; // 입력값이 정수가 아닐 경우 -1 반환
        }
    }

    /**
     * 특정 메시지를 출력한 후, 사용자 입력을 받아 반환하는 메서드입니다.
     *
     * @param message 사용자에게 보여줄 메시지({@code String}).
     * @return 사용자 입력값({@code String}). 입력값의 앞뒤 공백은 제거됨.
     */
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    /**
     * 콘솔에 전달된 메시지를 출력하는 메서드입니다.
     *
     * @param message 출력할 메시지({@code String}).
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * 게시글 목록을 출력하는 메서드입니다.
     * <p>
     * 리스트가 비어있을 경우 {@code "doesn't exist"} 메시지를 출력합니다.
     * 그렇지 않으면 각 게시글을 출력합니다.
     *
     * @param boards 출력할 {@code Board} 객체 리스트.
     */
    public void displayBoards(List<Board> boards) {
        if (boards.isEmpty()) {
            System.out.println("doesn't exist");
        } else {
            boards.forEach(System.out::println); // Board 객체의 toString() 메서드 호출하여 출력
        }
    }
}