package HRmanager0305.cli.io;

import HRmanager0305.dto.Employees;
import HRmanager0305.util.validation.ConsoleInputValidator;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class EmployeeIO {
    ConsoleInputValidator validator = new ConsoleInputValidator();
    // EmployeeIO에서 Scanner도 사용할 수 있음 (validator 내부 Scanner와 별개)
    private Scanner scanner = new Scanner(System.in);

    public int readEmployeeId() {
        return validator.readValidatedInt("employee_id(int) : ", 0, 999, "Please Check Employee ID");
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Integer readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    public String readFirstName() {
        return validator.readValidatedVarchar("first_name(max: 20) : ", 20, "Please Check First Name");
    }

    public String readLastName() {
        return validator.readValidatedVarchar("last_name(max: 25) : ", 25, "Please Check Last Name");
    }

    public String readEmail() {
        return validator.readValidatedVarchar("email(max: 25) : ", 25, "Please Check Email");
    }

    public Date readHireDate() {
        System.out.print("enter date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            // 간단하게 java.sql.Date.valueOf() 사용 (입력 형식이 정확해야 함)
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            // 형식이 틀리면 재입력하도록 예외 처리 (혹은 RuntimeException 발생)
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return readHireDate();
        }
    }

    public String readJobId() {
        return validator.readValidatedVarchar("job_id(max: 10) : ", 10, "Please Check Job ID");
    }

    public BigDecimal readSalary() {
        return validator.readValidatedBigDecimal("salary : ", new BigDecimal("0.00"), new BigDecimal("999999.99"), 2, "Please Check Salary");
    }

    public BigDecimal readCommissionPct() {
        return validator.readValidatedBigDecimal("commission_pct : ", new BigDecimal("0.00"), new BigDecimal("99.99"), 2, "Please Check Commission Percent");
    }

    public int readManagerId() {
        return validator.readValidatedInt("manager_id(int) : ", 0, 10, "Please Check Manager ID");
    }

    public int readDepartmentId() {
        return validator.readValidatedInt("department_id(int) : ", 0, 10, "Please Check Department ID");
    }

    public Date readStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        return validator.<Date>readValidated(
                "Enter start date (yyyy-MM-dd): ",
                input -> {
                    try {
                        return (Date) dateFormat.parse(input);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Invalid date format.");
                    }
                },
                date -> true, // 유효성 검사 추가
                "Invalid date format. Please enter in yyyy-MM-dd format."
        );
    }


    public Date readEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        return validator.<Date>readValidated(
                "Enter end date (yyyy-MM-dd): ",
                input -> {
                    try {
                        return (Date) dateFormat.parse(input);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Invalid date format.");
                    }
                },
                date -> true, // 유효성 검사 추가
                "Invalid date format. Please enter in yyyy-MM-dd format."
        );
    }

    public void printSearchSubmenu2(List<Employees> searchList) {
        if (searchList.isEmpty()) {
            System.out.println("No matching employees found.");
        } else {
            System.out.println("Result applicable employees:");
            for (Employees employee : searchList) {
                System.out.println(employee);
            }
        }
    }


    public void printSearchSubmenu1(int count) {
        System.out.println("Total Employees Found: " + count);
    }

    public void printSortSubMenu(List<Employees> sortedList) {
        System.out.println("Sorted Employee List:");
        for (Employees employee : sortedList) {
            System.out.println(employee);
        }
    }

    public int readSubMenuChoice() {
        return validator.readValidated(
                "Enter sub menu choice (1 or 2): ",
                input -> {
                    try {
                        return Integer.parseInt(input); // 입력을 정수로 변환
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid input. Please enter 1 or 2.");
                    }
                },
                choice -> choice == 1 || choice == 2, // 1 또는 2만 허용
                "Invalid choice. Please enter 1 or 2."
        );
    }
}