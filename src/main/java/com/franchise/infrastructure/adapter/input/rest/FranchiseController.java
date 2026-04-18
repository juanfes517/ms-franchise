package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.application.dto.response.FranchiseWithoutBranchDTO;
import com.franchise.application.handler.IFranchiseHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.FRANCHISE_API_PATH)
@RequiredArgsConstructor
public class FranchiseController {

    private final IFranchiseHandler franchiseHandler;

    @PostMapping
    public Mono<ResponseEntity<FranchiseWithoutBranchDTO>> createFranchise(
            @Valid @RequestBody CreateFranchiseDTO createFranchiseDTO) {
        return franchiseHandler.createFranchise(createFranchiseDTO)
                .map(saved -> ResponseEntity
                        .ok()
                        .body(saved));
    }
}
