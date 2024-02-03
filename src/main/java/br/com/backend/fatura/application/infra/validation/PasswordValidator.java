package br.com.backend.fatura.application.infra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(ValidPassword validPassword) {
        this.password = validPassword.password();
        this.confirmPassword = validPassword.confirmPassword();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(obj).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(obj).getPropertyValue(confirmPassword);

        if (passwordValue != null) {
            return passwordValue.equals(confirmPasswordValue);
        }
        return false;
    }
}
