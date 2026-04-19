package com.franchise.infrastructure.adapter.input.rest;

import com.franchise.application.dto.request.CreateFranchiseDTO;
import com.franchise.application.dto.request.FranchiseRequestDTO;
import com.franchise.application.dto.response.ExceptionResponseDTO;
import com.franchise.application.dto.response.FranchiseWithoutBranchDTO;
import com.franchise.application.handler.IFranchiseHandler;
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
@RequestMapping(ApiConstants.FRANCHISE_API_PATH)
@RequiredArgsConstructor
@Tag(name = "Franchise", description = ApiConstants.TAG_FRANCHISE_DESCRIPTION)
public class FranchiseController {

    private final IFranchiseHandler franchiseHandler;

    @PostMapping
    @Operation(summary = ApiConstants.OP_CREATE_FRANCHISE_SUMMARY, description = ApiConstants.OP_CREATE_FRANCHISE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.CREATED_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_CREATE_FRANCHISE),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<FranchiseWithoutBranchDTO>> createFranchise(
            @Valid @RequestBody CreateFranchiseDTO createFranchiseDTO) {
        return franchiseHandler.createFranchise(createFranchiseDTO)
                .map(saved -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(saved));
    }

    @PatchMapping
    @Operation(summary = ApiConstants.OP_UPDATE_FRANCHISE_SUMMARY, description = ApiConstants.OP_UPDATE_FRANCHISE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstants.OK_VALUE, description = ApiConstants.API_RESPONSE_SUCCESS_UPDATE_FRANCHISE),
            @ApiResponse(responseCode = ApiConstants.BAD_REQUEST_VALUE, description = ApiConstants.API_RESPONSE_BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
            @ApiResponse(responseCode = ApiConstants.NOT_FOUND_VALUE, description = ApiConstants.API_RESPONSE_NOT_FOUND_FRANCHISE,
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
    })
    public Mono<ResponseEntity<FranchiseWithoutBranchDTO>> updateFranchise(
            @Valid @RequestBody FranchiseRequestDTO franchiseRequestDTO) {
        return franchiseHandler.updateFranchise(franchiseRequestDTO)
                .map(updated -> ResponseEntity
                        .ok()
                        .body(updated));
    }
}
