package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Branch;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.BranchEntity;
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
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchDynamoRepositoryAdapterTest {

    @InjectMocks
    private BranchDynamoRepositoryAdapter adapter;

    @Mock
    private DynamoDbEnhancedAsyncClient client;

    @Mock
    private DynamoDbAsyncTable<BranchEntity> table;

    @Mock
    PagePublisher<BranchEntity> pagePublisher;

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

    @Test
    void shouldFindBranchSuccessfully() {
        String id = "BRANCH#123";

        BranchEntity branchEntity = BranchEntity.builder()
                .partitionKey("BRANCH#123")
                .sortKey("FRANCHISE#123")
                .name("test branch name")
                .build();

        when(pagePublisher.items())
                .thenReturn(SdkPublisher.adapt(Flux.just(branchEntity)));
        when(table.query(any(QueryConditional.class)))
                .thenReturn(pagePublisher);

        StepVerifier.create(adapter.findById(id))
                .assertNext(branch -> {
                    assertEquals("BRANCH#123", branch.getId());
                    assertEquals("FRANCHISE#123", branch.getFranchiseId());
                    assertEquals("test branch name", branch.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenInvalidBranchId() {
        String id = "InvalidBranchId";

        StepVerifier.create(adapter.findById(id))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Branch ID must start with 'BRANCH'"))
                .verify();
    }

    @Test
    void shouldFindAllBranchesSuccessfully() {
        String franchiseId = "FRANCHISE#123";

        BranchEntity branchEntity1 = BranchEntity.builder()
                .partitionKey("BRANCH#1")
                .sortKey(franchiseId)
                .name("Branch 1")
                .build();

        BranchEntity branchEntity2 = BranchEntity.builder()
                .partitionKey("BRANCH#2")
                .sortKey(franchiseId)
                .name("Branch 2")
                .build();

        when(pagePublisher.items())
                .thenReturn(SdkPublisher.adapt(Flux.just(branchEntity1, branchEntity2)));

        when(table.scan(any(ScanEnhancedRequest.class)))
                .thenReturn(pagePublisher);

        StepVerifier.create(adapter.findAllBranches(franchiseId))
                .expectNextMatches(branch -> branch.getName().equals("Branch 1"))
                .expectNextMatches(branch -> branch.getName().equals("Branch 2"))
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenNoBranchesFound() {
        String franchiseId = "FRANCHISE#123";

        when(pagePublisher.items())
                .thenReturn(SdkPublisher.adapt(Flux.empty()));

        when(table.scan(any(ScanEnhancedRequest.class)))
                .thenReturn(pagePublisher);

        StepVerifier.create(adapter.findAllBranches(franchiseId))
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenFranchiseIdIsInvalid() {
        String invalidFranchiseId = "INVALID#123";

        StepVerifier.create(adapter.findAllBranches(invalidFranchiseId))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals(DynamoAdapterConstants.INVALID_FRANCHISE_ID)
                )
                .verify();

        verifyNoInteractions(table);
    }

    @Test
    void shouldUpdateBranchSuccessfully() {
        Branch branch = Branch.builder()
                .id("BRANCH#123")
                .franchiseId("FRANCHISE#123")
                .name("New Branch Name")
                .build();

        BranchEntity branchEntity = BranchEntity.builder()
                .partitionKey("BRANCH#123")
                .sortKey("FRANCHISE#123")
                .name("New Branch Name")
                .build();

        when(table.updateItem(ArgumentMatchers.<UpdateItemEnhancedRequest<BranchEntity>>any()))
                .thenReturn(CompletableFuture.completedFuture(branchEntity));

        StepVerifier.create(adapter.update(branch))
                .expectNextMatches(result ->
                        result.getId().equals("BRANCH#123") &&
                        result.getFranchiseId().equals("FRANCHISE#123") &&
                        result.getName().equals("New Branch Name")
                )
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenBranchIdIsInvalid() {
        Branch branch = Branch.builder()
                .id("INVALID#123")
                .franchiseId("FRANCHISE#123")
                .name("New Branch Name")
                .build();

        StepVerifier.create(adapter.update(branch))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals(DynamoAdapterConstants.INVALID_BRANCH_ID)
                )
                .verify();

        verifyNoInteractions(table);
    }

    @Test
    void shouldReturnErrorWhenFranchiseIdIsInvalidInUpdateFranchise() {
        Branch branch = Branch.builder()
                .id("BRANCH#123")
                .franchiseId("INVALID#123")
                .name("New Branch Name")
                .build();

        StepVerifier.create(adapter.update(branch))
                .expectErrorMatches(error ->
                        error instanceof IllegalArgumentException &&
                        error.getMessage().equals(DynamoAdapterConstants.INVALID_FRANCHISE_ID)
                )
                .verify();

        verifyNoInteractions(table);
    }
}