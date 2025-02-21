package board0221;

import java.util.List;
import java.util.Optional;

/**
 * {@code BoardService} 인터페이스는 게시판 기능을 정의합니다.
 * <p>
 * 게시글의 생성, 조회, 수정, 삭제 등의 핵심적인 기능을 포함하고 있으며,
 * {@code BoardManager} 클래스에서 이 인터페이스를 구현하여 동작을 제공합니다.
 */
public interface BoardService {
    /**
     * 새로운 게시글을 생성하는 메서드입니다.
     *
     * @param btitle   게시글 제목
     * @param bcontent 게시글 내용
     * @param bwriter  게시글 작성자
     * @return 생성된 게시글 객체 ({@code Board})
     */
    Board createBoard(String btitle, String bcontent, String bwriter);

    /**
     * 저장된 모든 게시글을 조회하는 메서드입니다.
     *
     * @return 게시글 목록 ({@code List<Board>})
     */
    List<Board> getAllBoards();

    /**
     * 특정 게시글을 업데이트하는 메서드입니다.
     *
     * @param bno        수정할 게시글 번호
     * @param newContent 새로운 게시글 내용
     */
    void updateBoard(String bno, String newContent);

    /**
     * 특정 게시글을 삭제하는 메서드입니다.
     *
     * @param bno 삭제할 게시글 번호
     */
    void deleteBoard(String bno);

    /**
     * 저장된 모든 게시글을 삭제하는 메서드입니다.
     */
    void clearBoards();

    /**
     * 특정 게시글을 게시글 번호를 통해 조회하는 메서드입니다.
     *
     * @param bno 조회할 게시글 번호
     * @return 게시글 객체를 {@code Optional} 형태로 반환
     */
    Optional<Board> getBoardByBno(String bno);
}