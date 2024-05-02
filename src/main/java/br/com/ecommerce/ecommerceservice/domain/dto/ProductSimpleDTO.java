package br.com.ecommerce.ecommerceservice.domain.dto;

import br.com.ecommerce.ecommerceservice.domain.enuns.ProductType;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private String cep;
    @NotNull
    @Range(min = 1, max = 1000000)
    private BigDecimal value;
    @NotBlank
    @Size(max = 255)
    private String description;
    @NotBlank
    @Size(max = 255)
    private String name;
    private String imgUrl;
    private Set<String> availableAreas;

}
