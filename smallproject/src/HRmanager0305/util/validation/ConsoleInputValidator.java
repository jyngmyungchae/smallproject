package HRmanager0305.util.validation;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConsoleInputValidator implements Validator {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public <T> T readValidated(String prompt, Function<String, T> parser, Predicate<T> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                T result = parser.apply(input);
                if (validator.test(result)) {
                    return result;
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(errorMessage);
            }
            System.out.println(errorMessage);
        }
    }

    public int readValidatedInt(String prompt, int min, int max, String errorMessage) {
        return readValidated(prompt, Integer::parseInt, i -> i >= min && i <= max, errorMessage);
    }
    // int korean = readValidatedInt("employee_id : ", 0, 10);

    public String readValidatedVarchar(String prompt, int maxLength, String errorMessage) {
        return readValidated(prompt, s -> s, s -> s.length() <= maxLength, errorMessage);
    }

    // String name = readValidatedVarchar("이름 (최대 20자): ", 20, "20자 이하로 입력하세요.");

    public BigDecimal readValidatedBigDecimal(String prompt,
                                              BigDecimal min,
                                              BigDecimal max,
                                              int scale,
                                              String errorMessage) {
        return readValidated(prompt, BigDecimal::new,
                bd -> bd.scale() == scale && bd.compareTo(min) >= 0 && bd.compareTo(max) <= 0,
                errorMessage);
    }

    public static boolean validateFieldValue(String field, String value) {
        if (value == null) return false;
        switch (field) {
            case "first_name":
                return value.length() <= 20;
            case "last_name":
                return value.length() <= 25;
            case "email":
                return value.length() <= 25;
            case "hire_date":
                try {
                    java.sql.Date.valueOf(value);
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            case "salary":
                try {
                    var bd = new BigDecimal(value);
                    return bd.scale() == 2 &&
                            bd.compareTo(new BigDecimal("0.00")) >= 0 &&
                            bd.compareTo(new BigDecimal("999999.99")) <= 0;
                } catch (Exception e) {
                    return false;
                }
            case "commission_pct":
                try {
                    var bd = new BigDecimal(value);
                    return bd.scale() == 2 &&
                            bd.compareTo(new BigDecimal("0.00")) >= 0 &&
                            bd.compareTo(new BigDecimal("99.99")) <= 0;
                } catch (Exception e) {
                    return false;
                }
            case "manager_id":
                return Integer.parseInt(value) > 0;
            case "department_id":
                return Integer.parseInt(value) > 0;
            default:
                System.out.println("No validation rule for field: " + field);
                return false;
        }
    }


}
