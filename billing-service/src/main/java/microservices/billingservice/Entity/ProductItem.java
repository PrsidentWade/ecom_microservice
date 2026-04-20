package microservices.billingservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import microservices.billingservice.Model.Product;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Double price;
    private Integer quantite;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bill bill;
    @Transient
    private Product product;
}
