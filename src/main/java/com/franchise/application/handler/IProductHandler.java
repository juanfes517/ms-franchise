package com.franchise.application.handler;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.request.ProductRequestDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import reactor.core.publisher.Mono;

public interface IProductHandler {

    Mono<ProductResponseDTO> addProductToBranch(CreateProductDTO createProductDTO);
    Mono<ProductResponseDTO> deleteProductFromBranch(String productId, String branchId);
    Mono<ProductResponseDTO> updateProduct(ProductRequestDTO productRequestDTO);
}
