package com.franchise.domain.api;

import com.franchise.domain.model.Branch;
import com.franchise.domain.model.BranchWithMaxProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {

    Mono<Branch> addBranchToFranchise(Branch branch);
    Mono<Branch> findBranchById(String id);
    Flux<BranchWithMaxProduct> findMaxStockProductPerBranch(String franchiseId);
    Mono<Branch> updateBranch(Branch branch);
}
