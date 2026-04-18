package com.franchise.domain.usecase;

import com.franchise.domain.api.IProductServicePort;
import com.franchise.domain.model.Product;
import com.franchise.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;

    @Override
    public Mono<Product> addProductToBranch(Product product) {
        return productPersistencePort.save(product);
    }

    @Override
    public Mono<Product> deleteProductFromBranch(String productId, String branchId) {
        return productPersistencePort.delete(productId, branchId);
    }
}
