package com.franchise.domain.usecase;

import com.franchise.domain.model.Product;
import com.franchise.domain.spi.IProductPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @InjectMocks
    private ProductUseCase productUseCase;

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Test
    void shouldCreateProductSuccessfully() {

        Product productInput = Product.builder()
                .name("test product name")
                .stock(5)
                .branchId("BRANCH#123")
                .build();

        Product productOutput = Product.builder()
                .id("PRODUCT#123")
                .name("test product name")
                .stock(5)
                .branchId("BRANCH#123")
                .build();

        when(productPersistencePort.save(any(Product.class)))
                .thenReturn(Mono.just(productOutput));

        StepVerifier.create(productUseCase.addProductToBranch(productInput))
                .assertNext(product -> {
                    assertEquals("PRODUCT#123", product.getId());
                    assertEquals("BRANCH#123", productInput.getBranchId());
                    assertEquals(5, productInput.getStock());
                    assertEquals("test product name", productInput.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldDeleteProductSuccessfully() {

        String productId = "PRODUCT#123";
        String branchId = "BRANCH#123";

        Product productOutput = Product.builder()
                .id("PRODUCT#123")
                .name("test product name")
                .stock(5)
                .branchId("BRANCH#123")
                .build();

        when(productPersistencePort.delete(any(String.class), any(String.class)))
                .thenReturn(Mono.just(productOutput));

        StepVerifier.create(productUseCase.deleteProductFromBranch(productId, branchId))
                .assertNext(product -> {
                    assertEquals(productId, product.getId());
                    assertEquals(branchId, product.getBranchId());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        Product product = Product.builder()
                .id("PRODUCT#123")
                .branchId("BRANCH#123")
                .name("Product name")
                .stock(50)
                .build();

        Product newProduct = Product.builder()
                .id("PRODUCT#123")
                .branchId("BRANCH#123")
                .name("New product name")
                .stock(42)
                .build();

        when(productPersistencePort.updateProduct(any(Product.class)))
                .thenReturn(Mono.just(newProduct));

        StepVerifier.create(productUseCase.updateProduct(product))
                .assertNext(updatedProduct -> {
                    assertEquals("New product name", updatedProduct.getName());
                    assertEquals(42, updatedProduct.getStock());
                })
                .verifyComplete();
    }
}