package studentmanager0206.code;

import java.util.List;

/**
 * SortedStudent 인터페이스는 학생 리스트를 다양한 기준(총점, 학번)으로 정렬하는 기능을 제공합니다.
 */
public interface SortedStudent {
    /**
     * 학생 리스트를 총점 기준으로 정렬합니다.
     * @param students 학생 리스트
     */
    void sortByTotal(List<Student> students);

    /**
     * 학생 리스트를 학번 기준으로 정렬합니다.
     * @param students 학생 리스트
     */
    void sortBySno(List<Student> students);
}
