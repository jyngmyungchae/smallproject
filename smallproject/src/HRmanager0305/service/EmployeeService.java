package HRmanager0305.service;

import HRmanager0305.dto.Employees;

import java.util.Date;
import java.util.List;

public interface EmployeeService {

    List<Employees> addEmployee(Employees employee);
    List<Employees> deleteEmployee(int employeeId);
    List<Employees> updateEmployee(Employees employee);
    List<Employees> updateName(String oldFullName, String newFirstName, String newLastName);
    List<Employees> updateByChoice(String fieldToUpdate, String oldValue, String newValue);

    //사원 번호를 기준으로 검색
    List<Employees> searchByEmpId(int employee_id);
    //이름으로 검색 - last name 검색
    List<Employees>  searchByLastname(String Last_name);
    //이름 검색 - first name 검색
    List<Employees> searchByFirstname(String First_name);
    //직업 검색
    List<Employees> searchByJobId(String job_id);
    //고용일/근속기간 검색 - 고용일 검색
    List<Employees> searchByHireDate(Date hire_date);
    //고용일/근속기간 검색 - 근무기간 검색 : job_history
    List<Employees> searchByEmploymentDuration(Date startDate, Date endDate);

    //sort : 사원번호를 기준으로 정렬
    List<Employees> sortByEmpId();
    //sort : 이름 기준으로 정렬
    List<Employees> sortByName();
    //sort : 근속기간 기준으로 정렬
    List<Employees> sortByJHireDate();
    //sort : 사원번호를 기준으로 정렬
    List<Employees> sortByJobId();
    //sort  sub menu : 오름차순 / 내림차순 선택
    int searchSubMenu1(List<Employees> searchList);
    List<Employees> searchSubMenu2(List<Employees> searchList);
    List<Employees> sortEmployees(List<Employees> sortList, int choiceSubMenu);

}