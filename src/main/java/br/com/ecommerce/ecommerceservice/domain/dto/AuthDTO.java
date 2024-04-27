package br.com.ecommerce.ecommerceservice.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime expirationDate;
    private String token;
}
