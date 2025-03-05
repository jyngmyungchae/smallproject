package HRmanager0305.service;

public interface SalaryService {
    double getSalaryAfterTax(int employeeId);
    double getAnnualSalary(int employeeId);
    double getAvgAnnSalJobId(String jobId);
    double getAverageSalaryDpt(int departmentId);
    boolean isBudgetExceeded(double budget);
    double simulateAnnSalaryRaise(int employeeId, double percentage);
    double getIncreasedAnnualSalary(int employeeId, char performanceGrade);

    double calculateRetirementPay(int employeeId);
    boolean applySalaryIncrease(int employeeId, double rate);
    double calculateBonus(int employeeId, double performanceFactor);
    double calculateOvertimePay(int employeeId, double overtimeHours);
    int applyBatchSalaryIncrease(double percentage);
    boolean updateEmployeeSalary(int employeeId, double newSalary);
}