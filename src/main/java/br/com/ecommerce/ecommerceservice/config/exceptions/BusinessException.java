package br.com.ecommerce.ecommerceservice.config.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String property;
    private String message;
    private Object[] values;

    public BusinessException(String property, String message) {
        this.property = property;
        this.message = message;
    }
}