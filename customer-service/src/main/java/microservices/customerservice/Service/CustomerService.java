package microservices.customerservice.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.customerservice.DTO.CustomerRequest;
import microservices.customerservice.DTO.CustomerResponse;
import microservices.customerservice.Entity.Customer;
import microservices.customerservice.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request){
        if(customerRepository.existsByEmail(request.getEmail())){
               throw new RuntimeException("Cette email existe deja");
        }

        Customer customer = Customer.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .build();
        Customer saved = customerRepository.save(customer);
        log.info("CUSTOMER AJOUTER AVEC SUCCESS !!!");

        return Builrespone(saved);
    }

    public List<CustomerResponse>  getAll(){
        List<CustomerResponse> responses = customerRepository.findAll()
                .stream()
                .map(this::Builrespone)
                .collect(Collectors.toList());
        return responses;
    }
    public CustomerResponse customerId(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cette customer n'existe pas"));
        return Builrespone(customer);
    }

    public CustomerResponse update(Long id, CustomerRequest request){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cette customer n'existe pas"));

        if(!customer.getEmail().equals(request.getEmail()) && customerRepository.existsByEmail(request.getEmail())){
               throw new RuntimeException("Cette email est deja utiliser ");
        }

        customer.setFirstname(request.getFirstname());
        customer.setLastname(request.getLastname());
        customer.setEmail(request.getEmail());

        Customer saved = customerRepository.save(customer);


        return Builrespone(saved);
    }

    public void  delete(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cette customer n'existe pas"));
        customerRepository.delete(customer);
        log.info("Cutomer supprimer avec success"+ customer);
    }


    private CustomerResponse Builrespone(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .build();
    }
}
