package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.application.dto.request.FranchiseRequestDTO;
import com.franchise.application.dto.response.FranchiseWithoutBranchDTO;
import com.franchise.application.handler.IFranchiseHandler;
import com.franchise.application.helper.exception.FranchiseNotFoundException;
import com.franchise.application.helper.mapper.FranchiseMapper;
import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<FranchiseWithoutBranchDTO> createFranchise(CreateFranchiseDTO createFranchiseDTO) {
        Franchise franchise = FranchiseMapper.toDomain(createFranchiseDTO);
        return franchiseServicePort
                .createFranchise(franchise)
                .map(FranchiseMapper::toDTO);
    }

    @Override
    public Mono<FranchiseWithoutBranchDTO> updateFranchise(FranchiseRequestDTO franchiseRequestDTO) {
        Franchise franchise = FranchiseMapper.toDomain(franchiseRequestDTO);
        return franchiseServicePort
                .updateFranchise(franchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseRequestDTO.getId())))
                .map(FranchiseMapper::toDTO);
    }

}
