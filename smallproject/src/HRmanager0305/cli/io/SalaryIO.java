package HRmanager0305.cli.io;

import HRmanager0305.util.validation.ConsoleInputValidator;
import java.math.BigDecimal;

public class SalaryIO {
    private ConsoleInputValidator validator = new ConsoleInputValidator();

    // 직원 ID 입력. 직원 ID는 0부터 10 사이여야 한다.
    public int readEmployeeId() {
        return validator.readValidatedInt("employee_id(int) : ", 0, 10, "Please Check Employee ID.");
    }

    // 회사 예산 입력. 예산은 음수가 아니어야 한다.
    public double readBudget() {
        return validator.readValidated("Enter company budget: ", Double::parseDouble, d -> d >= 0, "Budget must be non-negative.");
    }

    // 연봉 인상률 입력. 인상률은 음수가 아니어야 한다.
    public double readPercentage() {
        return validator.readValidated("Enter percentage for salary raise: ", Double::parseDouble, d -> d >= 0, "Percentage must be non-negative.");
    }

    // job_id 입력. job_id는 0부터 10 사이여야 한다.
    public int readJobId() {
        return validator.readValidatedInt("job_id(int) : ", 0, 10, "Please Check Job ID.");
    }

    // department_id 입력. department_id는 0부터 10 사이여야 한다.
    public int readDepartmentId() {
        return validator.readValidatedInt("department_id(int) : ", 0, 10, "Please Check Department ID.");
    }

    // 성과 등급 입력. S, A, B, C, D 중 하나여야 한다.
    public char readPerformanceGrade() {
        return validator.readValidated("Enter performance grade (S, A, B, C, D): ", input -> {
            String upper = input.toUpperCase();
            if (upper.matches("[SABCD]") && upper.length() == 1) {
                return upper.charAt(0);
            } else {
                throw new IllegalArgumentException();
            }
        }, grade -> true, "Please enter a valid grade (S, A, B, C, D).");
    }

    // 급여 입력. 급여는 0.00부터 9999999.99 사이이고 소수점 2자리여야 한다.
    public double readSalary() {
        return validator.readValidatedBigDecimal("salary : ", new BigDecimal("0.00"), new BigDecimal("9999999.99"), 2, "Please Check Salary.").doubleValue();
    }

    // 세금 금액 입력. 세금 금액은 음수가 아니어야 한다.
    public double readTaxAmount() {
        return validator.readValidated("Enter tax amount: ", Double::parseDouble, d -> d >= 0, "Tax amount must be non-negative.");
    }

    // 오버타임 시간 입력. 오버타임 시간은 음수가 아니어야 한다.
    public double readOvertimeHours() {
        return validator.readValidated("Enter overtime hours: ", Double::parseDouble, h -> h >= 0, "Overtime hours must be non-negative.");
    }
}