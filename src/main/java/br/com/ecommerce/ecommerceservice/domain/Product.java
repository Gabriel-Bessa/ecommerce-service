package br.com.ecommerce.ecommerceservice.domain;

import br.com.ecommerce.ecommerceservice.domain.enuns.ProductType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_products")
public class Product implements Serializable {
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

    @Column(name = "name")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "needs_confirmation")
    private Boolean needsConfirmation;
}
