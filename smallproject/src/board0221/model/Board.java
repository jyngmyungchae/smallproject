package board0221.model;

import java.util.Date;

/**
 * Board 클래스는 게시글 데이터를 표현하는 불변(immutable) 객체입니다.
 * 게시글은 게시글 번호(bno), 제목(btitle), 내용(bcontent), 작성자(bwriter), 작성 날짜(bdate)를 가집니다.
 * 객체 생성은 Builder 패턴을 사용하여 수행됩니다.
 */
public class Board {
    // 게시글 번호 (식별자)
    private final String bno;
    // 게시글 제목
    private final String btitle;
    // 게시글 내용
    private final String bcontent;
    // 게시글 작성자
    private final String bwriter;
    // 게시글 작성일 (생성 시 자동 설정)
    private final Date bdate;

    /**
     * Board 생성자: Builder를 통해서만 객체가 생성되도록 private 접근 제어를 사용합니다.
     *
     * @param builder BoardBuilder 객체로부터 값들을 전달받음
     */
    private Board(BoardBuilder builder) {
        this.bno = builder.bno;
        this.btitle = builder.btitle;
        this.bcontent = builder.bcontent;
        this.bwriter = builder.bwriter;
        this.bdate = builder.bdate;
    }

    /**
     * @return 게시글 번호
     */
    public String getBno() { return bno; }

    /**
     * @return 게시글 제목
     */
    public String getBtitle() { return btitle; }

    /**
     * @return 게시글 내용
     */
    public String getBcontent() { return bcontent; }

    /**
     * @return 게시글 작성자
     */
    public String getBwriter() { return bwriter; }

    /**
     * @return 게시글 작성일
     */
    public Date getBdate() { return bdate; }

    /**
     * 기존 Board 객체의 데이터를 유지하면서 새로운 게시글 번호를 적용한 새로운 Board 객체를 생성합니다.
     * (불변 객체 특성상 기존 객체를 변경하지 않고 새 인스턴스를 만듦)
     *
     * @param newBno 새로운 게시글 번호
     * @return 새로운 게시글 번호가 적용된 Board 객체
     */
    public Board withBno(String newBno) {
        return new Board.BoardBuilder()
                .bno(newBno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bwriter(this.bwriter)
                .bdate(this.bdate)
                .build();
    }

    /**
     * BoardBuilder 클래스는 Builder 패턴을 이용하여 Board 객체를 생성하는 데 도움을 줍니다.
     */
    public static class BoardBuilder {
        private String bno;
        private String btitle;
        private String bcontent;
        private String bwriter;
        private Date bdate = new Date(); // 기본값: 현재 날짜

        /**
         * 게시글 번호를 설정합니다.
         * @param bno 게시글 번호
         * @return 현재 BoardBuilder 인스턴스 (메서드 체이닝 지원)
         */
        public BoardBuilder bno(String bno) {
            this.bno = bno;
            return this;
        }

        /**
         * 게시글 제목을 설정합니다.
         * @param btitle 게시글 제목
         * @return 현재 BoardBuilder 인스턴스
         */
        public BoardBuilder btitle(String btitle) {
            this.btitle = btitle;
            return this;
        }

        /**
         * 게시글 내용을 설정합니다.
         * @param bcontent 게시글 내용
         * @return 현재 BoardBuilder 인스턴스
         */
        public BoardBuilder bcontent(String bcontent) {
            this.bcontent = bcontent;
            return this;
        }

        /**
         * 게시글 작성자를 설정합니다.
         * @param bwriter 게시글 작성자
         * @return 현재 BoardBuilder 인스턴스
         */
        public BoardBuilder bwriter(String bwriter) {
            this.bwriter = bwriter;
            return this;
        }

        /**
         * 게시글 작성일을 설정합니다.
         * @param bdate 게시글 작성일
         * @return 현재 BoardBuilder 인스턴스
         */
        public BoardBuilder bdate(Date bdate) {
            this.bdate = bdate;
            return this;
        }

        /**
         * 설정된 값을 기반으로 새로운 Board 객체를 생성합니다.
         * @return 생성된 Board 객체
         */
        public Board build() {
            return new Board(this);
        }
    }

    /**
     * Board 객체의 주요 정보를 문자열 형태로 반환합니다.
     *
     * @return Board 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "Board{" +
                "bno='" + bno + '\'' +
                ", btitle='" + btitle + '\'' +
                ", bcontent='" + bcontent + '\'' +
                ", bwriter='" + bwriter + '\'' +
                ", bdate=" + bdate +
                '}';
    }
}