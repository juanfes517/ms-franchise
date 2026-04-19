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

    @Test
    void shouldFindFranchiseSuccessfully() {

        String id = "FRANCHISE#343dser12";

        Franchise franchiseOutput = Franchise.builder()
                .id("FRANCHISE#343dser12")
                .name("test franchise name")
                .build();

        when(franchisePersistencePort.findById(any(String.class)))
                .thenReturn(Mono.just(franchiseOutput));

        StepVerifier.create(franchiseUseCase.findFranchiseById(id))
                .assertNext(franchise -> {
                    assertEquals("FRANCHISE#343dser12", franchise.getId());
                    assertEquals("test franchise name", franchise.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        Franchise franchise = Franchise.builder()
                .id("PRODUCT#123")
                .name("Franchise name")
                .build();

        Franchise newFranchise = Franchise.builder()
                .id("PRODUCT#123")
                .name("New franchise name")
                .build();

        when(franchisePersistencePort.update(any(Franchise.class)))
                .thenReturn(Mono.just(newFranchise));

        StepVerifier.create(franchiseUseCase.updateFranchise(franchise))
                .assertNext(updatedFranchise -> {
                    assertEquals("PRODUCT#123", updatedFranchise.getId());
                    assertEquals("New franchise name", updatedFranchise.getName());
                })
                .verifyComplete();
    }

}