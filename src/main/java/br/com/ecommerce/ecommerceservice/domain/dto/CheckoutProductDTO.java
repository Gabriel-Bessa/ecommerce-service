package br.com.ecommerce.ecommerceservice.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank
    @Size(min = 35, max = 36)
    private String id;
    @NotNull
    @Range(min = 1, max = 10)
    private Integer quantity;
}
