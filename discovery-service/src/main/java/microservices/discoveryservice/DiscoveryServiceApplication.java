package microservices.discoveryservice;

import microservices.discoveryservice.Entity.Product;
import microservices.discoveryservice.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DiscoveryServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(DiscoveryServiceApplication.class, args);

    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {

            productRepository.saveAll(List.of(

                    Product.builder()
                            .name("Laptop Dell")
                            .description("Ordinateur portable")
                            .price(35000.00)
                            .quantite(10)
                            .build(),

                    Product.builder()
                            .name("iPhone 13")
                            .description("Smartphone Apple")
                            .price(500000.00)
                            .quantite(5)
                            .build(),

                    Product.builder()
                            .name("Clavier HP")
                            .description("Clavier USB")
                            .price(15000.00)
                            .quantite(20)
                            .build(),

                    Product.builder()
                            .name("Souris Logitech")
                            .description("Souris sans fil")
                            .price(10000.00)
                            .quantite(25)
                            .build(),

                    Product.builder()
                            .name("Ecran Samsung")
                            .description("Moniteur 24 pouces")
                            .price(120000.00)
                            .quantite(8)
                            .build(),

                    Product.builder()
                            .name("Casque Sony")
                            .description("Casque audio")
                            .price(30000.00)
                            .quantite(15)
                            .build(),

                    Product.builder()
                            .name("Imprimante Canon")
                            .description("Imprimante couleur")
                            .price(80000.00)
                            .quantite(6)
                            .build(),

                    Product.builder()
                            .name("Tablette Lenovo")
                            .description("Tablette Android")
                            .price(150000.00)
                            .quantite(7)
                            .build(),

                    Product.builder()
                            .name("Disque Dur 1TB")
                            .description("Stockage externe")
                            .price(40000.00)
                            .quantite(12)
                            .build(),

                    Product.builder()
                            .name("Chargeur USB-C")
                            .description("Charge rapide")
                            .price(8000.00)
                            .quantite(30)
                            .build()

            ));

            System.out.println("✅ 10 produits insérés !");
        };
    }
}
