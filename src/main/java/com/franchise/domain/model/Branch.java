package com.franchise.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Branch {

    private String id;
    private String name;
    private String franchiseId;
}
