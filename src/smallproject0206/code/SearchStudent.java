package smallproject0206.code;


/**
 * SearchStudent 인터페이스는 학생 정보를 학번으로 검색하는 기능을 제공합니다.
 * StudentOutput 인터페이스를 상속받아 학생 출력 기능도 포함합니다.
 */
public interface SearchStudent extends StudentOutput {
    /**
     * 학번을 기준으로 학생을 검색합니다.
     */
    void searchBySno();
}
