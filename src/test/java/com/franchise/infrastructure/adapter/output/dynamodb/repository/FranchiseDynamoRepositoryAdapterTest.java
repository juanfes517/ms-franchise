package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.application.helper.exception.FranchiseNotFoundException;
import com.franchise.domain.model.Franchise;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.FranchiseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseDynamoRepositoryAdapterTest {

    @InjectMocks
    private FranchiseDynamoRepositoryAdapter adapter;

    @Mock
    private DynamoDbEnhancedAsyncClient client;

    @Mock
    private DynamoDbAsyncTable<FranchiseEntity> table;

    @BeforeEach
    void setUp() {
        when(client.table(anyString(), ArgumentMatchers.<TableSchema<FranchiseEntity>>any()
        )).thenReturn(table);

        adapter.init();
    }

    @Test
    void shouldSaveFranchiseSuccessfully() {
        Franchise franchise = Franchise.builder()
                .name("test franchise name")
                .build();

        when(table.putItem(any(FranchiseEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        StepVerifier.create(adapter.save(franchise))
                .assertNext(savedFranchise -> {
                    assertTrue(savedFranchise.getId().startsWith("FRANCHISE#"));
                    assertEquals("test franchise name", savedFranchise.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindFranchiseSuccessfully() {
        String id = "FRANCHISE#123";

        Mono<FranchiseEntity> dynamoResponse = Mono.just(FranchiseEntity.builder()
                .partitionKey("FRANCHISE#123")
                .sortKey("FRANCHISE#123")
                .name("test franchise name")
                .build());

        when(table.getItem(any(Key.class)))
                .thenReturn(dynamoResponse.toFuture());

        StepVerifier.create(adapter.findById(id))
                .assertNext(franchise -> {
                    assertEquals("FRANCHISE#123", franchise.getId());
                    assertEquals("test franchise name", franchise.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenInvalidFranchiseId() {
        String id = "InvalidFranchiseId";

        StepVerifier.create(adapter.findById(id))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Franchise ID must start with 'FRANCHISE'"))
                .verify();
    }
}