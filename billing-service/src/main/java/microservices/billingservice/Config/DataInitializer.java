package microservices.billingservice.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.billingservice.Entity.Bill;
import microservices.billingservice.Entity.ProductItem;
import microservices.billingservice.Feign.CustomerRestClient;
import microservices.billingservice.Feign.ProductRestClient;
import microservices.billingservice.Model.Customer;
import microservices.billingservice.Model.Product;
import microservices.billingservice.Repository.BillRepository;
import microservices.billingservice.Repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;

    @Override
    public void run(String... args) throws Exception {

        // 🔹 éviter duplication
        if (billRepository.count() > 0) {
            log.info("Data already initialized");
            return;
        }

        log.info("Initializing billing data...");

        // =========================
        // 🔹 CUSTOMER (via API)
        // =========================
        Customer customer = customerRestClient.getCustomerById(1L);

        if (customer == null) {
            log.warn("Customer not found, skipping init");
            return;
        }

        // =========================
        // 🔹 BILL 1
        // =========================
        Bill bill1 = Bill.builder()
                .date(LocalDate.now())
                .customerId(customer.getId())
                .build();

        Bill savedBill1 = billRepository.save(bill1);

        // 🔹 récupérer produits via API
        Product p1 = productRestClient.getProductById(10L);
        Product p2 = productRestClient.getProductById(4L);

        List<ProductItem> items1 = List.of(
                ProductItem.builder()
                        .productId(p1.getId())
                        .price(p1.getPrice())
                        .quantite(2)
                        .bill(savedBill1)
                        .build(),

                ProductItem.builder()
                        .productId(p2.getId())
                        .price(p2.getPrice())
                        .quantite(1)
                        .bill(savedBill1)
                        .build()
        );

        productItemRepository.saveAll(items1);

        // =========================
        // 🔹 BILL 2
        // =========================
        Bill bill2 = Bill.builder()
                .date(LocalDate.now())
                .customerId(customer.getId())
                .build();

        Bill savedBill2 = billRepository.save(bill2);

        Product p3 = productRestClient.getProductById(6L);

        List<ProductItem> items2 = List.of(
                ProductItem.builder()
                        .productId(p3.getId())
                        .price(p3.getPrice())
                        .quantite(3)
                        .bill(savedBill2)
                        .build()
        );

        productItemRepository.saveAll(items2);

        log.info("Billing data initialized successfully 🚀");
    }
}
