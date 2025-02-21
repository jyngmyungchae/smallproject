package board0221;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class BoardDAO {
    private Map<String, Board> boardMap = new HashMap<>();

    public Board save(Board board) {
        if (board.getBno() == null) {
            String bno = findNextBno();
            board = board.withBno(bno);
        }
        boardMap.put(board.getBno(), board);
        return board;
    }

    private String findNextBno() {
        int candidate = 1;
        while (boardMap.containsKey(String.valueOf(candidate))) {
            candidate++;
        }
        return String.valueOf(candidate);
    }

    public List<Board> getAll() {
        return new ArrayList<>(boardMap.values());
    }

    public Optional<Board> findByBno(String bno) {
        return Optional.ofNullable(boardMap.get(bno));
    }

    public void update(Board board) {
        boardMap.put(board.getBno(), board);
    }

    public void delete(String bno) {
        boardMap.remove(bno);

    }

    public void clear() {
        boardMap.clear();
    }
}