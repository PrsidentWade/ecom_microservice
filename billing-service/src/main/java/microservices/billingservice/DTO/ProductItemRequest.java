package microservices.billingservice.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductItemRequest {
    private Long productId;
    private Double price;
    private Integer quantite;
}
