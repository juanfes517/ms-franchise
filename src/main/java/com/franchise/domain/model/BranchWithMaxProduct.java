package com.franchise.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BranchWithMaxProduct {

    private String id;
    private String name;
    private String franchiseId;
    private Product maxStockProduct;
}
