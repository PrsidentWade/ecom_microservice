package microservices.discoveryservice.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer quantite;
}
