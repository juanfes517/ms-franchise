package com.franchise.domain.api;

import com.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> findFranchiseById(String id);
    Mono<Franchise> updateFranchise(Franchise franchise);
}
