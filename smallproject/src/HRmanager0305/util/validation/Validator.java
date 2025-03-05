package HRmanager0305.util.validation;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Validator {
    <T> T readValidated(String prompt, Function<String, T> parser, Predicate<T> validator, String errorMessage);
}