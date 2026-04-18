package com.franchise.application.dto.request;

import com.franchise.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CreateBranchDTO {

    @NotBlank(message = DtoConstants.BRANCH_NAME_CANNOT_BE_NULL)
    private String name;

    @NotBlank(message = DtoConstants.FRANCHISE_ID_CANNOT_BE_NULL)
    private String franchiseId;
}
