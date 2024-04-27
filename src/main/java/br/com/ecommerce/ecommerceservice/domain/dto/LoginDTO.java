package br.com.ecommerce.ecommerceservice.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    @Size(min = 1, max = 255)
    private String email;
    @NotNull
    @Size(min = 1, max = 255)
    private String password;
}
