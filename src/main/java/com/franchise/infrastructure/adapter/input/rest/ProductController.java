package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateProductDTO;
import com.franchise.application.dto.request.ProductRequestDTO;
import com.franchise.application.dto.response.ExceptionResponseDTO;
import com.franchise.application.dto.response.ProductResponseDTO;
import com.franchise.application.handler.IProductHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.PRODUCT_API_PATH)
@RequiredArgsConstructor
@Tag(name = "Product", description = ApiConstants.TAG_PRODUCT_DESCRIPTION)
public class ProductController {

    private final IProductHandler productHandler;

    @PostMapping
    @Operation(summary = ApiConstants.OP_ADD_PRODUCT_SUMMARY, description = ApiConstants.OP_ADD_PRODUCT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.CREATED_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_CREATE_PRODUCT),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_BRANCH,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> addProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        return productHandler.addProductToBranch(productDTO)
                .map(product -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(product));
    }

    @DeleteMapping
    @Operation(summary = ApiConstants.OP_DELETE_PRODUCT_SUMMARY, description = ApiConstants.OP_DELETE_PRODUCT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.OK_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_DELETE_PRODUCT),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_PRODUCT_OR_BRANCH,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> deleteProduct(
            @RequestParam String productId, @RequestParam String branchId) {
        return productHandler.deleteProductFromBranch(productId, branchId)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product));
    }

    @PatchMapping
    @Operation(summary = ApiConstants.OP_UPDATE_PRODUCT_SUMMARY, description = ApiConstants.OP_UPDATE_PRODUCT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.OK_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_UPDATE_PRODUCT),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_PRODUCT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> updateProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        return productHandler.updateProduct(productDTO)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product));
    }
}
