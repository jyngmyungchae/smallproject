package HRmanager0305.cli.controller;

import HRmanager0305.cli.io.SalaryIO;
import HRmanager0305.service.SalaryService;
import HRmanager0305.service.SalaryServiceImpl;
import java.util.Scanner;

public class SalaryController {

    private SalaryService salaryService = new SalaryServiceImpl();
    private SalaryIO salaryIO = new SalaryIO();

    public static void main(String[] args) {
        SalaryController controller = new SalaryController();
        controller.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("========== 급여 메뉴 ==========");
            System.out.println("1. 세후 연봉 계산");
            System.out.println("2. 연봉 계산");
            System.out.println("3. 직원 급여 확인");
            System.out.println("4. 직무별 평균 연봉 계산");
            System.out.println("5. 부서별 평균 연봉 계산");
            System.out.println("6. 연봉 인상 시뮬레이션");
            System.out.println("7. 성과 등급에 따른 연봉 인상 계산");
            System.out.println("8. 예산 초과 여부 확인");
            System.out.println("9. 퇴직금 계산");
            System.out.println("10. 연봉 인상 적용");
            System.out.println("11. 보너스 계산");
            System.out.println("12. 오버타임 급여 계산");
            System.out.println("13. 전체 직원 일괄 연봉 인상 적용");
            System.out.println("14. 직원 급여 직접 수정");
            System.out.println("15. 종료");
            System.out.print("옵션을 선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    calculateSalaryAfterTax();
                    break;
                case 2:
                    calculateAnnualSalary();
                    break;
                case 3:
                    checkEmployeeSalaryDetails();
                    break;
                case 4:
                    calculateAvgSalaryByJobId();
                    break;
                case 5:
                    calculateAvgSalaryByDeptId();
                    break;
                case 6:
                    simulateAnnualRaise();
                    break;
                case 7:
                    calculateIncreasedSalary();
                    break;
                case 8:
                    checkBudgetExceeded();
                    break;
                case 9:
                    calculateRetirementPay();
                    break;
                case 10:
                    applySalaryIncrease();
                    break;
                case 11:
                    calculateBonus();
                    break;
                case 12:
                    calculateOvertimePay();
                    break;
                case 13:
                    applyBatchSalaryIncrease();
                    break;
                case 14:
                    updateEmployeeSalary();
                    break;
                case 15:
                    exitProgram = true;
                    System.out.println("급여 메뉴를 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 옵션입니다. 다시 시도하세요.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private void calculateSalaryAfterTax() {
        int empId = salaryIO.readEmployeeId();
        double afterTax = salaryService.getSalaryAfterTax(empId);
        System.out.println("직원 " + empId + "의 세후 연봉: " + afterTax);
    }

    private void calculateAnnualSalary() {
        int empId = salaryIO.readEmployeeId();
        double annualSalary = salaryService.getAnnualSalary(empId);
        System.out.println("직원 " + empId + "의 연봉: " + annualSalary);
    }

    private void checkEmployeeSalaryDetails() {
        int empId = salaryIO.readEmployeeId();
        double annualSalary = salaryService.getAnnualSalary(empId);
        double monthlySalary = annualSalary / 12;
        double afterTax = salaryService.getSalaryAfterTax(empId);

        System.out.println("직원 " + empId + " 급여 상세 내역");
        System.out.println("월급(세전): " + monthlySalary);
        System.out.println("연봉(세전): " + annualSalary);
        System.out.println("연봉(세후): " + afterTax);
    }

    private void calculateAvgSalaryByJobId() {
        int jobIdInt = salaryIO.readJobId();
        String jobId = String.valueOf(jobIdInt);
        double avgSalary = salaryService.getAvgAnnSalJobId(jobId);
        System.out.println("직무 " + jobId + "의 평균 연봉: " + avgSalary);
    }

    private void calculateAvgSalaryByDeptId() {
        int deptId = salaryIO.readDepartmentId();
        double avgSalary = salaryService.getAverageSalaryDpt(deptId);
        System.out.println("부서 " + deptId + "의 평균 연봉: " + avgSalary);
    }

    private void simulateAnnualRaise() {
        int empId = salaryIO.readEmployeeId();
        double percentage = salaryIO.readPercentage();
        double simulatedSalary = salaryService.simulateAnnSalaryRaise(empId, percentage);
        System.out.println("직원 " + empId + "의 연봉 인상 시뮬레이션 (" + percentage + "% 인상): " + simulatedSalary);
    }

    private void calculateIncreasedSalary() {
        int empId = salaryIO.readEmployeeId();
        char grade = salaryIO.readPerformanceGrade();
        double increasedSalary = salaryService.getIncreasedAnnualSalary(empId, grade);
        System.out.println("직원 " + empId + "의 성과 등급 " + grade + "에 따른 인상 연봉: " + increasedSalary);
    }

    private void checkBudgetExceeded() {
        double budget = salaryIO.readBudget();
        boolean exceeded = salaryService.isBudgetExceeded(budget);
        if (exceeded) {
            System.out.println("회사의 총 연봉이 예산을 초과합니다.");
        } else {
            System.out.println("회사의 총 연봉이 예산 이내입니다.");
        }
    }

    private void calculateRetirementPay() {
        int empId = salaryIO.readEmployeeId();
        double retirementPay = salaryService.calculateRetirementPay(empId);
        System.out.println("직원 " + empId + "의 퇴직금: " + retirementPay);
    }

    private void applySalaryIncrease() {
        int empId = salaryIO.readEmployeeId();
        double rate = salaryIO.readPercentage();
        boolean result = salaryService.applySalaryIncrease(empId, rate);
        if (result) {
            System.out.println("직원 " + empId + "의 급여가 인상되었습니다.");
        } else {
            System.out.println("급여 인상 적용에 실패하였습니다.");
        }
    }

    private void calculateBonus() {
        int empId = salaryIO.readEmployeeId();
        double performanceFactor = salaryIO.readPercentage();
        double bonus = salaryService.calculateBonus(empId, performanceFactor);
        System.out.println("직원 " + empId + "의 보너스: " + bonus);
    }

    private void calculateOvertimePay() {
        int empId = salaryIO.readEmployeeId();
        double overtimeHours = salaryIO.readOvertimeHours();
        double overtimePay = salaryService.calculateOvertimePay(empId, overtimeHours);
        System.out.println("직원 " + empId + "의 오버타임 급여: " + overtimePay);
    }

    private void applyBatchSalaryIncrease() {
        double percentage = salaryIO.readPercentage();
        int updatedCount = salaryService.applyBatchSalaryIncrease(percentage);
        System.out.println("전체 직원 중 " + updatedCount + "명의 급여가 인상되었습니다.");
    }

    private void updateEmployeeSalary() {
        int empId = salaryIO.readEmployeeId();
        double newSalary = salaryIO.readSalary();
        boolean result = salaryService.updateEmployeeSalary(empId, newSalary);
        if (result) {
            System.out.println("직원 " + empId + "의 급여가 " + newSalary + "으로 수정되었습니다.");
        } else {
            System.out.println("급여 수정에 실패하였습니다.");
        }
    }
}