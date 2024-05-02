package br.com.ecommerce.ecommerceservice.repository;

import br.com.ecommerce.ecommerceservice.domain.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    List<Role> findByKey(String key);

}
