package com.franchise.application.helper.mapper;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.domain.model.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static Product toDomain(CreateProductDTO createProductDTO) {
        return Product.builder()
                .name(createProductDTO.getName())
                .stock(createProductDTO.getStock())
                .branchId(createProductDTO.getBranchId())
                .build();
    }

    public static ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(product.getBranchId())
                .build();
    }
}
