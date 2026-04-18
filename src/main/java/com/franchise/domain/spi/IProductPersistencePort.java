package com.franchise.domain.spi;

import com.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Product> save(Product product);
    Mono<Product> delete(String productId, String branchId);
}
