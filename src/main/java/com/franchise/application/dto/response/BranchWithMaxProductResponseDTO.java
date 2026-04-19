package com.franchise.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BranchWithMaxProductResponseDTO {

    private String id;
    private String name;
    private String franchiseId;
    private ProductResponseDTO maxStockProduct;
}
