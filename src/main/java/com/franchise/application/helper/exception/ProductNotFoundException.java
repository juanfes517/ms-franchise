package com.franchise.application.helper.exception;

import com.franchise.application.helper.constants.ExceptionConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException(String productId, String branchId) {
        super(
                ExceptionConstants.PRODUCT_NOT_FOUND_MESSAGE,
                ExceptionConstants.PRODUCT_NOT_FOUND_CODE,
                Map.of(ExceptionConstants.PRODUCT_ID_STRING, productId, ExceptionConstants.BRANCH_ID_STRING, branchId)
        );
    }
}
