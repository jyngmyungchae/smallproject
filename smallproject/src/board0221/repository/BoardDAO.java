package board0221.repository;

import board0221.model.Board;

import java.util.*;

/**
 * {@code BoardDAO} 클래스는 게시글({@code Board}) 데이터를 메모리 내에서 저장하고 관리하는
 * 데이터 접근 객체(Data Access Object)입니다.
 * 내부적으로 {@code Map}을 사용하여 게시글 번호({@code bno})를 키로 저장합니다.
 */
public class BoardDAO {
    // 게시글을 저장하는 Map: key는 게시글 번호(bno), value는 Board 객체
    private Map<String, Board> boardMap = new HashMap<>();

    /**
     * 게시글을 저장하는 메서드입니다.
     * <p>
     * 입력된 {@code Board} 객체의 게시글 번호({@code bno})가 {@code null}인 경우,
     * 새로운 번호를 생성하여 게시글 번호를 할당한 후 저장합니다.
     * 만약 이미 {@code bno}가 존재하면 해당 번호를 그대로 사용하여 저장합니다.
     *
     * @param board 저장할 게시글 객체({@code Board})
     * @return 저장 후 게시글 번호가 할당된 {@code Board} 객체
     */
    public Board save(Board board) {
        if (board.getBno() == null) {
            String bno = findNextBno();
            board = board.withBno(bno);
        }
        boardMap.put(board.getBno(), board);
        return board;
    }

    /**
     * 다음에 사용할 게시글 번호를 생성하는 메서드입니다.
     * <p>
     * 현재 저장된 게시글 번호들과 중복되지 않는 첫 번째 정수(문자열 형태)를 반환합니다.
     *
     * @return 생성된 게시글 번호({@code String})
     */
    private String findNextBno() {
        int candidate = 1;
        while (boardMap.containsKey(String.valueOf(candidate))) {
            candidate++;
        }
        return String.valueOf(candidate);
    }

    /**
     * 저장된 모든 게시글을 조회하는 메서드입니다.
     *
     * @return 저장된 모든 {@code Board} 객체를 포함하는 리스트({@code List<Board>})
     */
    public List<Board> getAll() {
        return new ArrayList<>(boardMap.values());
    }

    /**
     * 게시글 번호({@code bno})를 이용하여 특정 게시글을 검색하는 메서드입니다.
     *
     * @param bno 검색할 게시글 번호({@code String})
     * @return 해당 게시글을 {@code Optional<Board>} 형태로 반환하며, 없으면 {@code Optional.empty()}를 반환합니다.
     */
    public Optional<Board> findByBno(String bno) {
        return Optional.ofNullable(boardMap.get(bno));
    }

    /**
     * 게시글을 수정하는 메서드입니다.
     * <p>
     * 입력된 {@code Board} 객체의 게시글 번호({@code bno})를 키로 사용하여
     * {@code boardMap}의 기존 값을 덮어씁니다.
     *
     * @param board 수정할 내용이 반영된 {@code Board} 객체
     */
    public void update(Board board) {
        boardMap.put(board.getBno(), board);
    }

    /**
     * 게시글을 삭제하는 메서드입니다.
     *
     * @param bno 삭제할 게시글의 번호({@code String})
     */
    public void delete(String bno) {
        boardMap.remove(bno);
    }

    /**
     * 저장된 모든 게시글을 삭제하는 메서드입니다.
     */
    public void clear() {
        boardMap.clear();
    }
}