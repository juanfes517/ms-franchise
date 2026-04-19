package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithMaxProductResponseDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import com.franchise.application.handler.IBranchHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.BRANCH_API_PATH)
@RequiredArgsConstructor
public class BranchController {

    private final IBranchHandler branchHandler;

    @PostMapping
    public Mono<ResponseEntity<BranchWithoutProductsDTO>> addBranchToFranchise(
            @Valid @RequestBody CreateBranchDTO createBranchDTO) {
        return branchHandler.addBranchToFranchise(createBranchDTO)
                .map(branch -> ResponseEntity
                        .ok()
                        .body(branch));
    }

    @GetMapping(ApiConstants.MAX_STOCK_PER_BRANCH_PATH)
    public Flux<BranchWithMaxProductResponseDTO> findMaxStockProductPerBranch(@PathVariable String franchiseId) {
        return branchHandler.findMaxStockProductPerBranch(franchiseId);
    }
}
