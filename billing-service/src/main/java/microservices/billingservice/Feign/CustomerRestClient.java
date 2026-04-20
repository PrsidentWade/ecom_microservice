package microservices.billingservice.Feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import microservices.billingservice.Model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {
    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customer-service", fallbackMethod = "getDefaultCustomer")
    Customer getCustomerById(@PathVariable Long id);

    default Customer getDefaultCustomer(Long id, Exception exception){
        exception.getStackTrace();
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstname("yacibne");
        customer.setLastname("sarr");
        customer.setEmail("sarr@gmeial.com");
        return customer;

    }

}
