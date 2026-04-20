package microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator locator(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
         return  new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }

//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//                // 🔹 Customer Service
//                .route("customer-service", r -> r
//                        .path("/customers/**")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("lb://CUSTOMER-SERVICE")
//                )
//
//                // 🔹 Product Service
//                .route("product-service", r -> r
//                        .path("/products/**")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("lb://PRODUCT-SERVICE")
//                )
//
//                .build();
//    }

}
