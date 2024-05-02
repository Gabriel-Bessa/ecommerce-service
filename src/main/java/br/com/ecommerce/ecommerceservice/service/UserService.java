package br.com.ecommerce.ecommerceservice.service;

import br.com.ecommerce.ecommerceservice.config.SecurityUtils;
import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.domain.User;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import br.com.ecommerce.ecommerceservice.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByCpf(username).orElseThrow(() -> new BusinessException("user.error", "user.not.found"));
        return mapper.toDetailsDto(user);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new BusinessException("user.error", "user.not.found"));
    }

    public UserDetailsDTO me() {
        return SecurityUtils.getAuthUser();
    }

    @Transactional
    public void createUser(UserDTO dto) {
        User entity = mapper.toEntity(dto);
        if (repository.existsByEmail(entity.getEmail())) {
            throw new BusinessException("user.error", "user.email.already.exists");
        }
        if (repository.existsByCpf(entity.getCpf())) {
            throw new BusinessException("user.error", "user.cpf.already.exists");
        }
        entity.setId(UUID.randomUUID().toString());
        entity.setPassword(encoder.encode(dto.getPassword()));
        setUserRoles(entity);
        repository.save(entity);
    }

    private void setUserRoles(User entity) {
        entity.setRoles(roleRepository.findByKey("ROLE_USER"));
    }
}
