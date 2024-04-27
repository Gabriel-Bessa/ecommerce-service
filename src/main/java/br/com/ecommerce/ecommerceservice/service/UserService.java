package br.com.ecommerce.ecommerceservice.service;

import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.domain.User;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDTO;
import br.com.ecommerce.ecommerceservice.repository.UserRepository;
import br.com.ecommerce.ecommerceservice.service.mapper.UserMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username).orElseThrow(() -> new BusinessException("user.error", "user.not.found"));
        return mapper.toDto(user);
    }

    @Transactional
    public void createUser(UserDTO dto) {
        User entity = mapper.toEntity(dto);
        if (repository.existsByEmail(entity.getEmail())) {
            throw new BusinessException("user.error", "user.email.already.exists");
        }
        entity.setId(UUID.randomUUID().toString());
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
    }
}
