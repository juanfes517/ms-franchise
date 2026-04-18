package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.helper.exception.BranchNotFoundException;
import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.api.IProductServicePort;
import com.franchise.domain.model.Branch;
import com.franchise.domain.model.Product;
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
class ProductHandlerTest {

    @InjectMocks
    private ProductHandler productHandler;

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IBranchServicePort branchServicePort;

    @Test
    void shouldCreateProductSuccessfully() {

        CreateProductDTO productInputDTO = CreateProductDTO.builder()
                .name("test product name")
                .stock(3)
                .branchId("BRANCH#123")
                .build();

        Product productOutput = Product.builder()
                .id("PRODUCT#123")
                .name("test product name")
                .stock(3)
                .branchId("BRANCH#123")
                .build();

        when(productServicePort.addProductToBranch(any(Product.class)))
                .thenReturn(Mono.just(productOutput));
        when(branchServicePort.findBranchById(any(String.class)))
                .thenReturn(Mono.just(new Branch()));

        StepVerifier.create(productHandler.addProductToBranch(productInputDTO))
                .assertNext(product -> {
                    assertEquals("PRODUCT#123", product.getId());
                    assertEquals("test product name", product.getName());
                    assertEquals(3, product.getStock());
                    assertEquals("BRANCH#123", product.getBranchId());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenBranchNotFound() {

        CreateProductDTO productInputDTO = CreateProductDTO.builder()
                .name("test product name")
                .stock(3)
                .branchId("InvalidBranchId")
                .build();

        when(branchServicePort.findBranchById(any(String.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(productHandler.addProductToBranch(productInputDTO))
                .expectErrorMatches(error ->
                        error instanceof BranchNotFoundException &&
                        error.getMessage().equals("Branch not found")
                )
                .verify();
    }
}