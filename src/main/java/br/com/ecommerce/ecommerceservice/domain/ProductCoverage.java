package br.com.ecommerce.ecommerceservice.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_product_coverage")
public class ProductCoverage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name="creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name="modification_date")
    @CreationTimestamp
    private LocalDateTime modificationDate;

    @Column(name="cep")
    private String cep;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
