package com.franchise.domain.spi;

import com.franchise.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Product> save(Product product);
    Mono<Product> delete(String productId, String branchId);
    Mono<Product> updateProduct(Product product);
    Flux<Product> getAllProductsByBranch(String branchId);
}
