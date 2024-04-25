package br.com.ecommerce.ecommerceservice.domain.dto;

import br.com.ecommerce.ecommerceservice.domain.enuns.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ProductSimpleDTO implements Serializable {

    @NotNull
    private ProductType type;
    @NotNull
    private Boolean needsConfirmation;
    @NotNull
    @Range(min = 1, max = 1000000)
    private BigDecimal value;
    @NotBlank
    @Size(max = 255)
    private String description;

}
