package com.franchise.infrastructure.helper.constants;

public class DynamoAdapterConstants {

    private DynamoAdapterConstants() {}

    public static final String PREFIX_FRANCHISE = "FRANCHISE#";
    public static final String PREFIX_BRANCH = "BRANCH#";
    public static final String PREFIX_PRODUCT = "PRODUCT#";
    public static final String DYNAMODB_TABLE_NAME = "franchise-system";

    public static final String INVALID_FRANCHISE_ID = "Franchise ID must start with 'FRANCHISE'";
    public static final String INVALID_BRANCH_ID = "Branch ID must start with 'BRANCH'";

}
