package br.com.ecommerce.ecommerceservice.service.mapper;

import br.com.ecommerce.ecommerceservice.domain.User;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDetailsDTO toDto(User user);
}
