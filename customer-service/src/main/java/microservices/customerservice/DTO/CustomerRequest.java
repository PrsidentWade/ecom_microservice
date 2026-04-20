package microservices.customerservice.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {
    private String firstname;
    private String lastname;
    private String email;
}
