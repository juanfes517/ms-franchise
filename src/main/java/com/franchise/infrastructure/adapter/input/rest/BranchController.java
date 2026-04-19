package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.BranchRequestDTO;
import com.franchise.application.dto.request.CreateBranchDTO;
import com.franchise.application.dto.response.BranchWithMaxProductResponseDTO;
import com.franchise.application.dto.response.BranchWithoutProductsDTO;
import com.franchise.application.dto.response.ExceptionResponseDTO;
import com.franchise.application.handler.IBranchHandler;
import com.franchise.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.BRANCH_API_PATH)
@RequiredArgsConstructor
@Tag(name = "Branch", description = ApiConstants.TAG_BRANCH_DESCRIPTION)
public class BranchController {

    private final IBranchHandler branchHandler;

    @PostMapping
    @Operation(summary = ApiConstants.OP_ADD_BRANCH_SUMMARY, description = ApiConstants.OP_ADD_BRANCH_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.CREATED_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_CREATE_BRANCH),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_FRANCHISE,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<BranchWithoutProductsDTO>> addBranchToFranchise(
            @Valid @RequestBody CreateBranchDTO createBranchDTO) {
        return branchHandler.addBranchToFranchise(createBranchDTO)
                .map(branch -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(branch));
    }

    @GetMapping(ApiConstants.MAX_STOCK_PER_BRANCH_PATH)
    @Operation(summary = ApiConstants.OP_MAX_STOCK_SUMMARY,
            description = ApiConstants.OP_MAX_STOCK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.OK_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_MAX_STOCK,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BranchWithMaxProductResponseDTO.class)))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_FRANCHISE,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Flux<BranchWithMaxProductResponseDTO> findMaxStockProductPerBranch(@PathVariable String franchiseId) {
        return branchHandler.findMaxStockProductPerBranch(franchiseId);
    }

    @PatchMapping
    @Operation(summary = ApiConstants.OP_UPDATE_BRANCH_SUMMARY, description = ApiConstants.OP_UPDATE_BRANCH_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.OK_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_UPDATE_BRANCH),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_BRANCH,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<BranchWithoutProductsDTO>> updateBranch(
            @Valid @RequestBody BranchRequestDTO branchRequestDTO) {
        return branchHandler.updateBranch(branchRequestDTO)
                .map(branch ->
                        ResponseEntity.ok().body(branch));
    }
}
