package br.com.ecommerce.ecommerceservice.domain;

import br.com.ecommerce.ecommerceservice.domain.enuns.PurchaseStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_purchases")
public class Purchase implements Serializable {
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<CoverageAnalysis> coverageAnalyses;
}
