package microservices.billingservice.DTO;

import lombok.Builder;
import lombok.Data;
import microservices.billingservice.Entity.ProductItem;

import java.util.List;
@Data
@Builder

public class BillinRequest {
    private Long customerId;
    private List<ProductItemRequest> items;
}
