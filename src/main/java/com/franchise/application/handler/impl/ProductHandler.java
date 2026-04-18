package com.franchise.application.handler.impl;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.application.handler.IProductHandler;
import com.franchise.application.helper.exception.BranchNotFoundException;
import com.franchise.application.helper.mapper.ProductMapper;
import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.api.IProductServicePort;
import com.franchise.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IBranchServicePort branchServicePort;

    @Override
    public Mono<ProductResponseDTO> addProductToBranch(CreateProductDTO createProductDTO) {
        return branchServicePort
                .findBranchById(createProductDTO.getBranchId())
                .switchIfEmpty(Mono.error(new BranchNotFoundException(createProductDTO.getBranchId())))
                .flatMap(branch -> {
                    Product product = ProductMapper.toDomain(createProductDTO);
                    return productServicePort.addProductToBranch(product);
                })
                .map(ProductMapper::toDTO);
    }
}
