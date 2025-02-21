package board0221;

import java.util.*;

/**
 * BoardDAO 클래스는 게시글(Board) 데이터를 메모리 내에 저장하고 관리하는
 * 데이터 접근 객체(Data Access Object)입니다.
 * 내부적으로 Map을 사용하여 게시글 번호(bno)를 키(key)로 저장합니다.
 */
public class BoardDAO {
    // 게시글을 저장하는 Map: key는 게시글 번호(bno), value는 Board 객체
    private Map<String, Board> boardMap = new HashMap<>();

    /**
     * 게시글 저장 메서드
     * <p>
     * 입력된 Board 객체의 게시글 번호(bno)가 null인 경우, 새로운 번호를 생성하여
     * 게시글 번호를 할당한 후 저장합니다.
     * 만약 이미 bno가 있다면 해당 번호를 그대로 사용하여 저장합니다.
     *
     * @param board 저장할 게시글 객체(Board)
     * @return 저장 후 게시글 번호가 할당된 Board 객체
     */
    public Board save(Board board) {
        // 게시글 번호가 없는 경우, 새로운 게시글 번호 생성
        if (board.getBno() == null) {
            String bno = findNextBno();
            // 기존 Board 객체의 정보를 유지하며 새로운 게시글 번호를 부여한 객체 생성
            board = board.withBno(bno);
        }
        // boardMap에 게시글 번호를 키로 하여 Board 객체를 저장
        boardMap.put(board.getBno(), board);
        return board;
    }

    /**
     * 다음에 사용할 게시글 번호를 생성하는 메서드
     * <p>
     * boardMap에 이미 존재하는 번호와 중복되지 않는 첫 번째 정수(문자열 형태)를 반환합니다.
     *
     * @return 생성된 게시글 번호(String)
     */
    private String findNextBno() {
        int candidate = 1;
        // candidate 값을 문자열로 변환하여 boardMap에 키로 존재하는지 확인
        while (boardMap.containsKey(String.valueOf(candidate))) {
            candidate++;  // 이미 존재하면 candidate 값을 증가시킴
        }
        return String.valueOf(candidate);
    }

    /**
     * 모든 게시글을 조회하는 메서드
     *
     * @return 저장된 모든 Board 객체들을 포함하는 리스트(List<Board>)
     */
    public List<Board> getAll() {
        // boardMap의 value들을 ArrayList로 반환하여 모든 게시글 조회
        return new ArrayList<>(boardMap.values());
    }

    /**
     * 게시글 번호(bno)를 이용하여 게시글을 검색하는 메서드
     *
     * @param bno 검색할 게시글 번호(String)
     * @return 해당 게시글을 Optional 형태로 반환, 없으면 Optional.empty()
     */
    public Optional<Board> findByBno(String bno) {
        return Optional.ofNullable(boardMap.get(bno));
    }

    /**
     * 게시글 수정 메서드
     * <p>
     * 입력된 Board 객체의 게시글 번호를 키로 하여 boardMap의 기존 값을 덮어씁니다.
     *
     * @param board 수정할 내용이 반영된 Board 객체
     */
    public void update(Board board) {
        boardMap.put(board.getBno(), board);
    }

    /**
     * 게시글 삭제 메서드
     *
     * @param bno 삭제할 게시글의 번호(String)
     */
    public void delete(String bno) {
        boardMap.remove(bno);
    }

    /**
     * 저장된 모든 게시글을 삭제하는 메서드
     */
    public void clear() {
        boardMap.clear();
    }
}