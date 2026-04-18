package com.franchise.application.helper.exception;

import com.franchise.application.helper.constants.ExceptionConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class BranchNotFoundException extends CustomException {

    public BranchNotFoundException(String franchiseId) {
        super(
                ExceptionConstants.BRANCH_NOT_FOUND_MESSAGE,
                ExceptionConstants.BRANCH_NOT_FOUND_CODE,
                Map.of(ExceptionConstants.BRANCH_ID_STRING, franchiseId)
        );
    }
}
