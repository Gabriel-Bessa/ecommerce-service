package br.com.ecommerce.ecommerceservice.config;

import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.config.exceptions.FieldErrorDTO;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public List<FieldErrorDTO> error(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationServiceException.class)
    public List<FieldErrorDTO> errorBusiness(AuthenticationServiceException exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), exception.getLocalizedMessage());
        return Collections.singletonList(error);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FieldErrorDTO> errorBusiness(HttpMessageNotReadableException exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<FieldErrorDTO> methodNoSupported(HttpRequestMethodNotSupportedException exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), String.format("O método %s para esse endpoint não é suportador pelo sistema", exception.getMethod()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}