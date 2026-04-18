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
public class CreateProductDTO {

    @NotBlank(message = DtoConstants.PRODUCT_NAME_CANNOT_BE_NULL)
    private String name;

    @PositiveOrZero
    private int stock;

    @NotBlank(message = DtoConstants.BRANCH_ID_CANNOT_BE_NULL)
    private String branchId;
}
