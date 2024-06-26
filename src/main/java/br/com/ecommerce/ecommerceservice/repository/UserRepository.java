package br.com.ecommerce.ecommerceservice.repository;

import br.com.ecommerce.ecommerceservice.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

}
