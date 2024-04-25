package br.com.ecommerce.ecommerceservice.repository;

import br.com.ecommerce.ecommerceservice.domain.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String>, JpaSpecificationExecutor<Product> {
}
