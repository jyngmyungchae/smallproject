package HRmanager0305.service;

import HRmanager0305.dao.EmployeeDaoImpl;
import HRmanager0305.dto.Employees;
import HRmanager0305.util.salarycalculator.SalaryCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaryServiceImpl implements SalaryService {
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    // 직원 ID로 단일 직원 조회
    private Optional<Employees> findEmployee(int employeeId) {
        Optional<List<Employees>> optList = employeeDao.findEmployee("employee_id", employeeId);
        if (optList.isPresent() && !optList.get().isEmpty()) {
            return Optional.of(optList.get().get(0));
        }
        return Optional.empty();
    }

    // 전체 직원 목록 조회
    private List<Employees> getAllEmployees() {
        return employeeDao.loadEmployees().orElse(new ArrayList<>());
    }

    // 월급(세전) 조회
    private double getSalaryBeforeTax(int employeeId) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        return optEmp.map(emp -> emp.getSalary().doubleValue()).orElse(0.0);
    }

    @Override
    public double getSalaryAfterTax(int employeeId) {
        double annualSalary = getAnnualSalary(employeeId);
        double tax = SalaryCalculator.calculateSalTax(annualSalary);
        return annualSalary - tax;
    }

    @Override
    public double getAnnualSalary(int employeeId) {
        double monthlySalary = getSalaryBeforeTax(employeeId);
        return monthlySalary * 12;
    }

    @Override
    public double getAvgAnnSalJobId(String jobId) {
        List<Employees> allEmployees = getAllEmployees();
        List<Employees> matchedEmployees = new ArrayList<>();
        for (Employees emp : allEmployees) {
            if (jobId.equals(emp.getJob_id())) {
                matchedEmployees.add(emp);
            }
        }
        double totalSal = matchedEmployees.stream()
                .mapToDouble(emp -> emp.getSalary().doubleValue() * 12)
                .sum();
        int count = matchedEmployees.size();
        return count > 0 ? totalSal / count : 0.0;
    }

    @Override
    public double getAverageSalaryDpt(int departmentId) {
        List<Employees> allEmployees = getAllEmployees();
        List<Employees> matchedEmployees = new ArrayList<>();
        for (Employees emp : allEmployees) {
            if (departmentId == emp.getDepartment_id()) {
                matchedEmployees.add(emp);
            }
        }
        double totalSal = matchedEmployees.stream()
                .mapToDouble(emp -> emp.getSalary().doubleValue() * 12)
                .sum();
        int count = matchedEmployees.size();
        return count > 0 ? totalSal / count : 0.0;
    }

    @Override
    public double simulateAnnSalaryRaise(int employeeId, double percentage) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double currentAnnual = optEmp.get().getSalary().doubleValue() * 12;
            return currentAnnual * (1 + percentage / 100.0);
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double annualSalary = optEmp.get().getSalary().doubleValue() * 12;
            // 성과 등급은 SalaryCalculator를 통해 산출
            char grade = SalaryCalculator.getPerfGrade(optEmp.get());
            double raisePercentage;
            switch (grade) {
                case 'S':
                    raisePercentage = 0.15;
                    break;
                case 'A':
                    raisePercentage = 0.10;
                    break;
                case 'B':
                    raisePercentage = 0.05;
                    break;
                case 'C':
                    raisePercentage = 0.03;
                    break;
                case 'D':
                    raisePercentage = 0.01;
                    break;
                default:
                    System.out.println("Invalid grade. Please check the employee's performance grade again.");
                    return annualSalary;
            }
            return annualSalary * (1 + raisePercentage);
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public boolean isBudgetExceeded(double budget) {
        double totalAnnualSalary = 0;
        for (Employees emp : getAllEmployees()) {
            totalAnnualSalary += emp.getSalary().doubleValue() * 12;
        }
        return totalAnnualSalary > budget;
    }

    // ───────── 추가 기능 ─────────

    // 퇴직금 계산: 최근 3달치 월급의 80% 산출
    public double calculateRetirementPay(int employeeId) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            return monthlySalary * 3 * 0.8;
        } else {
            System.out.println("Employee not found.");
            return -1.0;
        }
    }

    // 연봉 인상 적용: 인상률(%) 적용 후 급여 수정, 업데이트 성공 시 true 반환
    public boolean applySalaryIncrease(int employeeId, double rate) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            Employees emp = optEmp.get();
            double newSalary = emp.getSalary().doubleValue() * (1 + rate / 100.0);
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            return updated.isPresent();
        } else {
            System.out.println("Employee not found.");
            return false;
        }
    }

    // 보너스 계산: 월급의 일정 퍼센트(%)를 보너스로 산출
    public double calculateBonus(int employeeId, double performanceFactor) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            return monthlySalary * performanceFactor / 100.0;
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    // 오버타임 급여 계산: 시간당 급여 산출 후, 추가 근무시간과 1.5배 배율 적용
    public double calculateOvertimePay(int employeeId, double overtimeHours) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            double hourlyWage = monthlySalary / 160.0; // 기준 160시간/월
            return hourlyWage * overtimeHours * 1.5;
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    // 전체 직원에 대해 일괄 연봉 인상 적용: 인상률(%) 적용 후 업데이트된 직원 수 반환
    public int applyBatchSalaryIncrease(double percentage) {
        List<Employees> allEmployees = getAllEmployees();
        int updatedCount = 0;
        for (Employees emp : allEmployees) {
            double newSalary = emp.getSalary().doubleValue() * (1 + percentage / 100.0);
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            if (updated.isPresent()) {
                updatedCount++;
            }
        }
        return updatedCount;
    }

    // 직원 급여 직접 수정: 새로운 급여를 설정하고 업데이트, 성공 시 true 반환
    public boolean updateEmployeeSalary(int employeeId, double newSalary) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            Employees emp = optEmp.get();
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            return updated.isPresent();
        } else {
            System.out.println("Employee not found.");
            return false;
        }
    }
}