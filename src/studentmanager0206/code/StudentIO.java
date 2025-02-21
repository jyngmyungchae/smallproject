package studentmanager0206.code;

import java.util.List;

/**
 * StudentIO 인터페이스는 학생 정보를 입력, 출력, 검색, 정렬 및 저장, 삭제 기능을 포함합니다.
 */
public interface StudentIO extends StudentInput, SearchStudent, SortedStudent {
    /**
     * 학생 정보를 저장합니다.
     * @param student 저장할 학생 객체
     */
    void save(Student student);

    /**
     * 모든 학생 정보를 조회합니다.
     * @return 학생 리스트
     */
    List<Student> getAllStudents();

    /**
     * 주어진 학번의 학생 정보를 삭제합니다.
     * @param sno 삭제할 학생의 학번
     */
    void delete(String sno);
}
