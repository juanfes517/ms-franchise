package com.franchise.infrastructure.helper.constants;

public class DynamoAdapterConstants {

    private DynamoAdapterConstants() {}

    public static final String PREFIX_FRANCHISE = "FRANCHISE#";
    public static final String PREFIX_BRANCH = "BRANCH#";
    public static final String PREFIX_PRODUCT = "PRODUCT#";
    public static final String DYNAMODB_TABLE_NAME = "franchise-system";

    public static final String INVALID_FRANCHISE_ID = "Franchise ID must start with 'FRANCHISE'";
    public static final String INVALID_BRANCH_ID = "Branch ID must start with 'BRANCH'";
    public static final String INVALID_PRODUCT_ID = "Product ID must start with 'PRODUCT'";

    public static final String ATTRIBUTE_EXISTS_PARTITION_KEY = "attribute_exists(partitionKey)";
    public static final String DYNAMODB_BEGINS_EXPRESSION = "begins_with(partitionKey, :pkPrefix) AND sortKey = :sk";
    public static final String ATTRIBUTE_EXISTS_EXPRESSION = "attribute_exists(partitionKey) AND attribute_exists(sortKey)";
    public static final String PK_PREFIX_EXPRESSION_VALUE = ":pkPrefix";
    public static final String SK_PREFIX_EXPRESSION_VALUE = ":sk";
}
