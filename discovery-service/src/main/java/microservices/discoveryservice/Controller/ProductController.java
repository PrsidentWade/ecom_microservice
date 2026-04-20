package microservices.discoveryservice.Controller;

import lombok.RequiredArgsConstructor;
import microservices.discoveryservice.DTO.ProductRequest;
import microservices.discoveryservice.DTO.ProductResponse;
import microservices.discoveryservice.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ProductResponse create(@RequestBody ProductRequest request) {
        return   productService.createProd(request);
    }

    @GetMapping("/all")
    public List<ProductResponse> getAll(){
        return productService.getAll();
    }
    @GetMapping("/{id}")
    public ProductResponse  getProductById(@PathVariable Long id){
        return  productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@RequestBody ProductRequest request,@PathVariable Long id) {
        return  productService.update(id, request);

    }

    @DeleteMapping("/{id}")
    public void  delete(Long id) {
        productService.Delete(id);
    }

}
