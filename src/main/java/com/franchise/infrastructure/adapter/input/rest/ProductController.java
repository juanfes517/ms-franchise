package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.application.handler.IProductHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
