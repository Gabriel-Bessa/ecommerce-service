package br.com.ecommerce.ecommerceservice.config;

import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.config.exceptions.FieldErrorDTO;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationErrorHandler {

    private final MessageSource ms;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<FieldErrorDTO> errorBusiness(BusinessException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorDTO(ms.getMessage(ex.getProperty(), null, locale), ms.getMessage(ex.getMessage(), ex.getValues(), locale)));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDTO> error(MethodArgumentNotValidException expection) {
        List<FieldError> fieldErrors = expection.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
    }
}