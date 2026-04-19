package com.franchise.application.helper.mapper;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.application.dto.request.FranchiseRequestDTO;
import com.franchise.application.dto.response.FranchiseWithoutBranchDTO;
import com.franchise.domain.model.Franchise;

public class FranchiseMapper {

    private FranchiseMapper() {}

    public static Franchise toDomain(CreateFranchiseDTO createFranchiseDTO) {
        return Franchise.builder()
                .name(createFranchiseDTO.getName())
                .build();
    }

    public static Franchise toDomain(FranchiseRequestDTO franchiseRequestDTO) {
        return Franchise.builder()
                .id(franchiseRequestDTO.getId())
                .name(franchiseRequestDTO.getName())
                .build();
    }

    public static FranchiseWithoutBranchDTO toDTO(Franchise franchise) {
        return FranchiseWithoutBranchDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .build();
    }
}
