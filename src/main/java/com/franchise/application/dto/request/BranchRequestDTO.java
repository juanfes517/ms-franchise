package com.franchise.application.dto.request;

import com.franchise.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BranchRequestDTO {

    @NotBlank(message = DtoConstants.BRANCH_ID_CANNOT_BE_NULL)
    private String id;

    @NotBlank(message = DtoConstants.BRANCH_NAME_CANNOT_BE_NULL)
    private String name;

    @NotBlank(message = DtoConstants.FRANCHISE_ID_CANNOT_BE_NULL)
    private String franchiseId;
}
