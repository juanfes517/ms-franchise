package com.franchise.application.helper.mapper;

import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithMaxProductResponseDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.domain.model.Branch;
import com.franchise.domain.model.BranchWithMaxProduct;

public class BranchMapper {

    private BranchMapper() {
    }

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

    public static BranchWithMaxProductResponseDTO toDTO(BranchWithMaxProduct branchWithMaxProduct) {
        return BranchWithMaxProductResponseDTO.builder()
                .id(branchWithMaxProduct.getId())
                .name(branchWithMaxProduct.getName())
                .franchiseId(branchWithMaxProduct.getFranchiseId())
                .maxStockProduct(
                        ProductResponseDTO.builder()
                                .id(branchWithMaxProduct.getMaxStockProduct().getId())
                                .name(branchWithMaxProduct.getMaxStockProduct().getName())
                                .stock(branchWithMaxProduct.getMaxStockProduct().getStock())
                                .branchId(branchWithMaxProduct.getId())
                                .build()
                )
                .build();
    }
}
