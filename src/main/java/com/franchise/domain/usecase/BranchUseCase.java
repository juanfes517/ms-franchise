package com.franchise.domain.usecase;

import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.model.Branch;
import com.franchise.domain.spi.IBranchPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;

    @Override
    public Mono<Branch> addBranchToFranchise(Branch branch) {
        branch.setId(UUID.randomUUID().toString());
        return branchPersistencePort.save(branch);
    }
}
