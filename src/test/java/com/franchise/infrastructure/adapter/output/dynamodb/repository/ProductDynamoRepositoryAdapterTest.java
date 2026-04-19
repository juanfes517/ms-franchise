package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Product;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.ProductEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDynamoRepositoryAdapterTest {

    @InjectMocks
    private ProductDynamoRepositoryAdapter adapter;

    @Mock
    private DynamoDbEnhancedAsyncClient client;

    @Mock
    private DynamoDbAsyncTable<ProductEntity> table;

    @Mock
    private PagePublisher<ProductEntity> pagePublisher;

    @BeforeEach
    void setUp() {
        when(client.table(anyString(), ArgumentMatchers.<TableSchema<ProductEntity>>any()
        )).thenReturn(table);

        adapter.init();
    }

    @Test
    void shouldSaveProductSuccessfully() {
        Product product = Product.builder()
                .name("test product name")
                .branchId("BRANCH#123")
                .stock(5)
                .build();

        when(table.putItem(any(ProductEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        StepVerifier.create(adapter.save(product))
                .assertNext(savedProduct -> {
                    assertTrue(savedProduct.getId().startsWith("PRODUCT#"));
                    assertEquals("test product name", savedProduct.getName());
                    assertEquals("BRANCH#123", savedProduct.getBranchId());
                })
                .verifyComplete();
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        String productId = "PRODUCT#123";
        String branchId = "BRANCH#123";

        ProductEntity productEntity = ProductEntity.builder()
                .partitionKey(productId)
                .sortKey(branchId)
                .name("test product product")
                .stock(3)
                .build();

        when(table.deleteItem(ArgumentMatchers.<Consumer<DeleteItemEnhancedRequest.Builder>>any()))
                .thenReturn(CompletableFuture.completedFuture(productEntity));

        StepVerifier.create(adapter.delete(productId, branchId))
                .assertNext(product -> {
                    assertEquals(productId, product.getId());
                    assertEquals(branchId, product.getBranchId());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenProductIdIsInvalid() {
        String invalidProductId = "INVALID#123";
        String branchId = "BRANCH#123";

        StepVerifier.create(adapter.delete(invalidProductId, branchId))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Product ID must start with 'PRODUCT'")
                )
                .verify();

        verifyNoInteractions(table);
    }

    @Test
    void shouldReturnErrorWhenBranchIdIsInvalidInDeleteProduct() {
        String invalidProductId = "PRODUCT#123";
        String branchId = "INVALID#123";

        StepVerifier.create(adapter.delete(invalidProductId, branchId))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Branch ID must start with 'BRANCH'")
                )
                .verify();

        verifyNoInteractions(table);
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        Product product = Product.builder()
                .id("PRODUCT#123")
                .branchId("BRANCH#123")
                .name("Test product name")
                .stock(50)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .partitionKey("PRODUCT#123")
                .sortKey("BRANCH#123")
                .name("Test product name")
                .stock(50)
                .build();

        when(table.updateItem(ArgumentMatchers.<UpdateItemEnhancedRequest<ProductEntity>>any()))
                .thenReturn(CompletableFuture.completedFuture(productEntity));

        StepVerifier.create(adapter.updateProduct(product))
                .assertNext(updatedProduct -> {
                    assertEquals("Test product name", updatedProduct.getName());
                    assertEquals(50, updatedProduct.getStock());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetAllProductsByBranchSuccessfully() {
        String branchId = "BRANCH#123";

        ProductEntity productEntity1 = ProductEntity.builder()
                .partitionKey("PRODUCT#1")
                .sortKey(branchId)
                .name("Product 1")
                .stock(10)
                .build();

        ProductEntity productEntity2 = ProductEntity.builder()
                .partitionKey("PRODUCT#2")
                .sortKey(branchId)
                .name("Product 2")
                .stock(20)
                .build();

        when(pagePublisher.items())
                .thenReturn(SdkPublisher.adapt(Flux.just(productEntity1, productEntity2)));
        when(table.scan(any(ScanEnhancedRequest.class)))
                .thenReturn(pagePublisher);

        StepVerifier.create(adapter.getAllProductsByBranch(branchId))
                .expectNextMatches(product -> product.getName().equals("Product 1") && product.getStock().equals(10))
                .expectNextMatches(product -> product.getName().equals("Product 2") && product.getStock().equals(20))
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenNoProductsFound() {
        String branchId = "BRANCH#123";

        when(pagePublisher.items())
                .thenReturn(SdkPublisher.adapt(Flux.empty()));
        when(table.scan(any(ScanEnhancedRequest.class)))
                .thenReturn(pagePublisher);

        StepVerifier.create(adapter.getAllProductsByBranch(branchId))
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenBranchIdIsInvalid() {
        String invalidBranchId = "INVALID#123";

        StepVerifier.create(adapter.getAllProductsByBranch(invalidBranchId))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals(DynamoAdapterConstants.INVALID_BRANCH_ID)
                )
                .verify();

        verifyNoInteractions(table);
    }


}