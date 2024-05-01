package br.com.ecommerce.ecommerceservice.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    @Size(min = 1, max = 255)
    private String email;

    @CPF
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
    @NotBlank
    @Size(min = 1, max = 255)
    private String password;
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    private LocalDateTime birthDate;
    @NotBlank
    @Size(min = 7, max = 15)
    private String cep;
    @NotBlank
    @Size(min = 7, max = 255)
    private String street;
    @NotBlank
    @Size(min = 3, max = 255)
    private String neighborhood;
    @NotBlank
    @Size(min = 1, max = 20)
    private String number;
    @NotBlank
    @Size(min = 1, max = 255)
    private String city;
    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;
    @NotBlank
    @Size(min = 1, max = 255)
    private String complement;


}
