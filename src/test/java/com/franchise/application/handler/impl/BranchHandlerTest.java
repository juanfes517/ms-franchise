package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.helper.exception.FranchiseNotFoundException;
import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Branch;
import com.franchise.domain.model.Franchise;
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
class BranchHandlerTest {

    @InjectMocks
    private BranchHandler branchHandler;

    @Mock
    private IBranchServicePort branchServicePort;

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @Test
    void shouldCreateBranchSuccessfully() {

        CreateBranchDTO branchInputDTO = CreateBranchDTO.builder()
                .name("test branch name")
                .franchiseId("FRANCHISE#123")
                .build();

        Branch branchOutput = Branch.builder()
                .id("BRANCH#123")
                .name("test branch name")
                .franchiseId("FRANCHISE#123")
                .build();

        when(branchServicePort.addBranchToFranchise(any(Branch.class)))
                .thenReturn(Mono.just(branchOutput));
        when(franchiseServicePort.findFranchiseById(any(String.class)))
                .thenReturn(Mono.just(new Franchise()));

        StepVerifier.create(branchHandler.addBranchToFranchise(branchInputDTO))
                .assertNext(branch -> {
                    assertEquals("BRANCH#123", branch.getId());
                    assertEquals("test branch name", branch.getName());
                    assertEquals("FRANCHISE#123", branch.getFranchiseId());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenFranchiseNotFound() {

        CreateBranchDTO branchInputDTO = CreateBranchDTO.builder()
                .name("test branch name")
                .franchiseId("invalid franchise id")
                .build();

        when(franchiseServicePort.findFranchiseById(any(String.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(branchHandler.addBranchToFranchise(branchInputDTO))
                .expectErrorMatches(error ->
                        error instanceof FranchiseNotFoundException &&
                        error.getMessage().equals("Franchise not found")
                )
                .verify();
    }
}