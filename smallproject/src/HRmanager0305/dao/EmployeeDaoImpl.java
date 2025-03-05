package HRmanager0305.dao;

import HRmanager0305.dto.Employees;
import HRmanager0305.config.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EmployeeDaoImpl implements EmployeeDao {
    Connection conn = null;
    PreparedStatement pstmt = null;

    @Override
    public <T> Optional<List<Employees>> findEmployee(String searchMenu, T searchValue) {
        Function<T, String> converter = v -> (v instanceof String) ? (String) v : String.valueOf(v);
        String strValue = converter.apply(searchValue);

        List<Employees> findEmployeeList = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM employees WHERE " + searchMenu + " = ?");
            pstmt.setString(1, strValue);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    // (필요시 성과 점수 등 추가 가능)
                    findEmployeeList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        return findEmployeeList.isEmpty() ? Optional.empty() : Optional.of(findEmployeeList);
    }

    @Override
    public Optional<List<Employees>> findJobHistory(Date startDate, Date endDate) {
        List<Employees> findJobHistoryList = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT e.* FROM employees e JOIN job_history j ON e.employee_id = j.employee_id " +
                    "WHERE (j.start_date BETWEEN ? AND ?) AND (j.end_date BETWEEN ? AND ? OR j.end_date IS NULL)");
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    findJobHistoryList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        return findJobHistoryList.isEmpty() ? Optional.empty() : Optional.of(findJobHistoryList);
    }

    @Override
    public Optional<List<Employees>> loadEmployees() {
        List<Employees> loadEmployeeList = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM employees");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    loadEmployeeList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        return loadEmployeeList.isEmpty() ? Optional.empty() : Optional.of(loadEmployeeList);
    }

    @Override
    public Optional<Employees> addEmployee(Employees employee) {
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String insertSql = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id) " +
                    "VALUES (?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, employee.getEmployee_id());
            pstmt.setString(2, employee.getFirst_name());
            pstmt.setString(3, employee.getLast_name());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhone_number());
            pstmt.setString(6, employee.getJob_id());
            pstmt.setBigDecimal(7, employee.getSalary());
            pstmt.setBigDecimal(8, employee.getCommission());
            pstmt.setInt(9, employee.getManager_id());
            pstmt.setInt(10, employee.getDepartment_id());

            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                String selectSql = "SELECT * FROM employees WHERE employee_id = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setInt(1, employee.getEmployee_id());
                    try (ResultSet rsSelect = selectStmt.executeQuery()) {
                        if (rsSelect.next()) {
                            Employees inserted = Employees.builder()
                                    .employee_id(rsSelect.getInt("employee_id"))
                                    .first_name(rsSelect.getString("first_name"))
                                    .last_name(rsSelect.getString("last_name"))
                                    .email(rsSelect.getString("email"))
                                    .phone_number(rsSelect.getString("phone_number"))
                                    .hire_date(rsSelect.getDate("hire_date"))
                                    .job_id(rsSelect.getString("job_id"))
                                    .salary(rsSelect.getBigDecimal("salary"))
                                    .commission(rsSelect.getBigDecimal("commission_pct"))
                                    .manager_id(rsSelect.getInt("manager_id"))
                                    .department_id(rsSelect.getInt("department_id"))
                                    .build();
                            return Optional.of(inserted);
                        }
                    }
                }
            } else {
                System.out.println("Failed to add employee information for employee_id: " + employee.getEmployee_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, rs);
            closeConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employees> deleteEmployee(int employeeId) {
        try {
            conn = DBUtil.getConnection();
            String deleteSql = "DELETE FROM employees WHERE employee_id = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, employeeId);
            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully deleted employee with employee_id: " + employeeId);
            } else {
                System.out.println("Failed to delete employee with employee_id: " + employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, null);
            closeConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employees> updateEmployee(Employees employee) {
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String updateSql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ?, commission_pct = ?, manager_id = ?, department_id = ? WHERE employee_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, employee.getFirst_name());
            updateStmt.setString(2, employee.getLast_name());
            updateStmt.setString(3, employee.getEmail());
            updateStmt.setString(4, employee.getPhone_number());
            updateStmt.setDate(5, employee.getHire_date());
            updateStmt.setString(6, employee.getJob_id());
            updateStmt.setBigDecimal(7, employee.getSalary());
            updateStmt.setBigDecimal(8, employee.getCommission());
            updateStmt.setInt(9, employee.getManager_id());
            updateStmt.setInt(10, employee.getDepartment_id());
            updateStmt.setInt(11, employee.getEmployee_id());

            int cnt = updateStmt.executeUpdate();
            if (cnt > 0) {
                String selectSql = "SELECT * FROM employees WHERE employee_id = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setInt(1, employee.getEmployee_id());
                rs = selectStmt.executeQuery();
                if (rs.next()) {
                    Employees updated = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"))
                            .build();
                    return Optional.of(updated);
                }
            } else {
                System.out.println("Failed to update employee information for employee_id: " + employee.getEmployee_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, selectStmt, rs);
            closeResources(null, updateStmt, null);
            closeConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Employees>> updateName(String oldFullName, String newFirstName, String newLastName) {
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        List<Employees> updatedList = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String updateSql = "UPDATE employees SET first_name = ?, last_name = ? WHERE CONCAT(first_name, ' ', last_name) = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newFirstName);
            updateStmt.setString(2, newLastName);
            updateStmt.setString(3, oldFullName);
            int updateCount = updateStmt.executeUpdate();
            if (updateCount > 0) {
                String selectSql = "SELECT * FROM employees WHERE first_name = ? AND last_name = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setString(1, newFirstName);
                selectStmt.setString(2, newLastName);
                rs = selectStmt.executeQuery();
                while (rs.next()) {
                    Employees employee = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"))
                            .build();
                    updatedList.add(employee);
                }
                if (updatedList.isEmpty()) {
                    System.out.println("No employee name updated for full name: " + oldFullName);
                    return Optional.empty();
                } else {
                    return Optional.of(updatedList);
                }
            } else {
                System.out.println("No employee name updated for full name: " + oldFullName);
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            closeResources(null, selectStmt, rs);
            closeResources(null, updateStmt, null);
            closeConnection(conn);
        }
    }

    @Override
    public Optional<List<Employees>> updateByChoice(String fieldToUpdate, String oldValue, String newValue) {
        List<String> allowedFields = List.of("first_name", "last_name", "email", "phone_number", "job_id", "salary", "commission_pct", "manager_id", "department_id");
        if (!allowedFields.contains(fieldToUpdate)) {
            System.out.println("Invalid update field: " + fieldToUpdate);
            return Optional.empty();
        }
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        List<Employees> updatedList = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String updateSql = "UPDATE employees SET " + fieldToUpdate + " = ? WHERE " + fieldToUpdate + " = ?";
            updateStmt = conn.prepareStatement(updateSql);
            if (fieldToUpdate.equals("salary") || fieldToUpdate.equals("commission_pct")) {
                updateStmt.setBigDecimal(1, new BigDecimal(newValue));
                updateStmt.setBigDecimal(2, new BigDecimal(oldValue));
            } else if (fieldToUpdate.equals("manager_id") || fieldToUpdate.equals("department_id")) {
                updateStmt.setInt(1, Integer.parseInt(newValue));
                updateStmt.setInt(2, Integer.parseInt(oldValue));
            } else {
                updateStmt.setString(1, newValue);
                updateStmt.setString(2, oldValue);
            }
            int updateCount = updateStmt.executeUpdate();
            if (updateCount > 0) {
                String selectSql = "SELECT * FROM employees WHERE " + fieldToUpdate + " = ?";
                selectStmt = conn.prepareStatement(selectSql);
                if (fieldToUpdate.equals("salary") || fieldToUpdate.equals("commission_pct")) {
                    selectStmt.setBigDecimal(1, new BigDecimal(newValue));
                } else if (fieldToUpdate.equals("manager_id") || fieldToUpdate.equals("department_id")) {
                    selectStmt.setInt(1, Integer.parseInt(newValue));
                } else {
                    selectStmt.setString(1, newValue);
                }
                rs = selectStmt.executeQuery();
                while (rs.next()) {
                    Employees employee = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"))
                            .build();
                    updatedList.add(employee);
                }
                if (updatedList.isEmpty()) {
                    System.out.println("No update performed on " + fieldToUpdate + " from " + oldValue + " to " + newValue);
                    return Optional.empty();
                } else {
                    return Optional.of(updatedList);
                }
            } else {
                System.out.println("No update performed on " + fieldToUpdate + " from " + oldValue + " to " + newValue);
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            closeResources(null, selectStmt, rs);
            closeResources(null, updateStmt, null);
            closeConnection(conn);
        }
    }

    private void closeResources(Connection connection, PreparedStatement statement, ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if(statement != null) {
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void closeConnection(Connection connection) {
        if(connection != null) {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}