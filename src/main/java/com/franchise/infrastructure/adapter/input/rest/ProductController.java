package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.application.handler.IProductHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.PRODUCT_API_PATH)
@RequiredArgsConstructor
public class ProductController {

    private final IProductHandler productHandler;

    @PostMapping
    public Mono<ResponseEntity<ProductResponseDTO>> addProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        return productHandler.addProductToBranch(productDTO)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product));
    }

    @DeleteMapping
    public Mono<ResponseEntity<ProductResponseDTO>> deleteProduct(
            @RequestParam String productId, @RequestParam String branchId) {
        return productHandler.deleteProductFromBranch(productId, branchId)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product));
    }
}
