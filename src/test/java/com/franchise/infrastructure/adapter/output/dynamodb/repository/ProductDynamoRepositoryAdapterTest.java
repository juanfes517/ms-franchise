package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Product;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDynamoRepositoryAdapterTest {

    @InjectMocks
    private ProductDynamoRepositoryAdapter adapter;

    @Mock
    private DynamoDbEnhancedAsyncClient client;

    @Mock
    private DynamoDbAsyncTable<ProductEntity> table;

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
}