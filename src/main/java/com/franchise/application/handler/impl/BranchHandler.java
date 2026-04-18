package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import com.franchise.application.handler.IBranchHandler;
import com.franchise.application.helper.exception.FranchiseNotFoundException;
import com.franchise.application.helper.mapper.BranchMapper;
import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BranchHandler implements IBranchHandler {

    private final IBranchServicePort branchServicePort;
    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<BranchWithoutProductsDTO> addBranchToFranchise(CreateBranchDTO createBranchDTO) {

        return franchiseServicePort.findFranchiseById(createBranchDTO.getFranchiseId())
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(createBranchDTO.getFranchiseId())))
                .flatMap(franchise -> {
                    Branch branch = BranchMapper.toDomain(createBranchDTO);
                    return branchServicePort.addBranchToFranchise(branch);
                })
                .map(BranchMapper::toDTO);
    }
}
