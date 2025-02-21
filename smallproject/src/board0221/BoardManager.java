package board0221;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

/**
 * {@code BoardManager} 클래스는 {@code BoardService} 인터페이스를 구현하여
 * 게시글의 생성, 조회, 수정, 삭제 등의 기능(CRUD)을 수행하는 게시판 관리 클래스입니다.
 * <p>
 * 내부적으로 {@code BoardDAO}를 사용하여 게시글 데이터를 관리하며,
 * {@code BoardIO}를 통해 사용자와 상호작용합니다.
 * 또한, 명령 패턴을 활용하여 메뉴 번호에 따라 실행할 명령을 관리합니다.
 */
public class BoardManager implements BoardService {
    // 게시글 데이터 접근 객체: 게시글의 저장, 조회, 수정, 삭제를 담당
    private final BoardDAO dao;
    // 사용자 입출력 객체: 메뉴 표시, 사용자 입력, 메시지 출력 등을 처리
    private final BoardIO io;
    // 메뉴 번호에 해당하는 명령(Runnable)을 저장하는 맵 (명령 패턴 활용)
    private final Map<Integer, Runnable> commandMap;

    /**
     * 생성자: {@code BoardDAO} 및 {@code BoardIO} 객체를 초기화하고,
     * 명령 맵({@code commandMap})을 설정합니다.
     */
    public BoardManager() {
        this.dao = new BoardDAO();
        this.io = new BoardIO();
        this.commandMap = new HashMap<>();
        initCommands(); // 메뉴와 기능(메서드) 매핑 초기화
    }

    /**
     * 메뉴 번호와 해당하는 명령을 {@code commandMap}에 등록하는 메서드입니다.
     */
    private void initCommands() {
        commandMap.put(1, this::handleCreateBoard);  // 1번 메뉴: 게시글 생성 처리
        commandMap.put(2, this::handleRead);         // 2번 메뉴: 게시글 조회 및 수정/삭제
        commandMap.put(3, this::handleClear);        // 3번 메뉴: 전체 게시글 삭제
        commandMap.put(4, this::exit);               // 4번 메뉴: 프로그램 종료
    }

    /**
     * 프로그램의 실행 루프를 담당하는 메서드입니다.
     * <p>
     * 사용자가 메뉴를 선택하고, 해당 메뉴에 대한 명령을 실행합니다.
     * 잘못된 메뉴 선택 시 "재입력" 메시지를 출력합니다.
     */
    public void run() {
        while (true) {
            io.displayMenu();                 // 메뉴 화면 출력
            int choice = io.getMenuChoice();  // 사용자 입력 받기
            Runnable command = commandMap.get(choice); // 메뉴 번호에 해당하는 명령 조회
            if (command != null) {
                command.run();                // 명령 실행
            } else {
                io.displayMessage("재입력");  // 잘못된 입력 처리
            }
        }
    }

    /**
     * 새로운 게시글을 생성하고 저장하는 메서드입니다.
     *
     * @param btitle   게시글 제목
     * @param bcontent 게시글 내용
     * @param bwriter  게시글 작성자
     * @return 저장된 게시글 객체 ({@code Board})
     */
    @Override
    public Board createBoard(String btitle, String bcontent, String bwriter) {
        Board board = new Board.BoardBuilder()
                .btitle(btitle)
                .bcontent(bcontent)
                .bwriter(bwriter)
                .build();
        return dao.save(board); // 게시글 저장 및 번호 할당
    }

    /**
     * 저장된 모든 게시글을 반환하는 메서드입니다.
     *
     * @return 게시글 목록 ({@code List<Board>})
     */
    @Override
    public List<Board> getAllBoards() {
        return dao.getAll();
    }

    /**
     * 특정 게시글을 업데이트하는 메서드입니다.
     *
     * @param bno        수정할 게시글 번호
     * @param newContent 새로운 게시글 내용
     */
    @Override
    public void updateBoard(String bno, String newContent) {
        Optional<Board> boardOpt = dao.findByBno(bno);
        if (!boardOpt.isPresent()) {
            return;
        }
        Board board = boardOpt.get();
        Board updated = new Board.BoardBuilder()
                .bno(board.getBno())
                .btitle(board.getBtitle())
                .bcontent(newContent)
                .bwriter(board.getBwriter())
                .bdate(board.getBdate())
                .build();
        dao.update(updated);
    }

    /**
     * 특정 게시글을 삭제하는 메서드입니다.
     *
     * @param bno 삭제할 게시글 번호
     */
    @Override
    public void deleteBoard(String bno) {
        dao.delete(bno);
    }

    /**
     * 모든 게시글을 삭제하는 메서드입니다.
     */
    @Override
    public void clearBoards() {
        dao.clear();
    }

    /**
     * 특정 게시글을 게시글 번호를 통해 조회하는 메서드입니다.
     *
     * @param bno 조회할 게시글 번호
     * @return 게시글 객체를 {@code Optional} 형태로 반환
     */
    @Override
    public Optional<Board> getBoardByBno(String bno) {
        return dao.findByBno(bno);
    }

    /**
     * 사용자 입력을 받아 새 게시글을 생성하는 메서드입니다.
     */
    private void handleCreateBoard() {
        String title = io.prompt("제목 : ");
        String content = io.prompt("내용 : ");
        String writer = io.prompt("작성자 : ");
        Board board = createBoard(title, content, writer);
        io.displayMessage("작성 완료: " + board.getBno());
    }

    /**
     * 저장된 게시글 목록을 출력하고, 수정 또는 삭제할 게시글을 선택하는 메서드입니다.
     */
    private void handleRead() {
        io.displayBoards(getAllBoards());
        String bno = io.prompt("수정 또는 삭제하고 싶은 bno 입력\n엔터입력= exit\n");
        if (bno.isEmpty()) {
            return;
        }
        Optional<Board> boardOpt = getBoardByBno(bno);
        if (!boardOpt.isPresent()) {
            io.displayMessage("doesn’t exist bno " + bno);
            return;
        }
        String action = io.prompt("u 입력 = 수정\nd 입력 = 삭제\n나머지 키 = exit\n");
        if (action.equalsIgnoreCase("u")) {
            String newContent = io.prompt("새로운 내용 : ");
            updateBoard(bno, newContent);
            io.displayMessage("updated "+ (bno));
        } else if (action.equalsIgnoreCase("d")) {
            deleteBoard(bno);
            io.displayMessage("deleted "+ (bno));
        } else {
            io.displayMessage("exit");
        }
    }

    /**
     * 저장된 모든 게시글을 삭제하는 메서드입니다.
     */
    private void handleClear() {
        clearBoards();
        io.displayMessage("clear");
    }

    /**
     * 프로그램을 종료하는 메서드입니다.
     */
    private void exit() {
        io.displayMessage("exit");
        System.exit(0);
    }
}