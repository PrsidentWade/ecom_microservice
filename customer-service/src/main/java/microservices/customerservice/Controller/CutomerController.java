package microservices.customerservice.Controller;

import lombok.RequiredArgsConstructor;
import microservices.customerservice.DTO.CustomerRequest;
import microservices.customerservice.DTO.CustomerResponse;
import microservices.customerservice.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CutomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public CustomerResponse create(@RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/all")
    public List<CustomerResponse> getAll() {
        return  customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.customerId(id);
    }

    @PutMapping("/{id}")
    public CustomerResponse  update( @PathVariable Long id,@RequestBody CustomerRequest request) {
        return customerService.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}
