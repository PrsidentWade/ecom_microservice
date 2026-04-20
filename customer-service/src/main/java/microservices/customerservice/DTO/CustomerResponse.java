package microservices.customerservice.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
