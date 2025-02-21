package board0221;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

/**
 * BoardManager 클래스는 BoardService 인터페이스를 구현하여
 * 게시글 생성, 조회, 수정, 삭제 등의 기능(CRUD)을 수행합니다.
 * 사용자와의 입출력(BoardIO) 및 데이터 저장(BoardDAO)을 담당합니다.
 */
public class BoardManager implements BoardService {
    // 게시글 데이터 접근 객체: 게시글의 저장, 조회, 수정, 삭제를 담당
    private final BoardDAO dao;
    // 사용자 입출력 객체: 메뉴 표시, 사용자 입력, 메시지 출력 등을 처리
    private final BoardIO io;
    // 메뉴 번호에 해당하는 명령(Runnable)을 저장하는 맵 (명령 패턴 활용)
    private final Map<Integer, Runnable> commandMap;

    /**
     * 생성자: dao, io 객체를 초기화하고 명령(commandMap)을 설정합니다.
     */
    public BoardManager() {
        this.dao = new BoardDAO();
        this.io = new BoardIO();
        this.commandMap = new HashMap<>();
        initCommands(); // 메뉴와 기능(메서드) 매핑 초기화
    }

    /**
     * initCommands 메서드는 메뉴 번호와 해당 명령(메서드 참조)을 commandMap에 등록합니다.
     */
    private void initCommands() {
        commandMap.put(1, this::handleCreateBoard);  // 1번 메뉴: 게시글 생성 처리
        commandMap.put(2, this::handleRead);           // 2번 메뉴: 게시글 조회 후 수정/삭제 처리
        commandMap.put(3, this::handleClear);          // 3번 메뉴: 전체 게시글 삭제 처리
        commandMap.put(4, this::exit);                 // 4번 메뉴: 프로그램 종료 처리
    }

    /**
     * run 메서드는 무한 루프로 사용자 입력을 받고 해당하는 명령을 실행합니다.
     * 잘못된 메뉴 선택 시 "재입력" 메시지를 출력합니다.
     */
    public void run() {
        while (true) {
            io.displayMenu();               // 메뉴 화면 출력
            int choice = io.getMenuChoice();  // 사용자로부터 메뉴 번호 입력받음
            Runnable command = commandMap.get(choice); // 메뉴 번호에 해당하는 명령 조회
            if (command != null) {
                command.run();              // 해당 명령 실행
            } else {
                io.displayMessage("재입력"); // 유효하지 않은 번호면 재입력 요청 메시지 출력
            }
        }
    }

    /**
     * createBoard 메서드는 사용자 입력으로 제목, 내용, 작성자를 받아
     * 새로운 Board 객체를 생성한 후 데이터 저장소(dao)에 저장합니다.
     *
     * @param btitle  게시글 제목
     * @param bcontent 게시글 내용
     * @param bwriter 게시글 작성자
     * @return 저장된 Board 객체 (게시글 번호가 할당된 상태)
     */
    @Override
    public Board createBoard(String btitle, String bcontent, String bwriter) {
        Board board = new Board.BoardBuilder()
                .btitle(btitle)
                .bcontent(bcontent)
                .bwriter(bwriter)
                .build();
        board = dao.save(board); // dao.save()를 통해 게시글 저장 및 게시글 번호 할당
        return board;
    }

    /**
     * getAllBoards 메서드는 현재 저장소에 저장된 모든 게시글 목록을 반환합니다.
     *
     * @return List<Board> 모든 게시글 목록
     */
    @Override
    public List<Board> getAllBoards() {
        return dao.getAll();
    }

    /**
     * updateBoard 메서드는 주어진 게시글 번호(bno)에 해당하는 게시글을 찾아,
     * 내용(bcontent)을 새로 업데이트한 후 저장소에 반영합니다.
     *
     * @param bno        수정할 게시글 번호
     * @param newContent 새로운 게시글 내용
     */
    @Override
    public void updateBoard(String bno, String newContent) {
        Optional<Board> boardOpt = dao.findByBno(bno); // 게시글 조회
        if (!boardOpt.isPresent()) {
            return; // 게시글이 없으면 아무 작업도 수행하지 않음
        }
        Board board = boardOpt.get();
        // 기존 게시글의 제목, 작성자, 작성일은 유지하면서 내용만 변경한 새 객체 생성 (불변 객체 보존)
        Board updated = new Board.BoardBuilder()
                .bno(board.getBno())
                .btitle(board.getBtitle())
                .bcontent(newContent)
                .bwriter(board.getBwriter())
                .bdate(board.getBdate())
                .build();
        dao.update(updated); // 업데이트된 게시글을 저장소에 반영
    }

    /**
     * deleteBoard 메서드는 주어진 게시글 번호(bno)를 이용해 해당 게시글을 삭제합니다.
     *
     * @param bno 삭제할 게시글 번호
     */
    @Override
    public void deleteBoard(String bno) {
        dao.delete(bno);
    }

    /**
     * clearBoards 메서드는 저장소에 있는 모든 게시글을 삭제합니다.
     */
    @Override
    public void clearBoards() {
        dao.clear();
    }

    /**
     * getBoardByBno 메서드는 주어진 게시글 번호(bno)를 통해 게시글을 조회하고,
     * 결과를 Optional 형태로 반환합니다.
     *
     * @param bno 조회할 게시글 번호
     * @return Optional<Board> 게시글이 존재하면 Optional에 포함, 아니면 Optional.empty()
     */
    @Override
    public Optional<Board> getBoardByBno(String bno) {
        return dao.findByBno(bno);
    }

    /**
     * handleCreateBoard 메서드는 사용자로부터 게시글 정보를 입력받아,
     * 새로운 게시글을 생성하고 작성 완료 메시지를 출력합니다.
     */
    private void handleCreateBoard() {
        String title = io.prompt("제목 : ");
        String content = io.prompt("내용 : ");
        String writer = io.prompt("작성자 : ");
        Board board = createBoard(title, content, writer);
        io.displayMessage("작성 완료: " + board.getBno());
    }

    /**
     * handleRead 메서드는 저장소에 있는 모든 게시글을 출력하고,
     * 사용자로부터 수정 또는 삭제할 게시글 번호를 입력받아 해당 작업을 수행합니다.
     */
    private void handleRead() {
        io.displayBoards(getAllBoards()); // 현재 저장된 모든 게시글 출력
        String bno = io.prompt("수정 또는 삭제하고 싶은 bno 입력\n엔터입력= exit\n");
        if (bno.isEmpty()) { // 입력이 없으면 종료
            return;
        }
        Optional<Board> boardOpt = getBoardByBno(bno); // 입력한 bno로 게시글 조회
        if (!boardOpt.isPresent()) {
            io.displayMessage("doesn`t exist bno " + bno); // 게시글이 없을 경우 메시지 출력
            return;
        }
        String action = io.prompt("u 입력 = 수정\nd 입력 = 삭제\n나머지 키 = exit");
        if (action.equalsIgnoreCase("u")) { // 수정 선택 시
            String newContent = io.prompt("새로운 내용 : ");
            updateBoard(bno, newContent);
            io.displayMessage("updated");
        } else if (action.equalsIgnoreCase("d")) { // 삭제 선택 시
            deleteBoard(bno);
            io.displayMessage("deleted");
        } else {
            io.displayMessage("exit"); // 그 외 입력 시 아무 작업도 수행하지 않음
        }
    }

    /**
     * handleClear 메서드는 저장소의 모든 게시글을 삭제한 후, 삭제 완료 메시지를 출력합니다.
     */
    private void handleClear() {
        clearBoards();
        io.displayMessage("clear");
    }

    /**
     * exit 메서드는 종료 메시지를 출력한 후, 프로그램을 종료합니다.
     */
    private void exit() {
        io.displayMessage("exit");
        System.exit(0); // 프로그램 강제 종료
    }
}