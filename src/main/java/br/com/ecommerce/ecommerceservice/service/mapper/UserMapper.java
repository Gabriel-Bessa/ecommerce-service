package br.com.ecommerce.ecommerceservice.service.mapper;

import br.com.ecommerce.ecommerceservice.domain.User;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDetailsDTO toDetailsDto(User user);

    UserDTO toDto(User user);
}
