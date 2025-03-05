package HRmanager0305.service;

import HRmanager0305.dao.EmployeeDaoImpl;
import HRmanager0305.dto.Employees;
import java.util.*;
import static HRmanager0305.util.validation.ConsoleInputValidator.validateFieldValue;

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    // 사원 번호를 기준으로 검색
    public List<Employees> searchByEmpId(int employee_id) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("employee_id", employee_id);
        return searchList.orElse(new ArrayList<>());
    }

    // 이름으로 검색 - last name 검색
    public List<Employees> searchByLastname(String Last_name) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("last_name", Last_name);


        return searchList.orElse(new ArrayList<>());
    }

    // 이름 검색 - first name 검색
    public List<Employees> searchByFirstname(String First_name) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("first_name", First_name);


        return searchList.orElse(new ArrayList<>());
    }

    // 직업 검색
    public List<Employees> searchByJobId(String job_id) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("job_id", job_id);


        return searchList.orElse(new ArrayList<>());
    }

    // 고용일/근속기간 검색 - 고용일 검색
    public List<Employees> searchByHireDate(Date hire_date) {
        java.sql.Date sqlHireDate = new java.sql.Date(hire_date.getTime());

        Optional<List<Employees>> searchList = employeeDao.findEmployee("hire_date", sqlHireDate);

        return searchList.orElse(new ArrayList<>());
    }

    // 고용일/근속기간 검색 - 근무기간 검색 : job_history
    public List<Employees> searchByEmploymentDuration(Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        Optional<List<Employees>> searchList = employeeDao.findJobHistory(sqlStartDate, sqlEndDate);

        return searchList.orElse(new ArrayList<>());
    }

    public int searchSubMenu1(List<Employees> searchList){
        return searchList.size();
    }

    public  List<Employees> searchSubMenu2(List<Employees> searchList){
        return searchList;
    }

    // sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByEmpId() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list ->  {list.sort(Comparator.comparing(Employees::getEmployee_id));},
                () -> System.out.println("No employees found.")
        );
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 이름 기준으로 정렬
    public List<Employees> sortByName() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> {list.sort(Comparator.comparing(Employees::getFirst_name)
                        .thenComparing(Employees::getLast_name));},
                () -> System.out.println("No employees found.")
        );

        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 입사일 기준으로 정렬
    public List<Employees> sortByJHireDate() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> {list.sort(Comparator.comparing(Employees::getHire_date));},
                () -> System.out.println("No employees found.")
        );
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByJobId() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> {list.sort(Comparator.comparing(Employees::getJob_id));},
                () -> System.out.println("No employees found.")
        );
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    //true 면 오름차순 /fale면 역순
    public List<Employees> sortEmployees(List<Employees> sortList, int choiceSubMenu) {
        if (choiceSubMenu ==1 ) {
            return sortList;
        } else {
            Collections.reverse(sortList);
        }
        return sortList;
    }
    @Override
    public List<Employees> addEmployee(Employees employee) {
        var insertedEmployee = employeeDao.addEmployee(employee);
        insertedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully added employee: " + e),
                () -> System.out.println("Failed to add employee with employee_id: " + employee.getEmployee_id())
        );
        return insertedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> deleteEmployee(int employeeId) {
        var deletedEmployee = employeeDao.deleteEmployee(employeeId);
        deletedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully deleted employee: " + e),
                () -> System.out.println("Failed to delete employee with employee_id: " + employeeId)
        );
        return deletedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> updateEmployee(Employees employee) {
        var updatedEmployee = employeeDao.updateEmployee(employee);
        updatedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully updated employee: " + e),
                () -> System.out.println("Failed to update employee with employee_id: " + employee.getEmployee_id())
        );
        return updatedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> updateName(String oldFullName, String newFirstName, String newLastName) {
        var updatedEmployees = employeeDao.updateName(oldFullName, newFirstName, newLastName);
        updatedEmployees.ifPresentOrElse(
                list -> {
                    System.out.println("Successfully updated employee names:");
                    list.forEach(System.out::println);
                },
                () -> System.out.println("Failed to update employee name for full name: " + oldFullName)
        );
        return updatedEmployees.orElse(Collections.emptyList());
    }

    @Override
    public List<Employees> updateByChoice(String fieldToUpdate, String oldValue, String newValue) {
        if (!validateFieldValue(fieldToUpdate, oldValue)) {
            System.out.println("Invalid old value for field: " + fieldToUpdate);
            return Collections.emptyList();
        }
        if (!validateFieldValue(fieldToUpdate, newValue)) {
            System.out.println("Invalid new value for field: " + fieldToUpdate);
            return Collections.emptyList();
        }

        var updatedEmployees = employeeDao.updateByChoice(fieldToUpdate, oldValue, newValue);
        updatedEmployees.ifPresentOrElse(
                list -> {
                    System.out.println("Successfully updated " + fieldToUpdate + ":");
                    list.forEach(System.out::println);
                },
                () -> System.out.println("Failed to update " + fieldToUpdate + " from " + oldValue + " to " + newValue)
        );
        return updatedEmployees.orElse(Collections.emptyList());
    }
}
