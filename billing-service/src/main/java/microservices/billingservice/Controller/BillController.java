package microservices.billingservice.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.billingservice.DTO.BillResponse;
import microservices.billingservice.DTO.BillinRequest;
import microservices.billingservice.Service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billings")
@RequiredArgsConstructor
@Slf4j
public class BillController {

    private final BillingService billService;

    // =========================
    // 🔹 CREATE BILL
    // =========================
    @PostMapping
    public ResponseEntity<BillResponse> create(@RequestBody BillinRequest request) {

        BillResponse response = billService.create(request);

        log.info("API -> Bill created id={}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // 🔹 GET ALL BILLS
    // =========================
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAll() {

        List<BillResponse> responses = billService.getAll();

        return ResponseEntity.ok(responses);
    }

    // =========================
    // 🔹 GET BILL BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getById(@PathVariable Long id) {

        BillResponse response = billService.getById(id);

        return ResponseEntity.ok(response);
    }

    // =========================
    // 🔹 DELETE BILL
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        billService.delete(id);

        log.info("API -> Bill deleted id={}", id);

        return ResponseEntity.noContent().build();
    }
}