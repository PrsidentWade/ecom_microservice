package microservices.billingservice.DTO;

import lombok.Builder;
import lombok.Data;
import microservices.billingservice.Entity.ProductItem;
import microservices.billingservice.Model.Customer;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder

public class BillResponse {
    private Long id;
    private LocalDate date;
    private Customer customer;
    private List<ProductItemResponse> items;
}
