package com.franchise.domain.usecase;

import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.model.Branch;
import com.franchise.domain.spi.IBranchPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;

    @Override
    public Mono<Branch> addBranchToFranchise(Branch branch) {
        return branchPersistencePort.save(branch);
    }
}
