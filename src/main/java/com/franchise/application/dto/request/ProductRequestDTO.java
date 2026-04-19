package com.franchise.application.dto.request;

import com.franchise.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductRequestDTO {

    @NotBlank(message = DtoConstants.PRODUCT_ID_CANNOT_BE_NULL)
    private String id;

    @NotBlank(message = DtoConstants.BRANCH_ID_CANNOT_BE_NULL)
    private String branchId;

    @PositiveOrZero
    private Integer stock;

    private String name;
}
