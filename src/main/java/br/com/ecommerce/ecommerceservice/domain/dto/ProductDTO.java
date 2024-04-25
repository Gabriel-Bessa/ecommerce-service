package br.com.ecommerce.ecommerceservice.domain.dto;

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
public class ProductDTO extends ProductSimpleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
}
