package board0221;

import java.util.Date;

public class Board {
    private final String bno;
    private final String btitle;
    private final String bcontent;
    private final String bwriter;
    private final Date bdate;

    private Board(BoardBuilder builder) {
        this.bno = builder.bno;
        this.btitle = builder.btitle;
        this.bcontent = builder.bcontent;
        this.bwriter = builder.bwriter;
        this.bdate = builder.bdate;
    }

    public String getBno() { return bno; }
    public String getBtitle() { return btitle; }
    public String getBcontent() { return bcontent; }
    public String getBwriter() { return bwriter; }
    public Date getBdate() { return bdate; }

    public Board withBno(String newBno) {
        return new Board.BoardBuilder()
                .bno(newBno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bwriter(this.bwriter)
                .bdate(this.bdate)
                .build();
    }

    public static class BoardBuilder {
        private String bno;
        private String btitle;
        private String bcontent;
        private String bwriter;
        private Date bdate = new Date();

        public BoardBuilder bno(String bno) {
            this.bno = bno;
            return this;
        }
        public BoardBuilder btitle(String btitle) {
            this.btitle = btitle;
            return this;
        }
        public BoardBuilder bcontent(String bcontent) {
            this.bcontent = bcontent;
            return this;
        }
        public BoardBuilder bwriter(String bwriter) {
            this.bwriter = bwriter;
            return this;
        }
        public BoardBuilder bdate(Date bdate) {
            this.bdate = bdate;
            return this;
        }
        public Board build() {
            return new Board(this);
        }
    }

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
