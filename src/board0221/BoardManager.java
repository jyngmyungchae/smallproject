package board0221;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

public class BoardManager implements BoardService {
    private final BoardDAO dao;
    private final BoardIO io;
    private final Map<Integer, Runnable> commandMap;

    public BoardManager() {
        this.dao = new BoardDAO();
        this.io = new BoardIO();
        this.commandMap = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        commandMap.put(1, this::handleCreateBoard);
        commandMap.put(2, this::handleRead);
        commandMap.put(3, this::handleClear);
        commandMap.put(4, this::exit);
    }

    public void run() {
        while (true) {
            io.displayMenu();
            int choice = io.getMenuChoice();
            Runnable command = commandMap.get(choice);
            if (command != null) {
                command.run();
            } else {
                io.displayMessage("재입력");
            }
        }
    }

    @Override
    public Board createBoard(String btitle, String bcontent, String bwriter) {
        Board board = new Board.BoardBuilder()
                .btitle(btitle)
                .bcontent(bcontent)
                .bwriter(bwriter)
                .build();
        board = dao.save(board);
        return board;
    }

    @Override
    public List<Board> getAllBoards() {
        return dao.getAll();
    }

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


    @Override
    public void deleteBoard(String bno) {
        dao.delete(bno);
    }

    @Override
    public void clearBoards() {
        dao.clear();
    }

    @Override
    public Optional<Board> getBoardByBno(String bno) {
        return dao.findByBno(bno);
    }

    private void handleCreateBoard() {
        String title = io.prompt("제목 : ");
        String content = io.prompt("내용 : ");
        String writer = io.prompt("작성자 : ");
        Board board = createBoard(title, content, writer);
        io.displayMessage("작성 완료: " + board.getBno());
    }

    private void handleRead() {
        io.displayBoards(getAllBoards());
        String bno = io.prompt("수정 또는 삭제하고 싶은 bno 입력\n엔터입력= exit\n");
        if (bno.isEmpty()) {
            return;
        }
        Optional<Board> boardOpt = getBoardByBno(bno);
        if (!boardOpt.isPresent()) {
            io.displayMessage("doesn`t exist bno " + bno);
            return;
        }
        String action = io.prompt("u 입력 = 수정\nd 입력 = 삭제\n나머지 키 = exit");
        if (action.equalsIgnoreCase("u")) {
            String newContent = io.prompt("새로운 내용 : ");
            updateBoard(bno, newContent);
            io.displayMessage("updated");
        } else if (action.equalsIgnoreCase("d")) {
            deleteBoard(bno);
            io.displayMessage("deleted");
        } else {
            io.displayMessage("exit");
        }
    }

    private void handleClear() {
        clearBoards();
        io.displayMessage("clear");
    }

    private void exit() {
        io.displayMessage("exit");
        System.exit(0);
    }
}