package com.franchise.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Branch {

    private Long id;
    private String name;
    private Long franchiseId;
}
