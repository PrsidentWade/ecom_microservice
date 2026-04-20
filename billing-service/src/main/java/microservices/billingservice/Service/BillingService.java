package microservices.billingservice.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.billingservice.DTO.BillResponse;
import microservices.billingservice.DTO.BillinRequest;
import microservices.billingservice.DTO.ProductItemResponse;
import microservices.billingservice.Entity.Bill;
import microservices.billingservice.Entity.ProductItem;
import microservices.billingservice.Feign.ProductRestClient;
import microservices.billingservice.Model.Customer;
import microservices.billingservice.Model.Product;
import microservices.billingservice.Repository.BillRepository;
import microservices.billingservice.Feign.CustomerRestClient;
import microservices.billingservice.Repository.ProductItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BillingService {

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;

    // =========================
    // 🔹 CREATE
    // =========================
    public BillResponse create(BillinRequest request) {

        Customer customer = getCustomerOrThrow(request.getCustomerId());

        Bill bill = Bill.builder()
                .date(LocalDate.now())
                .customerId(customer.getId())
                .build();

        Bill savedBill = billRepository.save(bill);

        List<ProductItem> items = buildProductItems(request, savedBill);

        productItemRepository.saveAll(items);

        log.info("Bill created id={}", savedBill.getId());

        return buildResponse(savedBill, customer, items);
    }

    // =========================
    // 🔹 GET ALL
    // =========================

    public List<BillResponse> getAll() {

        return billRepository.findAll()
                .stream()
                .map(this::buildFullResponse)
                .toList();
    }

    // =========================
    // 🔹 GET BY ID
    // =========================
    public BillResponse getById(Long id) {

        Bill bill = findBillOrThrow(id);

        return buildFullResponse(bill);
    }

    // =========================
    // 🔹 DELETE
    // =========================
    public void delete(Long id) {

        Bill bill = findBillOrThrow(id);

        billRepository.delete(bill);

        log.info("Bill deleted id={}", id);
    }

    // =========================
    // 🔥 MÉTHODES PRIVÉES
    // =========================

    private Bill findBillOrThrow(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found id=" + id));
    }

    private Customer getCustomerOrThrow(Long id) {
        Customer customer = customerRestClient.getCustomerById(id);
        if (customer == null) {
            throw new RuntimeException("Customer not found id=" + id);
        }
        return customer;
    }

    private Product getProductOrThrow(Long id) {
        Product product = productRestClient.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found id=" + id);
        }
        return product;
    }

    private List<ProductItem> buildProductItems(BillinRequest request, Bill bill) {

        return request.getItems().stream().map(itemReq -> {

            Product product = getProductOrThrow(itemReq.getProductId());

            return ProductItem.builder()
                    .productId(product.getId())
                    .price(product.getPrice()) // 🔥 source produit
                    .quantite(itemReq.getQuantite())
                    .bill(bill)
                    .build();

        }).toList();
    }

    private BillResponse buildFullResponse(Bill bill) {

        Customer customer = getCustomerOrThrow(bill.getCustomerId());

        List<ProductItem> items = productItemRepository.findByBillId(bill.getId());

        return buildResponse(bill, customer, items);
    }

    private BillResponse buildResponse(
            Bill bill,
            Customer customer,
            List<ProductItem> items
    ) {

        List<ProductItemResponse> itemResponses = items.stream().map(item -> {

            Product product = getProductOrThrow(item.getProductId());

            return ProductItemResponse.builder()
                    .id(item.getId())
                    .price(item.getPrice())
                    .quantite(item.getQuantite())
                    .product(product)
                    .build();

        }).toList();

        return BillResponse.builder()
                .id(bill.getId())
                .date(bill.getDate())
                .customer(customer)
                .items(itemResponses)
                .build();
    }
}