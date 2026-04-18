package com.franchise.application.helper.mapper;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import com.franchise.domain.model.Branch;

public class BranchMapper {

    private BranchMapper() {}

    public static Branch toDomain(CreateBranchDTO createBranchDTO) {
        return Branch.builder()
                .name(createBranchDTO.getName())
                .franchiseId(createBranchDTO.getFranchiseId())
                .build();
    }

    public static BranchWithoutProductsDTO toDTO(Branch branch) {
        return BranchWithoutProductsDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .build();
    }
}
