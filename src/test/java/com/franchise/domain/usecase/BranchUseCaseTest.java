package com.franchise.domain.usecase;

import com.franchise.domain.model.Branch;
import com.franchise.domain.spi.IBranchPersistencePort;
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
class BranchUseCaseTest {

    @InjectMocks
    private BranchUseCase branchUseCase;

    @Mock
    private IBranchPersistencePort branchPersistencePort;

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
}