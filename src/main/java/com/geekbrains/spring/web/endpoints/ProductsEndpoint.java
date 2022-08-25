package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.soap.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {

    private static final String NAMESPACE_URI = "http://www.mvg.com/spring/ws/products";
    private final ProductsService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request){
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAll().forEach(p -> {
            Product product = new Product();
            product.setTitle(p.getTitle());
            product.setPrice(p.getPrice());
            response.getProducts().add(product);
        });
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProductById(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        Product product = new Product();
        product.setTitle(productService.findById(request.getId()).get().getTitle());
        product.setPrice(productService.findById(request.getId()).get().getPrice());
        product.setId(productService.findById(request.getId()).get().getId());
        response.setProduct(product);
        return response;
    }
}
