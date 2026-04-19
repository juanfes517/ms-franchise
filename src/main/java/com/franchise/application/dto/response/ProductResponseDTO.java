package com.franchise.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponseDTO {

    private String id;
    private String name;
    private Integer stock;
    private String branchId;
}
