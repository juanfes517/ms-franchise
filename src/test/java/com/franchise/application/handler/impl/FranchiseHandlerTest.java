package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Franchise;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FranchiseHandlerTest {

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @Test
    void shouldCreateFranchiseSuccessfully() {

        CreateFranchiseDTO franchiseInputDTO = CreateFranchiseDTO.builder()
                .name("test franchise name")
                .build();

        Franchise franchiseOutput = Franchise.builder()
                .id("FRANCHISE#343dser12")
                .name("test franchise name")
                .build();

        when(franchiseServicePort.createFranchise(any(Franchise.class)))
                .thenReturn(Mono.just(franchiseOutput));

        StepVerifier.create(franchiseHandler.createFranchise(franchiseInputDTO))
                .assertNext(franchise -> {
                    assertEquals("FRANCHISE#343dser12", franchise.getId());
                    assertEquals("test franchise name", franchise.getName());
                })
                .verifyComplete();
    }


}