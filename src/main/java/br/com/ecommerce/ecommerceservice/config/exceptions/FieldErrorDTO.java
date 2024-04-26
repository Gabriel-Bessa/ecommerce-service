package br.com.ecommerce.ecommerceservice.config.exceptions;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String field;
    private String msg;
}
