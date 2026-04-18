package com.franchise.application.handler;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.application.dto.response.FranchiseWithoutBranchDTO;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {

    Mono<FranchiseWithoutBranchDTO> createFranchise(CreateFranchiseDTO createFranchiseDTO);
}
