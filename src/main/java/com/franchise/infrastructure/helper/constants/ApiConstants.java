package com.franchise.infrastructure.helper.constants;

public class ApiConstants {

    private ApiConstants() {}

    public static final String FRANCHISE_API_PATH = "/franchise";
    public static final String BRANCH_API_PATH = "/branch";
    public static final String PRODUCT_API_PATH = "/product";
    public static final String MAX_STOCK_PER_BRANCH_PATH = "/franchises/{franchiseId}/branches/max-stock-products";

    public static final String TAG_FRANCHISE_DESCRIPTION = "Operations to manage franchises";
    public static final String TAG_BRANCH_DESCRIPTION = "Operations to manage branches";
    public static final String TAG_PRODUCT_DESCRIPTION = "Operations to manage products";

    public static final String OP_CREATE_FRANCHISE_SUMMARY = "Create franchise";
    public static final String OP_CREATE_FRANCHISE_DESCRIPTION = "Creates a new franchise";
    public static final String OP_UPDATE_FRANCHISE_SUMMARY = "Update franchise";
    public static final String OP_UPDATE_FRANCHISE_DESCRIPTION = "Updates an existing franchise";
    public static final String OP_ADD_BRANCH_SUMMARY = "Add branch";
    public static final String OP_ADD_BRANCH_DESCRIPTION = "Adds a branch to a franchise";
    public static final String OP_MAX_STOCK_SUMMARY = "Get max stock product by branch";
    public static final String OP_MAX_STOCK_DESCRIPTION = "Gets, for each branch in the franchise, the product with the highest stock";
    public static final String OP_UPDATE_BRANCH_SUMMARY = "Update branch";
    public static final String OP_UPDATE_BRANCH_DESCRIPTION = "Updates branch information";
    public static final String OP_ADD_PRODUCT_SUMMARY = "Add product";
    public static final String OP_ADD_PRODUCT_DESCRIPTION = "Adds a product to a branch";
    public static final String OP_DELETE_PRODUCT_SUMMARY = "Delete product";
    public static final String OP_DELETE_PRODUCT_DESCRIPTION = "Deletes a product from a branch";
    public static final String OP_UPDATE_PRODUCT_SUMMARY = "Update product";
    public static final String OP_UPDATE_PRODUCT_DESCRIPTION = "Updates product information";

    public static final String API_RESPONSE_SUCCESS_CREATE_FRANCHISE = "Franchise created successfully";
    public static final String API_RESPONSE_SUCCESS_UPDATE_FRANCHISE = "Franchise updated successfully";
    public static final String API_RESPONSE_SUCCESS_CREATE_BRANCH = "Branch created successfully";
    public static final String API_RESPONSE_SUCCESS_MAX_STOCK = "Query completed successfully";
    public static final String API_RESPONSE_SUCCESS_UPDATE_BRANCH = "Branch updated successfully";
    public static final String API_RESPONSE_SUCCESS_CREATE_PRODUCT = "Product created successfully";
    public static final String API_RESPONSE_SUCCESS_DELETE_PRODUCT = "Product deleted successfully";
    public static final String API_RESPONSE_SUCCESS_UPDATE_PRODUCT = "Product updated successfully";

    public static final String API_RESPONSE_BAD_REQUEST = "Invalid request";
    public static final String API_RESPONSE_NOT_FOUND_FRANCHISE = "Franchise not found";
    public static final String API_RESPONSE_NOT_FOUND_BRANCH = "Branch not found";
    public static final String API_RESPONSE_NOT_FOUND_PRODUCT = "Product not found";
    public static final String API_RESPONSE_NOT_FOUND_PRODUCT_OR_BRANCH = "Product or branch not found";
    public static final String API_RESPONSE_INTERNAL_SERVER_ERROR = "Internal server error";

    public static final String CREATED_VALUE = "201";
    public static final String BAD_REQUEST_VALUE = "400";
    public static final String NOT_FOUND_VALUE = "404";
    public static final String OK_VALUE = "200";

}
