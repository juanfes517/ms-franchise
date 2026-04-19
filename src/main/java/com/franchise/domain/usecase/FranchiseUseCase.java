package com.franchise.domain.usecase;

import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Franchise;
import com.franchise.domain.spi.IFranchisePersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchisePersistencePort.save(franchise);
    }

    @Override
    public Mono<Franchise> findFranchiseById(String id) {
        return franchisePersistencePort.findById(id);
    }

    @Override
    public Mono<Franchise> updateFranchise(Franchise franchise) {
        return franchisePersistencePort.update(franchise);
    }
}
