package com.franchise.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Product {

    private Long id;
    private String name;
    private int stock;
    private Long branchId;
}
