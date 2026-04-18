package com.franchise.domain.usecase;

import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Franchise;
import com.franchise.domain.spi.IFranchisePersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        franchise.setId(UUID.randomUUID().toString());
        return franchisePersistencePort.save(franchise);
    }

    @Override
    public Mono<Franchise> findFranchiseById(String id) {
        return franchisePersistencePort.findById(id);
    }
}
