package com.franchise.application.dto.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExceptionResponseDTO {

    private int status;
    private String code;
    private String message;
    private Map<String, String> details;
}
