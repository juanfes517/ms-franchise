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
    private int stock;
    private String branchId;
}
