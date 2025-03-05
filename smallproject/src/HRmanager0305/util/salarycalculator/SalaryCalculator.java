package HRmanager0305.util.salarycalculator;

import HRmanager0305.dto.Employees;

public class SalaryCalculator {

    // 연봉에 따른 세금 계산 (지방세 포함)
    public static double calculateSalTax(double annualSalary) {
        double tax = 0.0;
        if (annualSalary <= 14000000) {
            tax = annualSalary * 0.06;
        } else if (annualSalary <= 50000000) {
            tax = 14000000 * 0.06 + (annualSalary - 14000000) * 0.15;
        } else if (annualSalary <= 88000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (annualSalary - 50000000) * 0.24;
        } else if (annualSalary <= 150000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (annualSalary - 88000000) * 0.35;
        } else if (annualSalary <= 300000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (annualSalary - 150000000) * 0.38;
        } else if (annualSalary <= 500000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (annualSalary - 300000000) * 0.4;
        } else if (annualSalary <= 1000000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4
                    + (annualSalary - 500000000) * 0.42;
        } else {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4
                    + (1000000000 - 50000000) * 0.42 + (annualSalary - 1000000000) * 0.45;
        }
        double localTax = tax * 0.1;
        return tax + localTax;
    }

    // 성과 등급 산출 (Employees의 perfScore 필드를 기반으로)
    public static char getPerfGrade(Employees emp) {
        int score = emp.getPerfScore();
        int scoreOneDigit = score / 10;
        if (scoreOneDigit >= 10) return 'S';
        switch (scoreOneDigit) {
            case 9:
                return (score >= 95) ? 'S' : 'A';
            case 8:
                return 'B';
            case 7:
                return (score >= 75) ? 'B' : 'C';
            default:
                return 'D';
        }
    }
}