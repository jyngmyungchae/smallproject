package smallproject0206.code;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentDBIO 클래스는 데이터베이스 기반의 학생 I/O 기능을 구현한 클래스입니다.
 * StudentIO 인터페이스를 구현하며, 기본적인 CRUD 기능을 제공합니다.
 */
public class StudentDBIO extends ObjectIO implements StudentIO {
    public List<Student> students = new ArrayList<>();

    /**
     * 학생 정보를 입력받습니다.
     */
    @Override
    public void inputStudent() {}

    /**
     * 학생 정보를 출력합니다.
     */
    @Override
    public void outputStudent() {}

    /**
     * 학번을 기준으로 학생을 검색합니다.
     */
    @Override
    public void searchBySno() {}

    /**
     * 학생 리스트를 총점 기준으로 정렬합니다.
     * @param students 학생 리스트
     */
    @Override
    public void sortByTotal(List<Student> students) {}

    /**
     * 학생 리스트를 학번 기준으로 정렬합니다.
     * @param students 학생 리스트
     */
    @Override
    public void sortBySno(List<Student> students) {}

    /**
     * 학생 정보를 저장합니다.
     * @param student 저장할 학생 객체
     */
    @Override
    public void save(Student student) {}

    /**
     * 모든 학생 정보를 조회합니다.
     * @return 학생 정보 리스트
     */
    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }

    /**
     * 주어진 학번의 학생 정보를 삭제합니다.
     * @param sno 삭제할 학생의 학번
     */
    @Override
    public void delete(String sno) {

    }
}
