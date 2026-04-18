package com.franchise.application.helper.exception;

import com.franchise.application.helper.constants.ExceptionConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class FranchiseNotFoundException extends CustomException {

    public FranchiseNotFoundException(String franchiseId) {
        super(
                ExceptionConstants.FRANCHISE_NOT_FOUND_MESSAGE,
                ExceptionConstants.FRANCHISE_NOT_FOUND_CODE,
                Map.of(ExceptionConstants.FRANCHISE_ID_STRING, franchiseId)
        );
    }
}
