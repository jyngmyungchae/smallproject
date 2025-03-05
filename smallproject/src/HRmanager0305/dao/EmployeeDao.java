package HRmanager0305.dao;

import HRmanager0305.dto.Employees;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    <T> Optional<List<Employees>> findEmployee(String searchMenu, T searchValue);
    Optional<List<Employees>> findJobHistory(Date startDate, Date endDate);
    Optional<List<Employees>> loadEmployees();
    Optional<Employees> addEmployee(Employees employee);
    Optional<Employees> deleteEmployee(int employeeId);
    Optional<Employees> updateEmployee(Employees employee);
    Optional<List<Employees>> updateName(String oldFullName, String newFirstName, String newLastName);
    Optional<List<Employees>> updateByChoice(String fieldToUpdate, String oldValue, String newValue);
}