package br.com.ecommerce.ecommerceservice.service.mapper;

import br.com.ecommerce.ecommerceservice.domain.Product;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductSimpleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductSimpleDTO dto);

    ProductDTO toDto(Product product);
}
