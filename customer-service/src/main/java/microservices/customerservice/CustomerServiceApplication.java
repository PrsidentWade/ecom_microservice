package microservices.customerservice;

import microservices.customerservice.Entity.Customer;
import microservices.customerservice.Repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder()
                            .firstname("modou")
                            .lastname("Wade")
                            .email("abdoulayewade@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .firstname("serigne")
                    .lastname("dillao")
                    .email("serigne@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .firstname("sarr")
                    .lastname("boe")
                    .email("sarrb@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .firstname("samba")
                    .lastname("ndiaye")
                    .email("ndiate@gmail.com")
                    .build());
            System.out.println("✅ Données insérées !");
        };
    }

}
