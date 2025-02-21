package board0221;

import java.util.List;
import java.util.Optional;

/**
 * 게시판 서비스 인터페이스
 */
public interface BoardService {
    Board createBoard(String btitle, String bcontent, String bwriter);
    List<Board> getAllBoards();
    void updateBoard(String bno, String newContent);
    void deleteBoard(String bno);
    void clearBoards();
    Optional<Board> getBoardByBno(String bno);
}
