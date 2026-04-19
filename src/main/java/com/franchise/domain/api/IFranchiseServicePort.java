package com.franchise.domain.api;

import com.franchise.domain.model.BranchWithMaxProduct;
import com.franchise.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> findFranchiseById(String id);

}
