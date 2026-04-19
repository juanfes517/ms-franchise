package com.franchise.domain.usecase;

import com.franchise.domain.model.Branch;
import com.franchise.domain.model.Product;
import com.franchise.domain.spi.IBranchPersistencePort;
import com.franchise.domain.spi.IProductPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @InjectMocks
    private BranchUseCase branchUseCase;

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Test
    void shouldCreateBranchSuccessfully() {

        Branch branchInput = Branch.builder()
                .name("test branch name")
                .franchiseId("FRANCHISE#123")
                .build();

        Branch branchOutput = Branch.builder()
                .id("BRANCH#123")
                .name("test branch name")
                .franchiseId("FRANCHISE#123")
                .build();

        when(branchPersistencePort.save(any(Branch.class)))
                .thenReturn(Mono.just(branchOutput));

        StepVerifier.create(branchUseCase.addBranchToFranchise(branchInput))
                .assertNext(branch -> {
                    assertEquals("BRANCH#123", branch.getId());
                    assertEquals("test branch name", branchInput.getName());
                    assertEquals("FRANCHISE#123", branchInput.getFranchiseId());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindFranchiseSuccessfully() {

        String id = "BRANCH#123";

        Branch branchOutput = Branch.builder()
                .id("BRANCH#123")
                .franchiseId("FRANCHISE#123")
                .name("test branch name")
                .build();

        when(branchPersistencePort.findById(any(String.class)))
                .thenReturn(Mono.just(branchOutput));

        StepVerifier.create(branchUseCase.findBranchById(id))
                .assertNext(branch -> {
                    assertEquals("BRANCH#123", branch.getId());
                    assertEquals("FRANCHISE#123", branch.getFranchiseId());
                    assertEquals("test branch name", branch.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindMaxStockProductPerBranchSuccessfully() {
        String franchiseId = "FRANCHISE#123";

        Branch branch1 = Branch.builder().id("BRANCH#1").name("Branch 1").franchiseId(franchiseId).build();
        Branch branch2 = Branch.builder().id("BRANCH#2").name("Branch 2").franchiseId(franchiseId).build();

        Product product1 = Product.builder().id("PRODUCT#1").name("Product 1").stock(10).build();
        Product product2 = Product.builder().id("PRODUCT#2").name("Product 2").stock(50).build();
        Product product3 = Product.builder().id("PRODUCT#3").name("Product 3").stock(30).build();
        Product product4 = Product.builder().id("PRODUCT#4").name("Product 4").stock(20).build();

        when(branchPersistencePort.findAllBranches(franchiseId))
                .thenReturn(Flux.just(branch1, branch2));
        when(productPersistencePort.getAllProductsByBranch(branch1.getId()))
                .thenReturn(Flux.just(product1, product2));
        when(productPersistencePort.getAllProductsByBranch(branch2.getId()))
                .thenReturn(Flux.just(product3, product4));

        StepVerifier.create(branchUseCase.findMaxStockProductPerBranch(franchiseId))
                .expectNextMatches(result ->
                        result.getId().equals("BRANCH#1") &&
                        result.getMaxStockProduct().getStock().equals(50)
                )
                .expectNextMatches(result ->
                        result.getId().equals("BRANCH#2") &&
                        result.getMaxStockProduct().getStock().equals(30)
                )
                .verifyComplete();
    }

    @Test
    void shouldUpdateBranchSuccessfully() {
        Branch branch = Branch.builder()
                .id("BRANCH#123")
                .franchiseId("FRANCHISE#123")
                .name("New Branch Name")
                .build();

        when(branchPersistencePort.update(any(Branch.class)))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.updateBranch(branch))
                .expectNextMatches(result ->
                        result.getId().equals("BRANCH#123") &&
                        result.getName().equals("New Branch Name")
                )
                .verifyComplete();
    }
}