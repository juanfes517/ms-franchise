package com.franchise.domain.usecase;

import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.model.Branch;
import com.franchise.domain.model.BranchWithMaxProduct;
import com.franchise.domain.spi.IBranchPersistencePort;
import com.franchise.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final IProductPersistencePort productPersistencePort;

    @Override
    public Mono<Branch> addBranchToFranchise(Branch branch) {
        return branchPersistencePort.save(branch);
    }

    @Override
    public Mono<Branch> findBranchById(String id) {
        return branchPersistencePort.findById(id);
    }

    @Override
    public Flux<BranchWithMaxProduct> findMaxStockProductPerBranch(String franchiseId) {
        return branchPersistencePort
                .findAllBranches(franchiseId)
                .flatMap(branch ->
                        productPersistencePort
                                .getAllProductsByBranch(branch.getId())
                                .reduce((p1, p2) -> p1.getStock() >= p2.getStock() ? p1 : p2)
                                .map(maxProduct -> BranchWithMaxProduct.builder()
                                        .id(branch.getId())
                                        .name(branch.getName())
                                        .franchiseId(franchiseId)
                                        .maxStockProduct(maxProduct)
                                        .build())
                );
    }
}
