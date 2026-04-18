package com.franchise.domain.usecase;

import com.franchise.domain.model.Franchise;
import com.franchise.domain.spi.IFranchisePersistencePort;
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
class FranchiseUseCaseTest {

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    @Mock
    private IFranchisePersistencePort franchisePersistencePort;

    @Test
    void shouldCreateFranchiseSuccessfully() {

        Franchise franchiseInput = Franchise.builder()
                .name("test franchise name")
                .build();

        Franchise franchiseOutput = Franchise.builder()
                .id("FRANCHISE#343dser12")
                .name("test franchise name")
                .build();

        when(franchisePersistencePort.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchiseOutput));

        StepVerifier.create(franchiseUseCase.createFranchise(franchiseInput))
                .assertNext(franchise -> {
                    assertEquals("FRANCHISE#343dser12", franchise.getId());
                    assertEquals("test franchise name", franchise.getName());
                })
                .verifyComplete();
    }


}