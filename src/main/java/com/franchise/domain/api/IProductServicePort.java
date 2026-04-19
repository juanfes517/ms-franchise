package com.franchise.domain.api;

import com.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {

    Mono<Product> addProductToBranch(Product product);
    Mono<Product> deleteProductFromBranch(String productId, String branchId);
    Mono<Product> updateProduct(Product product);
}
