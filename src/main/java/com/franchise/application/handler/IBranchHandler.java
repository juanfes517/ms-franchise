package com.franchise.application.handler;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithMaxProductResponseDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchHandler {

    Mono<BranchWithoutProductsDTO> addBranchToFranchise(CreateBranchDTO createBranchDTO);
    Flux<BranchWithMaxProductResponseDTO> findMaxStockProductPerBranch(String franchiseId);
}
