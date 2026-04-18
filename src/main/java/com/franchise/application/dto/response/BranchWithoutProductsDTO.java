package com.franchise.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BranchWithoutProductsDTO {

    private String id;
    private String name;
    private String franchiseId;
}
