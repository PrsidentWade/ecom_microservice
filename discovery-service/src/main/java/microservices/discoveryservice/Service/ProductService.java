package microservices.discoveryservice.Service;

import lombok.RequiredArgsConstructor;
import microservices.discoveryservice.DTO.ProductRequest;
import microservices.discoveryservice.DTO.ProductResponse;
import microservices.discoveryservice.Entity.Product;
import microservices.discoveryservice.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProd(ProductRequest request){
        if(productRepository.existsByName(request.getName())){
            throw new RuntimeException("Cette produit existe deja !!!");
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantite(request.getQuantite())
                .build();

        Product saved = productRepository.save(product);
        return BuilResponse(saved);
    }
    public List<ProductResponse> getAll(){
        List<ProductResponse> responses = productRepository.findAll()
                .stream()
                .map(this::BuilResponse)
                .collect(Collectors.toList());
        return responses;
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        return BuilResponse(product);

    }

    public ProductResponse update(Long id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        if(!product.getName().equals(request.getName()) && productRepository.existsByName(request.getName())){
                throw new RuntimeException("Cette non existe daja");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setQuantite(request.getQuantite());
        product.setPrice(request.getPrice());

        Product saved = productRepository.save(product);

        return BuilResponse(saved);
    }

    public void Delete(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        productRepository.delete(product);

    }

    private ProductResponse BuilResponse(Product product){
        return  ProductResponse.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .description(product.getDescription())
                .quantite(product.getQuantite())
                .build();
    }
}
