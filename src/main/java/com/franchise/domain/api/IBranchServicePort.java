package com.franchise.domain.api;

import com.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {

    Mono<Branch> addBranchToFranchise(Branch branch);
    Mono<Branch> findBranchById(String id);
}
