package br.com.ecommerce.ecommerceservice.repository;

import br.com.ecommerce.ecommerceservice.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {



}
