package microservices.billingservice.DTO;

import lombok.Builder;
import lombok.Data;
import microservices.billingservice.Model.Product;

@Data
@Builder
public class ProductItemResponse {
    private Long id;
    private Double price;
    private Integer quantite;
    private Product product;
}