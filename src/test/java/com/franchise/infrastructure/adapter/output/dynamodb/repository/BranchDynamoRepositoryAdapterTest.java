package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Branch;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.BranchEntity;
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
class BranchDynamoRepositoryAdapterTest {

    @InjectMocks
    private BranchDynamoRepositoryAdapter adapter;

    @Mock
    private DynamoDbEnhancedAsyncClient client;

    @Mock
    private DynamoDbAsyncTable<BranchEntity> table;

    @BeforeEach
    void setUp() {
        when(client.table(anyString(), ArgumentMatchers.<TableSchema<BranchEntity>>any()
        )).thenReturn(table);

        adapter.init();
    }

    @Test
    void shouldSaveBranchSuccessfully() {
        Branch franchise = Branch.builder()
                .franchiseId("FRANCHISE#7eb")
                .name("Test branch name")
                .build();

        when(table.putItem(any(BranchEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        StepVerifier.create(adapter.save(franchise))
                .assertNext(savedBranch -> {
                    assertTrue(savedBranch.getId().startsWith("BRANCH#"));
                    assertEquals("FRANCHISE#7eb", savedBranch.getFranchiseId());
                    assertEquals("Test branch name", savedBranch.getName());
                })
                .verifyComplete();
    }
}